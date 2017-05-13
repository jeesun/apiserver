package com.simon.controller;

import com.alibaba.fastjson.JSON;
import com.simon.domain.PaperRecord;
import com.simon.domain.ResultMsg;
import com.simon.domain.SingleChoice;
import com.simon.domain.SingleRecord;
import com.simon.exception.NoPaperRecordException;
import com.simon.repository.AppUserRepository;
import com.simon.repository.PaperRecordRepository;
import com.simon.repository.SingleChoiceRepository;
import com.simon.repository.SingleRecordRepository;
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
@RequestMapping("/api/singlePaperRecords")
public class SinglePaperRecordController {
    private static final Logger LOG = LoggerFactory.getLogger(SinglePaperRecordController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PaperRecordRepository paperRecordRepository;

    @Autowired
    private SingleRecordRepository singleRecordRepository;

    @Autowired
    private SingleChoiceRepository singleChoiceRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultMsg post(@RequestParam String access_token,
                          @RequestParam String courseId,
                          @RequestParam String paperId,
                          @RequestBody String records){
        ResultMsg resultMsg = new ResultMsg();
        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);

        List<SingleRecord> singleRecordList = JSON.parseArray(records, SingleRecord.class);

        PaperRecord paperRecord = new PaperRecord();
        paperRecord.setDoTime(0);
        paperRecord.setCourseId(courseId);
        paperRecord.setPaperId(paperId);
        paperRecord.setPaperType(0);//单选题0，多选题1，填空题2，综合题3
        paperRecord.setUserId(userId);
        paperRecord = paperRecordRepository.save(paperRecord);

        for(int i=0; i<singleRecordList.size(); i++){
            SingleRecord singleRecord = singleRecordList.get(i);
            singleRecord.setUserId(userId);
            singleRecord.setSingleChoice(singleChoiceRepository.findOne(singleRecord.getSingleChoiceId()));
            singleRecord.setPaperRecordId(paperRecord.getId());
            singleRecordRepository.save(singleRecord);
        }

        resultMsg.setStatus(201);
        resultMsg.setMessage("提交成功");

        return resultMsg;
    }

    @RequestMapping(value = "/wrong", method = RequestMethod.GET)
    public ResultMsg getWrong(@RequestParam String access_token){
        ResultMsg resultMsg = new ResultMsg();

        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);

        List<SingleRecord> singleRecords = singleRecordRepository.findByUserId(userId);

        resultMsg.setStatus(200);
        resultMsg.setData(singleRecords);

        return resultMsg;
    }

    @RequestMapping(value = "/{paperRecordId}/records", method = RequestMethod.GET)
    public ResultMsg getIncludeRight(@RequestParam String access_token,
                                     @PathVariable("paperRecordId") String paperRecordId){
        ResultMsg resultMsg = new ResultMsg();
        List<SingleRecord> result = new ArrayList<>();

        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);

        PaperRecord paperRecord = paperRecordRepository.findOne(paperRecordId);

        List<SingleRecord> singleRecords = singleRecordRepository.findByPaperId(paperRecord.getPaperId());
        LOG.warn("singleRecords.size()="+singleRecords.size());
        List<SingleChoice> singleChoices = singleChoiceRepository.findByPaperId(paperRecord.getPaperId());
        LOG.warn("singleChoices.size()="+singleChoices.size());
        for(int i=0; i<singleChoices.size(); i++){
            SingleChoice singleChoice = singleChoices.get(i);

            //对比singleChoice和singleRecord，如果id相同，存singleRecord；如果一轮比较没有id相同的，说明用户答对了。
            for(int j=0; j<singleRecords.size(); j++){
                SingleRecord singleRecord = singleRecords.get(j);
                if(singleChoice.getId().equals(singleRecord.getSingleChoiceId())){
                    result.add(singleRecords.get(j));
                    break;
                }else if(j==(singleRecords.size()-1)){
                    SingleRecord correctRecord = new SingleRecord();
                    correctRecord.setSingleChoice(singleChoice);
                    correctRecord.setPaperRecordId(paperRecord.getId());
                    correctRecord.setPaperId(paperRecord.getPaperId());
                    correctRecord.setResult(true);
                    correctRecord.setUserId(userId);
                    correctRecord.setUserChose((int)singleChoice.getAnswer());
                    result.add(correctRecord);
                }
            }
        }
        resultMsg.setStatus(200);
        resultMsg.setData(result);
        return resultMsg;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultMsg get(@RequestParam String access_token, @RequestParam String courseId) throws NoPaperRecordException{
        ResultMsg resultMsg = new ResultMsg();

        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);

        List<PaperRecord> paperRecords = paperRecordRepository.findByUserIdAndPaperTypeAndCourseId(userId, PaperType.SINGLE_CHOICE, courseId);

        if(paperRecords.size()<=0){
            throw new NoPaperRecordException();
        }else{
            resultMsg.setStatus(200);
            resultMsg.setData(paperRecords);
        }

        return resultMsg;
    }
}
