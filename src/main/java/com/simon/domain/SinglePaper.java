package com.simon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by simon on 2017/3/4.
 */
//选择题试卷
@Document(collection = "qb_choice_paper")
public class SinglePaper {
    @Id
    private String id;

    private String paperName;

    private BelongInfo belongInfo;

    @DBRef
    private List<SingleChoice> choices;

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

    public BelongInfo getBelongInfo() {
        return belongInfo;
    }

    public void setBelongInfo(BelongInfo belongInfo) {
        this.belongInfo = belongInfo;
    }

    public List<SingleChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<SingleChoice> choices) {
        this.choices = choices;
    }

    public Long getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
