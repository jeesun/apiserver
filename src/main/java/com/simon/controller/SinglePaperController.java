package com.simon.controller;

import com.simon.domain.PaperRecord;
import com.simon.domain.ResultMsg;
import com.simon.domain.SingleChoice;
import com.simon.domain.SinglePaper;
import com.simon.repository.AppUserRepository;
import com.simon.repository.PaperRecordRepository;
import com.simon.repository.SingleChoiceRepository;
import com.simon.repository.SinglePaperRepository;
import com.simon.util.TokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by simon on 2017/3/14.
 */
@Api(value = "/api/singlePapers", description = "单选题试卷")
@RestController
@RequestMapping("/api/singlePapers")
public class SinglePaperController {
    @Autowired
    private SinglePaperRepository singlePaperRepository;

    @Autowired
    private SingleChoiceRepository singleChoiceRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PaperRecordRepository paperRecordRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultMsg getById(@PathVariable String id){
        ResultMsg resultMsg = new ResultMsg();
        SinglePaper singlePaper = singlePaperRepository.findOne(id);
        singlePaper.setChoices(singleChoiceRepository.findByPaperId(id));
        resultMsg.setStatus(ResultMsg.Status.OK);
        resultMsg.setData(singlePaper);
        return resultMsg;
    }

    /**
     *
     * @param courseId
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/course/{courseId}", method = RequestMethod.GET)
    public ResultMsg getByCourseId(@PathVariable(name = "courseId") String courseId,
                                   @RequestParam int limit,
                                   @RequestParam int offset){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setStatus(ResultMsg.Status.OK);
        resultMsg.setData(singlePaperRepository.findByBelongInfoCourseId(
                courseId,
                new PageRequest(
                        offset/limit, limit,
                        new Sort(Sort.Direction.DESC, "id"))));
        return resultMsg;
    }

    @RequestMapping(value = "/course/{courseId}/chapter/{chapterId}", method = RequestMethod.GET)
    public ResultMsg getByCourseIdAndChapterId(
            @PathVariable(name = "courseId") String courseId,
            @PathVariable String chapterId,
            @RequestParam int limit,
            @RequestParam int offset){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setStatus(ResultMsg.Status.OK);
        resultMsg.setData(singlePaperRepository.findByBelongInfoCourseIdAndBelongInfoChapterId(courseId, chapterId,
                new PageRequest(offset/limit, limit, new Sort(Sort.Direction.DESC, "lastEditTime"))));
        return resultMsg;
    }

    @RequestMapping(value = "/course/{courseId}/chapter/{chapterId}/section/{sectionId}", method = RequestMethod.GET)
    public ResultMsg getByCourseIdAndChapterIdAndSectionId(
            @PathVariable(name = "courseId") String courseId,
            @PathVariable String chapterId,
            @PathVariable String sectionId,
            @RequestParam int limit,
            @RequestParam int offset){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setStatus(ResultMsg.Status.OK);
        resultMsg.setData(singlePaperRepository.findByBelongInfoCourseIdAndBelongInfoChapterIdAndBelongInfoSectionId(
                courseId,chapterId,sectionId,new PageRequest(offset/limit, limit, new Sort(Sort.Direction.DESC, "lastEditTime"))
        ));
        return resultMsg;
    }

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
        List<SinglePaper> singlePaperList = singlePaperRepository.findByBelongInfoCourseIdAndBelongInfoChapterIdAndBelongInfoSectionId(courseId, chapterId, sectionId);

        for(int i=0; i<singlePaperList.size(); i++){
            boolean flag = false;//试卷没有被做过
            SinglePaper singlePaper = singlePaperList.get(i);
            for(int j=0; j<paperRecordList.size(); j++){
                if(singlePaper.getId().equals(paperRecordList.get(j).getPaperId())){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                List<SingleChoice> singleChoices = singleChoiceRepository.findByPaperId(singlePaper.getId());
                singlePaper.setChoices(singleChoices);
                resultMsg.setData(singlePaper);
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
