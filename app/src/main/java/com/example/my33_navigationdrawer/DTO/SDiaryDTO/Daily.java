package com.example.my33_navigationdrawer.DTO.SDiaryDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Daily {
    @SerializedName("dailyList")
    @Expose
    private List<DailyList> dailyList = null;

    public List<DailyList> getDailyList() {
        return dailyList;
    }

    public void setDailyList(List<DailyList> dailyList) {
        this.dailyList = dailyList;
    }

}