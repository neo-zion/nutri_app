package com.example.my33_navigationdrawer.DTO.StreamDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VList {
    @SerializedName("mvno")
    @Expose
    private String mvno;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("writer")
    @Expose
    private String writer;
    @SerializedName("regdate")
    @Expose
    private String regdate;
    @SerializedName("viewcnt")
    @Expose
    private Integer viewcnt;
    @SerializedName("replycnt")
    @Expose
    private Integer replycnt;

    public String getMvno() {
        return mvno;
    }

    public void setMvno(String mvno) {
        this.mvno = mvno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public Integer getViewcnt() {
        return viewcnt;
    }

    public void setViewcnt(Integer viewcnt) {
        this.viewcnt = viewcnt;
    }

    public Integer getReplycnt() {
        return replycnt;
    }

    public void setReplycnt(Integer replycnt) {
        this.replycnt = replycnt;
    }

}
