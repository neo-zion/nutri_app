package com.example.my33_navigationdrawer.DTO.SDiaryDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weekly {
    @SerializedName("weeklyList")
    @Expose
    private List<WeeklyList> weeklyList = null;

    public List<WeeklyList> getWeeklyList() {
        return weeklyList;
    }

    public void setWeeklyList(List<WeeklyList> weeklyList) {
        this.weeklyList = weeklyList;
    }

}