package com.simon.exception;

/**
 * Created by simon on 2017/3/18.
 */
public class NoCommmentException extends Exception {
    public NoCommmentException() {
        super("暂无评论");
    }
}
