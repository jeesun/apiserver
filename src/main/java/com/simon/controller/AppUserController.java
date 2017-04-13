package com.simon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by simon on 2017/3/5.
 */
@RestController
@RequestMapping("/api/appUsers")
public class AppUserController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/api/hellos/ip", method = RequestMethod.GET)
    public String hello(){
        String hello = restTemplate.getForObject("http://oauth/api/hellos/ip", String.class);
        return hello;
    }
}
