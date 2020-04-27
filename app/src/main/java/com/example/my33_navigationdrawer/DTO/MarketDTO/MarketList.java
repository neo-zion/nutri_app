package com.example.my33_navigationdrawer.DTO.MarketDTO;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarketList {

    @SerializedName("m_no")
    @Expose
    private Integer mNo;
    @SerializedName("m_title")
    @Expose
    private String mTitle;
    @SerializedName("m_content")
    @Expose
    private String mContent;
    @SerializedName("m_writer")
    @Expose
    private String mWriter;
    @SerializedName("m_regdate")
    @Expose
    private String mRegdate;
    @SerializedName("m_viewcnt")
    @Expose
    private Integer mViewcnt;
    @SerializedName("m_replycnt")
    @Expose
    private Integer mReplycnt;

    @SerializedName("attachList")
    @Expose
    private Object attachList;

    public Integer getMNo() {
        return mNo;
    }

    public void setMNo(Integer mNo) {
        this.mNo = mNo;
    }

    public String getMTitle() {
        return mTitle;
    }

    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMContent() {
        return mContent;
    }

    public void setMContent(String mContent) {
        this.mContent = mContent;
    }

    public String getMWriter() {
        return mWriter;
    }

    public void setMWriter(String mWriter) {
        this.mWriter = mWriter;
    }

    public String getMRegdate() {
        return mRegdate;
    }

    public void setMRegdate(String mRegdate) {
        this.mRegdate = mRegdate;
    }

    public Integer getMViewcnt() {
        return mViewcnt;
    }

    public void setMViewcnt(Integer mViewcnt) {
        this.mViewcnt = mViewcnt;
    }

    public Integer getMReplycnt() {
        return mReplycnt;
    }

    public void setMReplycnt(Integer mReplycnt) {
        this.mReplycnt = mReplycnt;
    }

    public Object getAttachList() {
        return attachList;
    }

    public void setAttachList(Object attachList) {
        this.attachList = attachList;
    }

}