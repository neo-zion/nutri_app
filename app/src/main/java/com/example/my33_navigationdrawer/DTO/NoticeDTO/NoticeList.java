package com.example.my33_navigationdrawer.DTO.NoticeDTO;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class NoticeList {

    @SerializedName("nbno")
    @Expose
    private Integer nbno;
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

    public Integer getNbno() {
        return nbno;
    }

    public void setNbno(Integer nbno) {
        this.nbno = nbno;
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

}