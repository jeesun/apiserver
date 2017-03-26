package com.simon.repository;

import com.simon.domain.MultiRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/26.
 */
public interface MultiRecordRepository extends MongoRepository<MultiRecord, String> {
    List<MultiRecord> findByUserId(String userId);
}
