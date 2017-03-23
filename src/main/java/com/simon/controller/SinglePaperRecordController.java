package com.simon.controller;

import com.alibaba.fastjson.JSON;
import com.simon.domain.PaperRecord;
import com.simon.domain.ResultMsg;
import com.simon.domain.SingleRecord;
import com.simon.repository.AppUserRepository;
import com.simon.repository.PaperRecordRepository;
import com.simon.repository.SingleRecordRepository;
import com.simon.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by simon on 2017/3/5.
 */
@RestController
@RequestMapping("/api/singlePaperRecords")
public class SinglePaperRecordController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PaperRecordRepository paperRecordRepository;

    @Autowired
    private SingleRecordRepository singleRecordRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultMsg post(@RequestParam String access_token,
                          @RequestParam String paperId,
                          @RequestParam String records){
        ResultMsg resultMsg = new ResultMsg();
        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);

        List<SingleRecord> singleRecordList = JSON.parseArray(records, SingleRecord.class);

        for(int i=0; i<singleRecordList.size(); i++){
            singleRecordRepository.save(singleRecordList.get(i));
        }

        PaperRecord paperRecord = new PaperRecord();
        paperRecord.setDoTime(0);
        paperRecord.setPaperId(paperId);
        paperRecord.setPaperType(0);//单选题0，多选题1，填空题2，综合题3
        paperRecord.setUserId(userId);
        paperRecordRepository.save(paperRecord);

        resultMsg.setStatus(201);
        resultMsg.setMessage("提交成功");

        return resultMsg;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResultMsg get(@RequestParam String access_token){
        ResultMsg resultMsg = new ResultMsg();

        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);

        List<SingleRecord> singleRecords = singleRecordRepository.findByUserId(userId);

        resultMsg.setStatus(200);
        resultMsg.setData(singleRecords);

        return resultMsg;
    }
}
