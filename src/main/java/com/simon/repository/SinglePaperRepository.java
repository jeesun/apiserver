package com.simon.repository;

import com.simon.domain.SinglePaper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/5.
 */
public interface SinglePaperRepository extends MongoRepository<SinglePaper, String> {
    List<SinglePaper> findByBelongInfoCourseId(String courseId);
    List<SinglePaper> findByBelongInfoCourseId(String courseId, Pageable pageable);
    List<SinglePaper> findByBelongInfoCourseIdAndBelongInfoChapterId(String courseId, String chapterId);
    List<SinglePaper> findByBelongInfoCourseIdAndBelongInfoChapterId(String courseId, String chapterId, Pageable pageable);
    List<SinglePaper> findByBelongInfoCourseIdAndBelongInfoChapterIdAndBelongInfoSectionId(String courseId, String chapterId, String sectionId);
    List<SinglePaper> findByBelongInfoCourseIdAndBelongInfoChapterIdAndBelongInfoSectionId(String courseId, String chapterId, String sectionId, Pageable pageable);
}
