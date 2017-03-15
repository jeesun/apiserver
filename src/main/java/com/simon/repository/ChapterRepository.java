package com.simon.repository;

import com.simon.domain.Chapter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/12.
 */
public interface ChapterRepository extends MongoRepository<Chapter, String> {
    List<Chapter> findByCourseId(String courseId);
}
