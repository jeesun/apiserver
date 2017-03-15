package com.simon.repository;

import com.simon.domain.FillBlank;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by simon on 2017/3/8.
 */
public interface FillBlankRepository extends MongoRepository<FillBlank, String> {

}
