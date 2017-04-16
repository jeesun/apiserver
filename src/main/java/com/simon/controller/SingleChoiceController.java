package com.simon.controller;

import com.simon.domain.ResultMsg;
import com.simon.domain.SingleChoice;
import com.simon.repository.SingleChoiceRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by simon on 2017/4/16.
 */
@Api(description = "单选题")
@RestController
@RequestMapping("/api/singleChoices")
public class SingleChoiceController {
    @Autowired
    private SingleChoiceRepository singleChoiceRepository;

    @RequestMapping(value = "{/id}", method = RequestMethod.GET)
    public ResultMsg get(@PathVariable String id){
        ResultMsg resultMsg = new ResultMsg();

        SingleChoice singleChoice = singleChoiceRepository.findOne(id);

        if(null == singleChoice){
            resultMsg.setStatus(404);
            resultMsg.setMessage("找不到该题目");
        }else{
            resultMsg.setStatus(200);
            resultMsg.setData(singleChoice);
            resultMsg.setMessage("获取成功");
        }
        return resultMsg;
    }
}
