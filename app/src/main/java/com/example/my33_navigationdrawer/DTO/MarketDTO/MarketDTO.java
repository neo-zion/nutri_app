package com.example.my33_navigationdrawer.DTO.MarketDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarketDTO {

    @SerializedName("marketList")
    @Expose
    private List<MarketList> marketList = null;

    public List<MarketList> getMarketList() {
        return marketList;
    }

    public void setMarketList(List<MarketList> marketList) {
        this.marketList = marketList;
    }

}