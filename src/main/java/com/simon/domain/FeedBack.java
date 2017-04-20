package com.simon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by simon on 2017/4/20.
 */

@Document(collection = "qb_feed_back")
public class FeedBack {
    @Id
    private String id;
    private String userId;
    private String content;
    private Long lastEditTime;
    private String device;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
