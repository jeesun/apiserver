package com.simon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

/**
 * Created by simon on 2017/3/4.
 */
//多选题
@Document(collection = "qb_multiple_choice")
public class MultiChoice {
    @Id
    private String id;

    private String question;

    private ArrayList<ChoiceItem> choiceItems;

    private Integer answerCount;

    private Long lastEditTime;

    private String analysis;

    private String paperId;

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

    public ArrayList<ChoiceItem> getChoiceItems() {
        return choiceItems;
    }

    public void setChoiceItems(ArrayList<ChoiceItem> choiceItems) {
        this.choiceItems = choiceItems;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public Long getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    @Override
    public String toString() {
        return "MultiChoice{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", choiceItems=" + choiceItems +
                ", answerCount=" + answerCount +
                '}';
    }
}
