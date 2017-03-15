package com.simon.repository;

import com.simon.domain.SingleChoice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/12.
 */
public interface SingleChoiceRepository extends MongoRepository<SingleChoice, String> {
    List<SingleChoice> findByPaperId(String paperId);
}
