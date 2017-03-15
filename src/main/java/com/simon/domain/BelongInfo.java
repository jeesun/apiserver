package com.simon.domain;

/**
 * Created by simon on 2017/3/11.
 */
public class BelongInfo {
    private String courseId;

    private String chapterId;

    private String sectionId;

    public BelongInfo(String courseId, String chapterId, String sectionId) {
        this.courseId = courseId;
        this.chapterId = chapterId;
        this.sectionId = sectionId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
}
