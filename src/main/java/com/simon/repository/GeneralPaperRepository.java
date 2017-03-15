package com.simon.repository;

import com.simon.domain.GeneralPaper;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by simon on 2017/3/9.
 */
public interface GeneralPaperRepository extends MongoRepository<GeneralPaper, String> {
}
