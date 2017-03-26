package com.simon.repository;

import com.simon.domain.FillBlankPaper;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/5.
 */
public interface FillBlankPaperRepository extends MongoRepository<FillBlankPaper, String> {
    List<FillBlankPaper> findByBelongInfoCourseIdAndBelongInfoChapterIdAndBelongInfoSectionId(String courseId,
                                                                                              String chapterId,
                                                                                              String sectionId);
}
