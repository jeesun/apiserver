package com.simon.repository;

import com.simon.domain.FeedBack;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by simon on 2017/4/21.
 */
public interface FeedBackRepository extends MongoRepository<FeedBack, String> {
}
