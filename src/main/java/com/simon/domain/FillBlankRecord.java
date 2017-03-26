package com.simon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by simon on 2017/3/5.
 */
//用户填空题填写内容的记录
@Document(collection = "qb_fill_blank_record")
public class FillBlankRecord {
    @Id
    private String id;
    private String paperId;
    private String fillBlankId;
    @DBRef
    private FillBlank fillBlank;
    private Integer index;
    private String[] userFilled;
    private Boolean[] result;
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

    public String getFillBlankId() {
        return fillBlankId;
    }

    public void setFillBlankId(String fillBlankId) {
        this.fillBlankId = fillBlankId;
    }

    public FillBlank getFillBlank() {
        return fillBlank;
    }

    public void setFillBlank(FillBlank fillBlank) {
        this.fillBlank = fillBlank;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String[] getUserFilled() {
        return userFilled;
    }

    public void setUserFilled(String[] userFilled) {
        this.userFilled = userFilled;
    }

    public Boolean[] getResult() {
        return result;
    }

    public void setResult(Boolean[] result) {
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
