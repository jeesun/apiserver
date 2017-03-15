package com.simon.controller;

import com.simon.domain.ResultMsg;
import com.simon.domain.SinglePaper;
import com.simon.repository.SingleChoiceRepository;
import com.simon.repository.SinglePaperRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultMsg getById(@PathVariable String id){
        ResultMsg resultMsg = new ResultMsg();
        SinglePaper singlePaper = singlePaperRepository.findOne(id);
        singlePaper.setChoices(singleChoiceRepository.findByPaperId(id));
        resultMsg.setStatus(ResultMsg.Status.OK);
        resultMsg.setData(singlePaper);
        return resultMsg;
    }

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
}
