package com.simon.exception;

/**
 * Created by simon on 2017/4/9.
 */
public class NoMsgException extends Exception {
    public NoMsgException() {
        super("暂无消息");
    }
}
