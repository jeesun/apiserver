package com.simon.exception;

/**
 * Created by simon on 2017/5/13.
 */
public class NoPaperRecordException extends Exception {
    public NoPaperRecordException() {
        super("没有练习记录");
    }
}
