package com.simon.controller;

import com.alibaba.fastjson.JSON;
import com.simon.domain.*;
import com.simon.exception.NoPaperRecordException;
import com.simon.repository.*;
import com.simon.util.PaperType;
import com.simon.util.TokenUtil;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 2017/3/15.
 */
@RestController
@RequestMapping("/api/multiPaperRecords")
public class MultiPaperRecordController {
    private static final Logger LOG = LoggerFactory.getLogger(MultiPaperRecordController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PaperRecordRepository paperRecordRepository;

    @Autowired
    private MultiChoiceRepository multiChoiceRepository;

    @Autowired
    private MultiRecordRepository multiRecordRepository;

    @Autowired
    private MultiPaperRepository multiPaperRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResultMsg post(@RequestParam String access_token,
                          @RequestParam String courseId,
                          @RequestParam String paperId,
                          @RequestBody String records){
        ResultMsg resultMsg = new ResultMsg();
        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);

        List<MultiRecord> multiRecords = JSON.parseArray(records, MultiRecord.class);

        PaperRecord paperRecord = new PaperRecord();
        paperRecord.setDoTime(0);
        paperRecord.setCourseId(courseId);
        paperRecord.setPaperId(paperId);
        paperRecord.setPaperType(1);//单选题0，多选题1，填空题2，综合题3
        paperRecord.setUserId(userId);
        paperRecord = paperRecordRepository.save(paperRecord);

        for(int i=0; i<multiRecords.size(); i++){
            MultiRecord multiRecord = multiRecords.get(i);
            multiRecord.setUserId(userId);
            multiRecord.setMultiChoice(multiChoiceRepository.findOne(multiRecord.getMultiChoiceId()));
            multiRecord.setPaperRecordId(paperRecord.getId());
            multiRecordRepository.save(multiRecord);
        }

        resultMsg.setStatus(201);
        resultMsg.setMessage("提交成功");

        return resultMsg;
    }

    @RequestMapping(value = "/wrong", method = RequestMethod.GET)
    public ResultMsg get(@RequestParam String access_token){
        ResultMsg resultMsg = new ResultMsg();

        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);
        List<MultiRecord> multiRecords = multiRecordRepository.findByUserId(userId);

        resultMsg.setStatus(ResultMsg.Status.OK);
        resultMsg.setData(multiRecords);
        return resultMsg;
    }

    @RequestMapping(value = "/{paperRecordId}/records", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ResultMsg getIncludeRight(@RequestParam String access_token,
                                     @PathVariable("paperRecordId") String paperRecordId){
        ResultMsg resultMsg = new ResultMsg();
        List<MultiRecord> result = new ArrayList<>();

        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);

        PaperRecord paperRecord = paperRecordRepository.findOne(paperRecordId);

        List<MultiRecord> multiRecords = multiRecordRepository.findByPaperId(paperRecord.getPaperId());
        List<MultiChoice> multiChoices = multiChoiceRepository.findByPaperId(paperRecord.getPaperId());
        for(int i=0; i<multiChoices.size(); i++){
            MultiChoice multiChoice = multiChoices.get(i);
            for(int j=0; j<multiRecords.size(); j++){
                MultiRecord multiRecord = multiRecords.get(j);
                if(multiChoice.getId().equals(multiRecord.getMultiChoiceId())){
                    result.add(multiRecord);
                    break;
                }else if(j==(multiRecords.size()-1)){
                    MultiRecord correctRecord = new MultiRecord();
                    correctRecord.setMultiChoice(multiChoice);
                    correctRecord.setMultiChoiceId(multiChoice.getId());
                    correctRecord.setPaperId(paperRecord.getPaperId());
                    correctRecord.setResult(true);
                    correctRecord.setUserId(userId);
                    correctRecord.setUserChose(
                            ArrayUtils.toPrimitive(((List<Integer>)multiChoice.getAnswer()).toArray(new Integer[multiChoice.getAnswerCount()])));
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

        List<PaperRecord> paperRecords = paperRecordRepository.findByUserIdAndPaperTypeAndCourseId(userId, PaperType.MULTI_CHOICE, courseId);
        List<MultiPaper> multiPapers = multiPaperRepository.findByBelongInfoCourseId(courseId);
        List<DetailPaperRecord> detailPaperRecords = new ArrayList<>();

        for(int i=0; i<paperRecords.size(); i++){
            DetailPaperRecord record = new DetailPaperRecord();
            record.setPaperRecord(paperRecords.get(i));
            for(int j=0; j<multiPapers.size(); j++){
                if(paperRecords.get(i).getPaperId().equals(multiPapers.get(j).getId())){
                    record.setPaperName(multiPapers.get(j).getPaperName());
                    record.setBelongInfo(multiPapers.get(j).getBelongInfo());
                    detailPaperRecords.add(record);
                    break;
                }
            }
        }

        if(paperRecords.size()<=0){
            throw new NoPaperRecordException();
        }else{
            resultMsg.setStatus(200);
            resultMsg.setData(detailPaperRecords);
        }

        return resultMsg;
    }
}
