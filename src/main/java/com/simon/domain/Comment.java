package com.simon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by simon on 2017/3/18.
 */
@Document(collection = "qb_comment")
public class Comment {
    @Id
    private String id;

    private String questionId;

    private int questionType;//0单选题，1多选题，2填空题

    private String content;

    @DBRef
    private AppUser commentUser;

    private String userId;

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

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AppUser getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(AppUser commentUser) {
        this.commentUser = commentUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
