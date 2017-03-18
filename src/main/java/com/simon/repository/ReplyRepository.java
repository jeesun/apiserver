package com.simon.repository;

import com.simon.domain.AppUser;
import com.simon.domain.Reply;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.awt.print.Pageable;
import java.util.List;

/**
 * Created by simon on 2017/3/18.
 */
public interface ReplyRepository extends MongoRepository<Reply, String> {
    List<Reply> findByReplyId(String replyId);
    List<Reply> findByFromUser(AppUser fromUser);
    List<Reply> findByToUser(AppUser toUser);
    List<Reply> findByFromUserId(String fromUserId);
    List<Reply> findByToUserId(String toUserId);
    List<Reply> findByCommentId(String commentId);
    List<Reply> findByCommentId(String commentId, Pageable pageable);
}
