package com.simon.controlleradvice;

import com.simon.domain.ResultMsg;
import com.simon.exception.DemoException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by simon on 2017/3/16.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = DemoException.class)
    @ResponseBody
    public ResultMsg jsonErrorHandler(HttpServletRequest request,
                                      DemoException e) throws Exception{
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setStatus(404);
        resultMsg.setData("not found");
        return resultMsg;
    }
}
