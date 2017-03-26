package com.simon.controller;

import com.simon.domain.FillBlank;
import com.simon.domain.FillBlankPaper;
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
@RequestMapping("/api/fillBlankPapers")
public class FillBlankPaperController {
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

    @RequestMapping(value = "/course/{courseId}/chapter/{chapterId}/section/{sectionId}/user", method = RequestMethod.GET)
    public ResultMsg getByCourseIdAndChapterIdAndSectionId(
            @PathVariable(name = "courseId") String courseId,
            @PathVariable String chapterId,
            @PathVariable String sectionId,
            @RequestParam String access_token){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setStatus(ResultMsg.Status.OK);
        String userId = TokenUtil.getInstance().getAppUserIdByAccessToken(appUserRepository, jdbcTemplate, access_token);
        List<PaperRecord> paperRecordList = paperRecordRepository.findByUserId(userId);
        List<FillBlankPaper> fillBlankPaperList = fillBlankPaperRepository.findByBelongInfoCourseIdAndBelongInfoChapterIdAndBelongInfoSectionId(courseId, chapterId, sectionId);

        for(int i=0; i<fillBlankPaperList.size(); i++){
            boolean flag = false;//试卷没有被做过
            FillBlankPaper fillBlankPaper = fillBlankPaperList.get(i);
            for(int j=0; j<paperRecordList.size(); j++){
                if(fillBlankPaper.getId().equals(paperRecordList.get(j).getId())){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                List<FillBlank> fillBlanks = fillBlankRepository.findByPaperId(fillBlankPaper.getId());
                fillBlankPaper.setFillBlanks(fillBlanks);
                resultMsg.setData(fillBlankPaper);
                break;
            }
        }
        if (null==resultMsg.getData()){
            resultMsg.setStatus(404);
            resultMsg.setMessage("您已做完所有试题");
        }
        return resultMsg;
    }
}
