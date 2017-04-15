package com.simon.repository;

import com.simon.domain.PaperRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/23.
 */
public interface PaperRecordRepository extends MongoRepository<PaperRecord, String> {
    List<PaperRecord> findByUserId(String userId);
    int countByUserId(String userId);
}
