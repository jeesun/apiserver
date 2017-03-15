package com.simon.repository;

import com.simon.domain.AppUser;
import com.simon.domain.MultiPaperRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/5.
 */
public interface MultiPaperRecordRepository extends MongoRepository<MultiPaperRecord, String> {
    List<MultiPaperRecord> findByAppUser(AppUser appUser);
}
