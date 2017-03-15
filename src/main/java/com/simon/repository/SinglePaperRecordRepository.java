package com.simon.repository;

import com.simon.domain.AppUser;
import com.simon.domain.SinglePaperRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/5.
 */
public interface SinglePaperRecordRepository extends MongoRepository<SinglePaperRecord, String> {
    List<SinglePaperRecord> findByAppUser(AppUser appUser);
}
