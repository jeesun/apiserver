package com.simon.repository;

import com.simon.domain.MultiPaper;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by simon on 2017/3/12.
 */
public interface MultiPaperRepository extends MongoRepository<MultiPaper, String> {
}
