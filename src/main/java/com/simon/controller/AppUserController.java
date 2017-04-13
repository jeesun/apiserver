package com.simon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by simon on 2017/3/5.
 */
@RestController
@RequestMapping("/api/appUsers")
public class AppUserController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/api/hellos/ip", method = RequestMethod.GET)
    public String hello(){
        String hello = restTemplate.getForObject("http://oauth/api/hellos/ip", String.class);
        return hello;
    }

    @RequestMapping(value = "/instances", method = RequestMethod.GET)
    public List<ServiceInstance> serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances("oauth");
        String urls = "";
        if (list != null && list.size() > 0 ) {
            return list;
        }
        return null;
    }
}
