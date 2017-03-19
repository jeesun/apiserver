package com.simon.controller;

import com.simon.domain.Chapter;
import com.simon.domain.Course;
import com.simon.domain.ResultMsg;
import com.simon.domain.Section;
import com.simon.repository.ChapterRepository;
import com.simon.repository.CourseRepository;
import com.simon.repository.SectionRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 2017/3/16.
 */
@Api(description = "课程")
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResultMsg get(){
        ResultMsg resultMsg = new ResultMsg();

        List<CourseDetail> courseDetails = new ArrayList<>();
        List<Course> courses = courseRepository.findAll();
        /*for(int i=0; i<courses.size(); i++){
            CourseDetail courseDetail = new CourseDetail();
            courseDetail.setCourse(courses.get(i));
            List<ChapterDetail> chapterDetails = new ArrayList<>();
            List<Chapter> chapters = chapterRepository.findByCourseId(courses.get(i).getId());
            convert(chapterDetails, chapters);
            courseDetail.setChapterDetails(chapterDetails);
            courseDetails.add(courseDetail);
        }*/

        resultMsg.setStatus(ResultMsg.Status.OK);
        resultMsg.setData(courseDetails);

        return resultMsg;
    }

    @RequestMapping(value = "/{courseId}/chapters", method = RequestMethod.GET)
    public ResultMsg get(@PathVariable String courseId){
        ResultMsg resultMsg = new ResultMsg();

        CourseDetail courseDetail = new CourseDetail();
        courseDetail.setCourse(courseRepository.findOne(courseId));
        List<ChapterDetail> chapterDetails = new ArrayList<>();
        List<Chapter> chapters = chapterRepository.findByCourseId(courseId);
        convert(chapterDetails, chapters);
        courseDetail.setChapterDetails(chapterDetails);

        resultMsg.setStatus(ResultMsg.Status.OK);
        resultMsg.setData(courseDetail);

        return resultMsg;
    }

    @RequestMapping(value = "/{courseId}/chapters/{chapterId}/sections", method = RequestMethod.GET)
    public ResultMsg get(@PathVariable String courseId, @PathVariable String chapterId){
        ResultMsg resultMsg = new ResultMsg();

        ChapterDetail chapterDetail = new ChapterDetail();
        chapterDetail.setChapter(chapterRepository.findOne(chapterId));
        chapterDetail.setSections(sectionRepository.findByChapterId(chapterId));

        resultMsg.setStatus(ResultMsg.Status.OK);
        resultMsg.setData(chapterDetail);
        return resultMsg;
    }

    private void convert(List<ChapterDetail> chapterDetails, List<Chapter> chapters){
        for(int i=0;i<chapters.size();i++){
            ChapterDetail chapterDetail = new ChapterDetail();
            chapterDetail.setChapter(chapters.get(i));
            chapterDetail.setSections(sectionRepository.findByChapterId(chapters.get(i).getId()));
            chapterDetails.add(chapterDetail);
        }
    }

    private class ChapterDetail {
        private Chapter chapter;
        private List<Section> sections;

        public Chapter getChapter() {
            return chapter;
        }

        public void setChapter(Chapter chapter) {
            this.chapter = chapter;
        }

        public List<Section> getSections() {
            return sections;
        }

        public void setSections(List<Section> sections) {
            this.sections = sections;
        }
    }

    private class CourseDetail {
        private Course course;
        private List<ChapterDetail> chapterDetails;

        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public List<ChapterDetail> getChapterDetails() {
            return chapterDetails;
        }

        public void setChapterDetails(List<ChapterDetail> chapterDetails) {
            this.chapterDetails = chapterDetails;
        }
    }

}
