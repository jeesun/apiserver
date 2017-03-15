package com.simon.domain;

/**
 * Created by simon on 2017/3/4.
 */
public class BlankItem {
    private Integer index;
    private String answer;

    public BlankItem() {
    }

    public BlankItem(Integer index, String answer) {
        this.index = index;
        this.answer = answer;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
