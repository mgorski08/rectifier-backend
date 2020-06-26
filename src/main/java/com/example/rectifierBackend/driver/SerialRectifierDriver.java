package com.example.rectifierBackend.driver;

import com.example.rectifierBackend.model.Sample;
import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;

@Component("serial")
public class SerialRectifierDriver implements RectifierDriver {


    @Override
    public Sample readSample(long bathId) {
        SerialPort comPort = SerialPort.getCommPort("/dev/ttyr00");
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);

        byte[] readBuffer = new byte[10];
        comPort.writeBytes(new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xF1, (byte)0xFF, 0, 0, 0, 0, 0, 0}, 10);
        comPort.readBytes(readBuffer, readBuffer.length);
        System.out.println(Arrays.toString(readBuffer));
        comPort.closePort();
        Sample sample = new Sample();
        sample.setCurrent(readBuffer[0]<<8+readBuffer[1]&0xFF);
        sample.setVoltage(readBuffer[6]<<8+readBuffer[7]&0xFF);
        sample.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return sample;
    }
}
