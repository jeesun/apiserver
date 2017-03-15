package com.simon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by simon on 2017/3/5.
 */
//单选题试卷做题记录
@Document(collection = "qb_single_paper_record")
public class SinglePaperRecord {
    @Id
    private String id;

    @DBRef
    private AppUser appUser;

    private SingleRecord[] records;

    private Long generateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public SingleRecord[] getRecords() {
        return records;
    }

    public void setRecords(SingleRecord[] records) {
        this.records = records;
    }

    public Long getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(Long generateTime) {
        this.generateTime = generateTime;
    }
}
