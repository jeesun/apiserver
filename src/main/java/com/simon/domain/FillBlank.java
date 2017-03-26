package com.simon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by simon on 2017/3/4.
 */
//填空题
@Document(collection = "qb_fill_blank")
public class FillBlank {
    @Id
    private String id;

    private String question;

    private List<BlankItem> blankItems;

    private Integer blankCount;

    private String analysis;

    private Long lastEditTime;

    private String paperId;

    public FillBlank() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<BlankItem> getBlankItems() {
        return blankItems;
    }

    public void setBlankItems(List<BlankItem> blankItems) {
        this.blankItems = blankItems;
    }

    public Integer getBlankCount() {
        return blankCount;
    }

    public void setBlankCount(Integer blankCount) {
        this.blankCount = blankCount;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Long getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }
}
