package com.example.rectifierBackend.service;

import com.example.rectifierBackend.model.Process;
import com.example.rectifierBackend.model.Sample;
import com.example.rectifierBackend.repository.ProcessRepository;
import com.example.rectifierBackend.repository.SampleRepository;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;

@Service
public class RectifierService {
    private static final long SAMPLE_RATE_MS = 2000;
    private final Random random = new Random();
    private final Map<Long, ScheduledFuture<?>> runningProcesses = new HashMap<>();
    private final TaskScheduler taskScheduler;
    private final SampleRepository sampleRepository;
    private final ProcessRepository processRepository;
    private final Set<BlockingQueue<Sample>> blockingQueueSet = new HashSet<>();

    @Autowired
    RectifierService(TaskScheduler taskScheduler,
                     SampleRepository sampleRepository,
                     ProcessRepository processRepository,
                     ApplicationEventPublisher applicationEventPublisher) {
        this.taskScheduler = taskScheduler;
        this.sampleRepository = sampleRepository;
        this.processRepository = processRepository;
    }

    public void startProcess(long processId) {
        Process process = processRepository
                .findById(processId)
                .orElseThrow(
                        () -> new RuntimeException("Process doesn't exist.")
                );
        ScheduledFuture<?> scheduledFuture =
                taskScheduler.scheduleAtFixedRate(() -> {
                    Sample sample = sampleBath(process.getBath().getId());
                    sample.setProcess(process);
                    sampleRepository.save(sample);
                    for(BlockingQueue<Sample> queue : blockingQueueSet) {
                        queue.add(sample);
                    }
                }, SAMPLE_RATE_MS);
        runningProcesses.put(processId, scheduledFuture);
        process.setStartTimestamp(new Timestamp(System.currentTimeMillis()));
        processRepository.save(process);
    }

    public void stopProcess(long processId) {
        Process process = processRepository
                .findById(processId)
                .orElseThrow(
                        () -> new RuntimeException("Process doesn't exist.")
                );
        ScheduledFuture<?> scheduledFuture = runningProcesses.get(processId);
        process.setStopTimestamp(new Timestamp(System.currentTimeMillis()));
        processRepository.save(process);
        scheduledFuture.cancel(false);
    }

    public Sample sampleBath(long bathId) {
        Sample sample = new Sample();
        sample.setCurrent(15 + random.nextGaussian());
        sample.setVoltage(12 + random.nextGaussian());
        sample.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return sample;
    }

    public void writeSamples(OutputStream outputStream, long processId) throws IOException {
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(outputStream);
        jsonGenerator.setCodec(new ObjectMapper());
        BlockingQueue<Sample> blockingQueue = new LinkedBlockingQueue<>();
        blockingQueueSet.add(blockingQueue);
        Sample sample;
        do {
            try {
                sample = blockingQueue.take();
                jsonGenerator.writeRaw("data:");
                jsonGenerator.writeObject(sample);
                jsonGenerator.flush();
            } catch (InterruptedException e) {
            }
        } while(true);
    }
}
