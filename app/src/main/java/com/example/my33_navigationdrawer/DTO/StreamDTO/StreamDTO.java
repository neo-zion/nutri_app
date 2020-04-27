package com.example.my33_navigationdrawer.DTO.StreamDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StreamDTO {
    @SerializedName("vList")
    @Expose
    private List<VList> vList = null;

    public List<VList> getVList() {
        return vList;
    }

    public void setVList(List<VList> vList) {
        this.vList = vList;
    }

}