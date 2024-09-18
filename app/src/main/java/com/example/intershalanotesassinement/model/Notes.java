package com.example.intershalanotesassinement.model;

public class Notes {

    int id;
    private String summary;
    private String topic;

    public Notes(int id,String summary, String topic) {
        this.id=id;
        this.topic = topic;
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

