package com.simon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

/**
 * Created by simon on 2017/3/4.
 */
//选择题试卷
@Document(collection = "qb_choice_paper")
public class ChoicePaper {
    @Id
    private String id;

    private String paperName;

    @DBRef
    private ArrayList<MultiChoice> choices;

    private Long lastEditTime;

    private boolean single;//是否是单选

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

    public ArrayList<MultiChoice> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<MultiChoice> choices) {
        this.choices = choices;
    }

    public Long getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }
}
