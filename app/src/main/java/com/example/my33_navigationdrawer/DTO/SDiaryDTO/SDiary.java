package com.example.my33_navigationdrawer.DTO.SDiaryDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SDiary {
    @SerializedName("daily")
    @Expose
    private Daily daily;
    @SerializedName("monthly")
    @Expose
    private Monthly monthly;
    @SerializedName("weekly")
    @Expose
    private Weekly weekly;

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public Monthly getMonthly() {
        return monthly;
    }

    public void setMonthly(Monthly monthly) {
        this.monthly = monthly;
    }

    public Weekly getWeekly() {
        return weekly;
    }

    public void setWeekly(Weekly weekly) {
        this.weekly = weekly;
    }

}
