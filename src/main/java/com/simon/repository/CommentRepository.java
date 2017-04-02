package com.simon.repository;

import com.simon.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by simon on 2017/3/18.
 */
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByQuestionId(String questionId);
    List<Comment> findByUserId(String userId);
}
