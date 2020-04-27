package com.example.my33_navigationdrawer.DTO.SDiaryDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeeklyList {

    @SerializedName("pushup")
    @Expose
    private Integer pushup;
    @SerializedName("standdown")
    @Expose
    private Integer standdown;
    @SerializedName("updatedate")
    @Expose
    private String updatedate;
    @SerializedName("nickname")
    @Expose
    private String nickname;

    public Integer getPushup() {
        return pushup;
    }

    public void setPushup(Integer pushup) {
        this.pushup = pushup;
    }

    public Integer getStanddown() {
        return standdown;
    }

    public void setStanddown(Integer standdown) {
        this.standdown = standdown;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}