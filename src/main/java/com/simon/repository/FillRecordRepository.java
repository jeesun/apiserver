package com.simon.repository;

import com.simon.domain.FillBlankRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/26.
 */
public interface FillRecordRepository extends MongoRepository<FillBlankRecord, String> {
    List<FillBlankRecord> findByUserId(String userId);
    int countByUserId(String userId);
}
