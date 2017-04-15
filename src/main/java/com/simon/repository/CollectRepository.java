package com.simon.repository;

import com.simon.domain.Collect;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/4/9.
 */
public interface CollectRepository extends MongoRepository<Collect, String> {
    List<Collect> getByUserId(String userId);
    List<Collect> getByUserId(String userId, Pageable pageable);
    int countByUserId(String userId);
    void deleteByUserIdAndQuestionId(String userId, String questionId);
}
