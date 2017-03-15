package com.simon.repository;

import com.simon.domain.MultiChoice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/12.
 */
public interface MultiChoiceRepository extends MongoRepository<MultiChoice, String> {
    List<MultiChoice> findByPaperId(String paperId);
}
