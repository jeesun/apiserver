package com.simon.controller;

import com.simon.exception.DemoException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by simon on 2017/3/16.
 */
@RestController
@RequestMapping("demo")
public class DemoController {
    @RequestMapping("/error")
    public String json()throws DemoException{
        throw new DemoException("没有找到");
    }
}
