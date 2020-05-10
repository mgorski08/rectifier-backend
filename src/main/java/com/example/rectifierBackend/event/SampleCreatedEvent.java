package com.example.rectifierBackend.event;

import com.example.rectifierBackend.model.Sample;
import org.springframework.context.ApplicationEvent;

public class SampleCreatedEvent extends ApplicationEvent {

    private final Sample sample;

    public SampleCreatedEvent(Object source, Sample sample) {
        super(source);
        this.sample = sample;
    }

    public Sample getSample() {
        return sample;
    }
}
