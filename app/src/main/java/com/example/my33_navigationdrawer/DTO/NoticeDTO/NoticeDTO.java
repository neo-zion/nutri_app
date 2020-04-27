package com.example.my33_navigationdrawer.DTO.NoticeDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoticeDTO {

    @SerializedName("list")
    @Expose
    private List<NoticeList> list = null;

    public List<NoticeList> getList() {
        return list;
    }

    public void setList(List<NoticeList> list) {
        this.list = list;
    }

}



