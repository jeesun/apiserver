package com.simon.controller;

import com.alibaba.fastjson.JSON;
import com.simon.domain.FillBlank;
import com.simon.domain.FillBlankRecord;
import com.simon.domain.PaperRecord;
import com.simon.domain.ResultMsg;
import com.simon.repository.*;
import com.simon.util.PaperType;
import com.simon.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 2017/3/5.
 */
@RestController
@RequestMapping("/api/fillBlankPaperRecords")
public class FillBlankPaperRecordController {
    private static final Logger LOG = LoggerFactory.getLogger(FillBlankPaperController.class);

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

    @RequestMapping(value = "/wrong", method = RequestMethod.GET)
    public ResultMsg get(@RequestParam String access_token){
        ResultMsg resultMsg = new ResultMsg();

        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);
        List<FillBlankRecord> fillBlankRecords = fillRecordRepository.findByUserId(userId);

        resultMsg.setStatus(ResultMsg.Status.OK);
        resultMsg.setData(fillBlankRecords);
        return resultMsg;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultMsg getIncludeRight(@RequestParam String access_token){
        ResultMsg resultMsg = new ResultMsg();
        List<FillBlankRecord> result = new ArrayList<>();

        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);

        List<PaperRecord> paperRecords = paperRecordRepository.findByUserIdAndPaperType(userId, PaperType.FILL_BLANK);
        LOG.warn("paperRecords.size()="+paperRecords.size());

        for(PaperRecord paperRecord : paperRecords){
            List<FillBlankRecord> fillBlankRecords = fillRecordRepository.findByPaperId(paperRecord.getPaperId());
            List<FillBlank> fillBlanks = fillBlankRepository.findByPaperId(paperRecord.getPaperId());
            for(int i=0; i<fillBlanks.size(); i++){
                FillBlank fillBlank = fillBlanks.get(i);
                for(int j=0; j<fillBlankRecords.size(); j++){
                    FillBlankRecord record = fillBlankRecords.get(j);
                    if(fillBlank.getId().equals(record.getFillBlankId())){
                        result.add(record);
                        break;
                    }else if(j==(fillBlanks.size()-1)){
                        FillBlankRecord correctRecord = new FillBlankRecord();
                        correctRecord.setFillBlank(fillBlank);
                        correctRecord.setPaperId(paperRecord.getPaperId());
                        correctRecord.setFillBlankId(fillBlank.getId());
                        Boolean recordResult[] = new Boolean[fillBlank.getBlankCount()];
                        for(int k=0; k<fillBlank.getBlankCount(); k++){
                            recordResult[k] = true;
                        }
                        correctRecord.setResult(recordResult);
                        correctRecord.setUserId(userId);
                        correctRecord.setUserFilled((String[])fillBlank.getAnswer());
                        result.add(correctRecord);
                    }

                }
            }
        }

        return resultMsg;
    }
}
