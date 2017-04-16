package com.simon.controller;

import com.simon.domain.FillBlank;
import com.simon.domain.ResultMsg;
import com.simon.repository.FillBlankRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by simon on 2017/4/16.
 */
@Api(description = "填空题")
@RestController
@RequestMapping("/api/fillBlanks")
public class FillBlankController {
    @Autowired
    private FillBlankRepository fillBlankRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultMsg get(@PathVariable String id){
        ResultMsg resultMsg = new ResultMsg();

        FillBlank fillBlank = fillBlankRepository.findOne(id);

        if(null == fillBlank){
            resultMsg.setStatus(404);
            resultMsg.setMessage("找不到该题目");
        }else{
            resultMsg.setStatus(200);
            resultMsg.setData(fillBlank);
        }

        return resultMsg;
    }
}
