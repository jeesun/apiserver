package com.simon.repository;

import com.simon.domain.MultiChoice;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by simon on 2017/3/4.
 */
public interface MultipleChoiceRepository extends MongoRepository<MultiChoice, String> {
}
