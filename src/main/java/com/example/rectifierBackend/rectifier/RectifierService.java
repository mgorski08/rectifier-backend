package com.example.rectifierBackend.rectifier;

import com.example.rectifierBackend.model.Bath;
import com.example.rectifierBackend.model.Process;
import com.fazecast.jSerialComm.SerialPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class RectifierService {

    public static void startProcess(Process process, Bath bath) {
        process.setBath(bath);
    }

    @Scheduled(fixedRate = 1000)
    public void pollRectifier() {
//        SerialPort[] comPorts = SerialPort.getCommPorts();
//        System.out.println(comPorts.length);
    }


}
