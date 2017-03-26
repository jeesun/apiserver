package com.simon.controller;

import com.alibaba.fastjson.JSON;
import com.simon.domain.FillBlankRecord;
import com.simon.domain.PaperRecord;
import com.simon.domain.ResultMsg;
import com.simon.repository.*;
import com.simon.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by simon on 2017/3/5.
 */
@RestController
@RequestMapping("/api/fillBlankPaperRecords")
public class FillBlankPaperRecordController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PaperRecordRepository paperRecordRepository;

    @Autowired
    private FillBlankRepository fillBlankRepository;

    @Autowired
    private FillRecordRepository fillRecordRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResultMsg post(@RequestParam String access_token,
                          @RequestParam String paperId,
                          @RequestBody String records){
        ResultMsg resultMsg = new ResultMsg();
        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);

        List<FillBlankRecord> fillBlankRecords = JSON.parseArray(records, FillBlankRecord.class);

        PaperRecord paperRecord = new PaperRecord();
        paperRecord.setDoTime(0);
        paperRecord.setPaperId(paperId);
        paperRecord.setPaperType(2);//单选题0，多选题1，填空题2，综合题3
        paperRecord.setUserId(userId);
        paperRecord = paperRecordRepository.save(paperRecord);

        for(int i=0; i<fillBlankRecords.size(); i++){
            FillBlankRecord fillBlankRecord = fillBlankRecords.get(i);
            fillBlankRecord.setUserId(userId);
            fillBlankRecord.setFillBlank(fillBlankRepository.findOne(fillBlankRecord.getFillBlankId()));
            fillBlankRecord.setPaperRecordId(paperRecord.getId());
            fillRecordRepository.save(fillBlankRecord);
        }

        resultMsg.setStatus(201);
        resultMsg.setMessage("提交成功");

        return resultMsg;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResultMsg get(@RequestParam String access_token){
        ResultMsg resultMsg = new ResultMsg();

        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);
        List<FillBlankRecord> fillBlankRecords = fillRecordRepository.findByUserId(userId);

        resultMsg.setStatus(ResultMsg.Status.OK);
        resultMsg.setData(fillBlankRecords);
        return resultMsg;
    }
}
