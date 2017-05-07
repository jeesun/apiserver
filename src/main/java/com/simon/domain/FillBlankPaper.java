package com.simon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by simon on 2017/3/5.
 */
//填空题试卷
@Document(collection = "qb_fill_blank_paper")
public class FillBlankPaper {
    @Id
    private String id;

    private String paperName;

    private BelongInfo belongInfo;

    @DBRef
    private List<FillBlank> fillBlanks;

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

    public List<FillBlank> getFillBlanks() {
        return fillBlanks;
    }

    public void setFillBlanks(List<FillBlank> fillBlanks) {
        this.fillBlanks = fillBlanks;
    }

    public Long getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
