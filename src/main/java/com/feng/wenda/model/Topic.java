package com.feng.wenda.model;


public class Topic {
    private int id;
    private String topicName;
    private String topicDesc;
    private int parentTopicId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    public int getParentTopicId() {
        return parentTopicId;
    }

    public void setParentTopicId(int parentTopicId) {
        this.parentTopicId = parentTopicId;
    }
}
