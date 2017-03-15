package com.simon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;

/**
 * Created by simon on 2017/3/4.
 */
//综合试卷（选择题+填空题，选择题可能有多选题）
public class GeneralPaper {
    @Id
    private String id;

    private String paperName;



    @DBRef
    private ArrayList<MultiChoice> singleChoices;

    @DBRef
    private ArrayList<MultiChoice> multiChoices;

    @DBRef
    private FillBlank[] fillBlanks;

    private Long lastEditTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public ArrayList<MultiChoice> getSingleChoices() {
        return singleChoices;
    }

    public void setSingleChoices(ArrayList<MultiChoice> singleChoices) {
        this.singleChoices = singleChoices;
    }

    public ArrayList<MultiChoice> getMultiChoices() {
        return multiChoices;
    }

    public void setMultiChoices(ArrayList<MultiChoice> multiChoices) {
        this.multiChoices = multiChoices;
    }

    public FillBlank[] getFillBlanks() {
        return fillBlanks;
    }

    public void setFillBlanks(FillBlank[] fillBlanks) {
        this.fillBlanks = fillBlanks;
    }

    public Long getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
