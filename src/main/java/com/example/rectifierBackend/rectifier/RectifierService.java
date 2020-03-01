package com.example.rectifierBackend.rectifier;

import com.example.rectifierBackend.model.Process;
import com.fazecast.jSerialComm.SerialPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RectifierService {

    public static Process startProcess(long bathId) {
        Process process = new Process();
        process.setBathId(bathId);
        return process;
    }

    @Scheduled(fixedRate = 1000)
    public void pollRectifier() {
//        SerialPort[] comPorts = SerialPort.getCommPorts();
//        System.out.println(comPorts.length);
    }


}
