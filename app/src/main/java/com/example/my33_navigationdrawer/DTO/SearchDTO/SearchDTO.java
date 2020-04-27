package com.example.my33_navigationdrawer.DTO.SearchDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchDTO {

    @SerializedName("iList")
    @Expose
    private List<IList> iList = null;
    @SerializedName("mList")
    @Expose
    private List<MList> mList = null;

    public List<IList> getIList() {
        return iList;
    }

    public void setIList(List<IList> iList) {
        this.iList = iList;
    }

    public List<MList> getMList() {
        return mList;
    }

    public void setMList(List<MList> mList) {
        this.mList = mList;
    }

}
