package com.simon.controller;

import com.simon.domain.MultiChoice;
import com.simon.domain.MultiPaper;
import com.simon.domain.PaperRecord;
import com.simon.domain.ResultMsg;
import com.simon.repository.AppUserRepository;
import com.simon.repository.MultiChoiceRepository;
import com.simon.repository.MultiPaperRepository;
import com.simon.repository.PaperRecordRepository;
import com.simon.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by simon on 2017/3/14.
 */
@RestController
@RequestMapping("/api/multiPapers")
public class MultiPaperController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PaperRecordRepository paperRecordRepository;

    @Autowired
    private MultiPaperRepository multiPaperRepository;

    @Autowired
    private MultiChoiceRepository multiChoiceRepository;

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
        List<MultiPaper> multiPaperList = multiPaperRepository.findByBelongInfoCourseIdAndBelongInfoChapterIdAndBelongInfoSectionId(courseId, chapterId, sectionId);

        for(int i=0; i<multiPaperList.size(); i++){
            boolean flag = false;//试卷没有被做过
            MultiPaper multiPaper = multiPaperList.get(i);
            for(int j=0;j<paperRecordList.size();j++){
                if(multiPaper.getId().equals(paperRecordList.get(j).getId())){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                List<MultiChoice> multiChoices = multiChoiceRepository.findByPaperId(multiPaper.getId());
                multiPaper.setChoices(multiChoices);
                resultMsg.setData(multiPaper);
            }
        }
        if (null==resultMsg.getData()){
            resultMsg.setStatus(404);
            resultMsg.setMessage("您已做完所有试题");
        }
        return resultMsg;
    }
}
