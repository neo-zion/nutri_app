package com.example.my33_navigationdrawer.DTO.SDiaryDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Monthly {
    @SerializedName("monthlyList")
    @Expose
    private List<MonthlyList> monthlyList = null;

    public List<MonthlyList> getMonthlyList() {
        return monthlyList;
    }

    public void setMonthlyList(List<MonthlyList> monthlyList) {
        this.monthlyList = monthlyList;
    }

}