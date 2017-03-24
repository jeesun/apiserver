package com.simon.controlleradvice;

import com.simon.domain.ResultMsg;
import com.simon.exception.DemoException;
import com.simon.exception.NoCommmentException;
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
        resultMsg.setMessage("not found");
        return resultMsg;
    }

    @ExceptionHandler(value = NoCommmentException.class)
    @ResponseBody
    public ResultMsg noComment(HttpServletRequest request,
                               NoCommmentException e) throws Exception{
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setStatus(404);
        resultMsg.setMessage(e.getMessage());
        return resultMsg;
    }
}
