package com.example.rectifierBackend.service;

import com.example.rectifierBackend.model.Process;
import com.example.rectifierBackend.model.Sample;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Random;

@Service
public class RectifierService {
    public void writeSamples(OutputStream outputStream, Process process) throws IOException {
        Random random = new Random();
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
