package com.example.rectifierBackend.service;

import com.example.rectifierBackend.model.Process;
import com.example.rectifierBackend.model.Sample;
import com.example.rectifierBackend.repository.SampleRepository;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Service
public class RectifierService {
    private static final long SAMPLE_RATE_MS = 2000;
    Random random = new Random();
    Map<Process, ScheduledFuture<?>> runningProcesses = new HashMap<>();
    TaskScheduler taskScheduler;
    SampleRepository sampleRepository;

    @Autowired
    RectifierService(TaskScheduler taskScheduler, SampleRepository sampleRepository) {
        this.taskScheduler = taskScheduler;
        this.sampleRepository = sampleRepository;
    }

    public void startProcess(Process process) {
        ScheduledFuture<?> scheduledFuture =
                taskScheduler.scheduleAtFixedRate(() -> {
                    /*
                    Collect sample
                    save to database

                    */
                    Sample sample = sampleBath(process.getBath().getId());
                    sample.setProcess(process);
                    sampleRepository.save(sample);
                }, SAMPLE_RATE_MS);

    }

    public Sample sampleBath(long bathId) {
        Sample sample = new Sample();
        sample.setCurrent(15 + random.nextGaussian());
        sample.setVoltage(12 + random.nextGaussian());
        sample.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return sample;
    }

    public void writeSamples(OutputStream outputStream, Process process) throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonGenerator jsonGenerator = factory.createGenerator(outputStream);
        jsonGenerator.setCodec(new ObjectMapper());
        jsonGenerator.writeStartArray();
        for (int i = 0 ; i < 10 ; ++i) {
            Sample sample = new Sample();
            sample.setProcess(process);
            sample.setCurrent(15 + random.nextGaussian());
            sample.setVoltage(12 + random.nextGaussian());
            sample.setTimestamp(new Timestamp(System.currentTimeMillis()));
            jsonGenerator.writeObject(sample);
            jsonGenerator.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.close();
    }
}
