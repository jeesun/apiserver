package com.simon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by simon on 2017/3/18.
 */
@Document(collection = "qb_reply")
public class Reply implements Comparable {
    @Id
    private String id;

    private String questionId;

    //冗余字段，用来一次性找到一个评论下面的所有回复
    private String commentId;

    private String replyId;//当是对评论的回复时，replyId=commentId

    //表示回复的类型，因为回复可以是针对评论的回复(comment)，也可以是针对回复的回复(reply)， 通过这个字段来区分两种情景。
    private Integer replyType;//0针对评论的回复，1针对回复的回复

    private String content;

    @DBRef
    private AppUser fromUser;

    @DBRef
    private AppUser toUser;

    private String fromUserId;

    private String toUserId;

    private Long lastEditTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public Integer getReplyType() {
        return replyType;
    }

    public void setReplyType(Integer replyType) {
        this.replyType = replyType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AppUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(AppUser fromUser) {
        this.fromUser = fromUser;
    }

    public AppUser getToUser() {
        return toUser;
    }

    public void setToUser(AppUser toUser) {
        this.toUser = toUser;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public Long getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    @Override
    public int compareTo(Object o) {
        Reply reply = (Reply) o;
        return this.lastEditTime.compareTo(reply.lastEditTime);
    }
}
