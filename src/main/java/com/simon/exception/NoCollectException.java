package com.simon.exception;

/**
 * Created by simon on 2017/4/9.
 */
public class NoCollectException extends Exception {
    public NoCollectException() {
        super("没有消息");
    }
}
