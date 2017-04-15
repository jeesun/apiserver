package com.simon.repository;

import com.simon.domain.SingleRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/24.
 */
public interface SingleRecordRepository extends MongoRepository<SingleRecord, String> {
    List<SingleRecord> findByPaperId(String paperId);
    List<SingleRecord> findByUserId(String userId);
    int countByUserId(String userId);
}
