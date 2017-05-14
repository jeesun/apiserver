package com.simon.domain;

/**
 * Created by simon on 2017/5/14.
 */
public class DetailPaperRecord {
    private PaperRecord paperRecord;
    private String paperName;
    private BelongInfo belongInfo;

    public PaperRecord getPaperRecord() {
        return paperRecord;
    }

    public void setPaperRecord(PaperRecord paperRecord) {
        this.paperRecord = paperRecord;
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
}
