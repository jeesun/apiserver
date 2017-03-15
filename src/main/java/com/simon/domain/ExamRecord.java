package com.simon.domain;

import org.springframework.data.annotation.Id;

/**
 * Created by simon on 2017/3/4.
 */
//做过的试卷
public class ExamRecord {
    @Id
    private String id;

    private Integer tag;//0,单选；1,多选；2，填空题；3，综合题。

    private String paperId;

    private FillBlankRecord[] blankItems;

    private SingleRecord[] choiceItems;

}
