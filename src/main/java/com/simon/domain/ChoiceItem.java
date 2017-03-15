package com.simon.domain;

/**
 * Created by simon on 2017/3/4.
 */
public class ChoiceItem {
    private String content;

    private boolean answer;

    public ChoiceItem() {
    }

    public ChoiceItem(String content, boolean answer) {
        this.content = content;
        this.answer = answer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "ChoiceItem{" +
                "content='" + content + '\'' +
                ", answer=" + answer +
                '}';
    }
}
