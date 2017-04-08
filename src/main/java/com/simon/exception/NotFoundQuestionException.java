package com.simon.exception;

/**
 * Created by simon on 2017/4/9.
 */
public class NotFoundQuestionException extends Exception {
    public NotFoundQuestionException() {
        super("找不到问题");
    }
}
