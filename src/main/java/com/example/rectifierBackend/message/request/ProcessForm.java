package com.example.rectifierBackend.message.request;

public class ProcessForm {

    private String description;
    private long bathId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getBathId() {
        return bathId;
    }

    public void setBathId(long bathId) {
        this.bathId = bathId;
    }
}
