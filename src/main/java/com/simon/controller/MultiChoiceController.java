package com.simon.controller;

import com.simon.domain.MultiChoice;
import com.simon.domain.ResultMsg;
import com.simon.repository.MultiChoiceRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by simon on 2017/4/16.
 */
@Api(description = "多选题")
@RestController
@RequestMapping("/api/multiChoices")
public class MultiChoiceController {

    @Autowired
    private MultiChoiceRepository multiChoiceRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultMsg get(@PathVariable String id){
        ResultMsg resultMsg = new ResultMsg();

        MultiChoice multiChoice = multiChoiceRepository.findOne(id);

        if(null == multiChoice){
            resultMsg.setStatus(404);
            resultMsg.setMessage("找不到该题目");
        }else{
            resultMsg.setStatus(300);
            resultMsg.setData(multiChoice);
        }

        return resultMsg;
    }
}
