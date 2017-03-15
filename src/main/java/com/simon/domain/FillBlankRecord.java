package com.simon.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Created by simon on 2017/3/5.
 */
//用户填空题填写内容的记录
public class FillBlankRecord {
    @DBRef
    private FillBlank fillBlank;
    private String userFill;
    private boolean result;

    public FillBlank getFillBlank() {
        return fillBlank;
    }

    public void setFillBlank(FillBlank fillBlank) {
        this.fillBlank = fillBlank;
    }

    public String getUserFill() {
        return userFill;
    }

    public void setUserFill(String userFill) {
        this.userFill = userFill;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
