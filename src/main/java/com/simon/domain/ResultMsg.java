package com.simon.domain;


/**
 * Created by simon on 2017/3/4.
 */
public class ResultMsg {
    private Integer status;
    private String message;
    private Object data;
    private String developerMessage;
    private Object moreInfo;

    public ResultMsg() {
    }

    public ResultMsg(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public Object getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(Object moreInfo) {
        this.moreInfo = moreInfo;
    }

    public static class Status{
        public static final Integer OK = 200;
        public static final Integer ERR_NOT_FOUND = 404;
    }
}
