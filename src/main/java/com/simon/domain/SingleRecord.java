package com.simon.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Created by simon on 2017/3/4.
 */
//用户单选题选择的选项记录（单条题目）
public class SingleRecord {
    @DBRef
    private MultiChoice multiChoice;
    private Integer[] order;
    private Integer choiceIndex;
    private boolean result;

    public MultiChoice getMultiChoice() {
        return multiChoice;
    }

    public void setMultiChoice(MultiChoice multiChoice) {
        this.multiChoice = multiChoice;
    }

    public Integer[] getOrder() {
        return order;
    }

    public void setOrder(Integer[] order) {
        this.order = order;
    }

    public Integer getChoiceIndex() {
        return choiceIndex;
    }

    public void setChoiceIndex(Integer choiceIndex) {
        this.choiceIndex = choiceIndex;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
