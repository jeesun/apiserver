package com.simon.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by simon on 2017/3/23.
 */
@Document(collection = "qb_single_record")
public class SingleRecord {
    private String paperId;
    private String singleChoiceId;
    @DBRef
    private SingleChoice singleChoice;
    private Integer index;
    private Integer userChose;
    private Boolean result;
    private String userId;
    private String paperRecordId;

    public SingleRecord() {
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getSingleChoiceId() {
        return singleChoiceId;
    }

    public void setSingleChoiceId(String singleChoiceId) {
        this.singleChoiceId = singleChoiceId;
    }

    public SingleChoice getSingleChoice() {
        return singleChoice;
    }

    public void setSingleChoice(SingleChoice singleChoice) {
        this.singleChoice = singleChoice;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getUserChose() {
        return userChose;
    }

    public void setUserChose(Integer userChose) {
        this.userChose = userChose;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaperRecordId() {
        return paperRecordId;
    }

    public void setPaperRecordId(String paperRecordId) {
        this.paperRecordId = paperRecordId;
    }
}
