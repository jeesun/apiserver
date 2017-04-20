package com.simon.exception;

/**
 * Created by simon on 2017/4/9.
 */
public class NoCollectException extends Exception {
    public NoCollectException() {
        super("您还没有收藏题目");
    }
}
