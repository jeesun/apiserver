package com.simon.repository;

import com.simon.domain.FillBlank;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/8.
 */
public interface FillBlankRepository extends MongoRepository<FillBlank, String> {
    List<FillBlank> findByPaperId(String paperId);
}
