package com.simon.repository;

import com.simon.domain.Section;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/12.
 */
public interface SectionRepository extends MongoRepository<Section, String> {
    List<Section> findByChapterId(String chapterId);
}
