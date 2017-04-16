package com.simon.controller;

import com.alibaba.fastjson.JSON;
import com.simon.domain.AppUser;
import com.simon.domain.Collect;
import com.simon.domain.ResultMsg;
import com.simon.exception.NoCollectException;
import com.simon.exception.NotFoundQuestionException;
import com.simon.repository.*;
import com.simon.util.TokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by simon on 2017/4/9.
 */
@Api(description = "错题")
@RestController
@RequestMapping("/api/collects")
public class CollectController {
    @Autowired
    private CollectRepository collectRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SingleChoiceRepository singleChoiceRepository;

    @Autowired
    private MultiChoiceRepository multiChoiceRepository;

    @Autowired
    private FillBlankRepository fillBlankRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResultMsg get(@RequestParam String access_token,
                         @RequestParam(required = false) Integer limit,
                         @RequestParam(required = false) Integer offset) throws Exception{
        ResultMsg resultMsg = new ResultMsg();
        AppUser appUser = TokenUtil.getInstance().getAppUserByAccessToken(appUserRepository, jdbcTemplate, access_token);

        List<Collect> collects;
        if(null!=limit && null!=offset){
            collects = collectRepository.getByUserId(appUser.getId(), new PageRequest(offset/limit, limit, new Sort(Sort.Direction.DESC, "lastEditTime")));
        }else{
            collects = collectRepository.getByUserId(appUser.getId());
        }
        if(collects.size()<=0){
            throw new NoCollectException();
        }
        resultMsg.setStatus(200);
        resultMsg.setData(collects);

        return resultMsg;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResultMsg post(@RequestParam String access_token,
                          @RequestBody String json){
        ResultMsg resultMsg = new ResultMsg();
        AppUser appUser = TokenUtil.getInstance().getAppUserByAccessToken(appUserRepository, jdbcTemplate, access_token);

        Collect collect = JSON.parseObject(json, Collect.class);
        collect.setUserId(appUser.getId());
        collect.setLastEditTime(System.currentTimeMillis());
        collectRepository.save(collect);

        resultMsg.setStatus(201);
        resultMsg.setMessage("收藏成功");

        return resultMsg;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultMsg getById(@PathVariable String id) throws Exception{
        ResultMsg resultMsg = new ResultMsg();
        Collect collect = collectRepository.findOne(id);
        String questionId = collect.getQuestionId();
        int questionType = collect.getQuestionType();
        switch (questionType){
            case 0:
                //单选题
                resultMsg.setStatus(200);
                resultMsg.setData(singleChoiceRepository.findOne(questionId));
                break;
            case 1:
                //多选题
                resultMsg.setStatus(200);
                resultMsg.setData(multiChoiceRepository.findOne(questionId));
                break;
            case 2:
                //填空题
                resultMsg.setStatus(200);
                resultMsg.setData(fillBlankRepository.findOne(questionId));
                break;
            default:
                throw new NotFoundQuestionException();
        }
        return resultMsg;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultMsg deleteById(@PathVariable String id){
        ResultMsg resultMsg = new ResultMsg();

        collectRepository.delete(id);

        resultMsg.setStatus(200);
        resultMsg.setMessage("取消收藏成功");

        return resultMsg;
    }

    @RequestMapping(value = "/user/{userId}/question/{questionId}", method = RequestMethod.DELETE)
    public ResultMsg deleteByUserIdAndQuestionId(@PathVariable String userId,
                                                 @PathVariable String questionId){
        ResultMsg resultMsg = new ResultMsg();
        collectRepository.deleteByUserIdAndQuestionId(userId, questionId);
        resultMsg.setStatus(200);
        resultMsg.setMessage("取消收藏成功");

        return resultMsg;
    }
}
