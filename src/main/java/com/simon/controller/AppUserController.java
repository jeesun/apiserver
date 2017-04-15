package com.simon.controller;

import com.simon.domain.AppUser;
import com.simon.domain.ResultMsg;
import com.simon.repository.*;
import com.simon.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by simon on 2017/3/5.
 */
@RestController
@RequestMapping("/api/appUsers")
public class AppUserController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PaperRecordRepository paperRecordRepository;

    @Autowired
    private SingleRecordRepository singleRecordRepository;

    @Autowired
    private MultiRecordRepository multiRecordRepository;

    @Autowired
    private FillRecordRepository fillRecordRepository;

    @Autowired
    private CollectRepository collectRepository;

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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultMsg get(@RequestParam String access_token){
        ResultMsg resultMsg = new ResultMsg();
        Map<String, Object> responseMap = new LinkedHashMap<>();
        AppUser appUser = TokenUtil.getInstance().getAppUserByAccessToken(appUserRepository,jdbcTemplate, access_token );

        resultMsg.setStatus(200);
        responseMap.put("appUser", appUser);
        responseMap.put("didCount", paperRecordRepository.countByUserId(appUser.getId()));
        responseMap.put("wrongCount", singleRecordRepository.countByUserId(appUser.getId())
        +multiRecordRepository.countByUserId(appUser.getId())
        +fillRecordRepository.countByUserId(appUser.getId()));
        responseMap.put("collectCount", collectRepository.countByUserId(appUser.getId()));
        resultMsg.setData(responseMap);

        return resultMsg;
    }
}
