package com.simon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by simon on 2017/3/5.
 */
//用户多选题选择的选项记录
@Document(collection = "qb_multi_record")
public class MultiRecord {
    @Id
    private String id;
    private String paperId;
    private String multiChoiceId;
    @DBRef
    private MultiChoice multiChoice;
    private Integer index;
    private Integer[] userChose;
    private Boolean result;
    private String userId;
    private String paperRecordId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getMultiChoiceId() {
        return multiChoiceId;
    }

    public void setMultiChoiceId(String multiChoiceId) {
        this.multiChoiceId = multiChoiceId;
    }

    public MultiChoice getMultiChoice() {
        return multiChoice;
    }

    public void setMultiChoice(MultiChoice multiChoice) {
        this.multiChoice = multiChoice;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer[] getUserChose() {
        return userChose;
    }

    public void setUserChose(Integer[] userChose) {
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
