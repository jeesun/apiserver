package com.simon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by simon on 2017/3/5.
 */
//多选题试卷做题记录
@Document(collection = "qb_multi_paper_record")
public class MultiPaperRecord {
    @Id
    private String id;

    @DBRef
    private AppUser appUser;

    private MultiRecord[] records;

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

    public MultiRecord[] getRecords() {
        return records;
    }

    public void setRecords(MultiRecord[] records) {
        this.records = records;
    }

    public Long getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(Long generateTime) {
        this.generateTime = generateTime;
    }
}
