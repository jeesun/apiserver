package com.simon.controller;

import com.simon.domain.FeedBack;
import com.simon.domain.ResultMsg;
import com.simon.repository.AppUserRepository;
import com.simon.repository.FeedBackRepository;
import com.simon.util.TokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by simon on 2017/4/21.
 */
@Api(description = "反馈")
@RestController
@RequestMapping("/api/feedBacks")
public class FeedBackController {
    @Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AppUserRepository appUserRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResultMsg post(@RequestParam String access_token,
                          @RequestParam String content,
                          @RequestParam String device){
        ResultMsg resultMsg = new ResultMsg();
        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);
        FeedBack feedBack = new FeedBack();
        feedBack.setUserId(userId);
        feedBack.setContent(content);
        feedBack.setLastEditTime(System.currentTimeMillis());
        feedBack.setDevice(device);
        feedBack = feedBackRepository.save(feedBack);
        if(null != feedBack){
            resultMsg.setStatus(201);
            resultMsg.setMessage("反馈成功！感谢您的反馈");
        }else{
            resultMsg.setStatus(404);
            resultMsg.setMessage("反馈失败");
        }
        return resultMsg;
    }
}
