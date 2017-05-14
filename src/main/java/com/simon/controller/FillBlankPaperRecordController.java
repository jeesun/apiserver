package com.simon.controller;

import com.alibaba.fastjson.JSON;
import com.simon.domain.*;
import com.simon.exception.NoPaperRecordException;
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

    @Autowired
    private FillBlankPaperRepository fillBlankPaperRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResultMsg post(@RequestParam String access_token,
                          @RequestParam String courseId,
                          @RequestParam String paperId,
                          @RequestBody String records){
        ResultMsg resultMsg = new ResultMsg();
        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);

        List<FillBlankRecord> fillBlankRecords = JSON.parseArray(records, FillBlankRecord.class);

        PaperRecord paperRecord = new PaperRecord();
        paperRecord.setDoTime(0);
        paperRecord.setCourseId(courseId);
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

    @RequestMapping(value = "/{paperRecordId}/records", method = RequestMethod.GET)
    public ResultMsg getIncludeRight(@RequestParam String access_token,
                                     @PathVariable("paperRecordId") String paperRecordId){
        ResultMsg resultMsg = new ResultMsg();
        List<FillBlankRecord> result = new ArrayList<>();

        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);

        PaperRecord paperRecord = paperRecordRepository.findOne(paperRecordId);

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
        resultMsg.setStatus(200);
        resultMsg.setData(result);
        return resultMsg;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultMsg get(@RequestParam String access_token, @RequestParam String courseId) throws NoPaperRecordException {
        ResultMsg resultMsg = new ResultMsg();

        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);

        List<PaperRecord> paperRecords = paperRecordRepository.findByUserIdAndPaperTypeAndCourseId(userId, PaperType.FILL_BLANK, courseId);
        List<FillBlankPaper> fillBlankPapers = fillBlankPaperRepository.findAllByBelongInfoCourseId(courseId);
        List<DetailPaperRecord> detailPaperRecords = new ArrayList<>();
        for(int i=0; i<paperRecords.size(); i++){
            DetailPaperRecord record = new DetailPaperRecord();
            record.setPaperRecord(paperRecords.get(i));
            for(int j=0; j<fillBlankPapers.size(); j++){
                if(paperRecords.get(i).getPaperId().equals(fillBlankPapers.get(j).getId())){
                    record.setPaperName(fillBlankPapers.get(j).getPaperName());
                    record.setBelongInfo(fillBlankPapers.get(j).getBelongInfo());
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
