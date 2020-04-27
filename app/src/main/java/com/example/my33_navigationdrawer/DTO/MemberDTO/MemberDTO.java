package com.example.my33_navigationdrawer.DTO.MemberDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberDTO {

    @SerializedName("list")
    @Expose
    private MemberInfo memberInfo;

    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }


}