package com.simon.repository;

import com.simon.domain.MultiPaper;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/12.
 */
public interface MultiPaperRepository extends MongoRepository<MultiPaper, String> {
    List<MultiPaper> findByBelongInfoCourseIdAndBelongInfoChapterIdAndBelongInfoSectionId(String courseId, String chapterId, String sectionId);
    List<MultiPaper> findByBelongInfoCourseId(String courseId);
}
