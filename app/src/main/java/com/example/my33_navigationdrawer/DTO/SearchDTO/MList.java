package com.example.my33_navigationdrawer.DTO.SearchDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MList {
    @SerializedName("bssh_NM")
    @Expose
    private String bsshNM;
    @SerializedName("induty_NM")
    @Expose
    private String indutyNM;
    @SerializedName("addr")
    @Expose
    private String addr;
    @SerializedName("fnclty_CN")
    @Expose
    private String fncltyCN;
    @SerializedName("day_INTK_CN")
    @Expose
    private String dayINTKCN;
    @SerializedName("mno")
    @Expose
    private Integer mno;
    @SerializedName("prms_DT")
    @Expose
    private String prmsDT;
    @SerializedName("hf_FNCLTY_MTRAL_RCOGN_NO")
    @Expose
    private String hfFNCLTYMTRALRCOGNNO;
    @SerializedName("aplc_RAWMTRL_NM")
    @Expose
    private String aplcRAWMTRLNM;
    @SerializedName("iftkn_ATNT_MATR_CN")
    @Expose
    private String iftknATNTMATRCN;

    public String getBsshNM() {
        return bsshNM;
    }

    public void setBsshNM(String bsshNM) {
        this.bsshNM = bsshNM;
    }

    public String getIndutyNM() {
        return indutyNM;
    }

    public void setIndutyNM(String indutyNM) {
        this.indutyNM = indutyNM;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getFncltyCN() {
        return fncltyCN;
    }

    public void setFncltyCN(String fncltyCN) {
        this.fncltyCN = fncltyCN;
    }

    public String getDayINTKCN() {
        return dayINTKCN;
    }

    public void setDayINTKCN(String dayINTKCN) {
        this.dayINTKCN = dayINTKCN;
    }

    public Integer getMno() {
        return mno;
    }

    public void setMno(Integer mno) {
        this.mno = mno;
    }

    public String getPrmsDT() {
        return prmsDT;
    }

    public void setPrmsDT(String prmsDT) {
        this.prmsDT = prmsDT;
    }

    public String getHfFNCLTYMTRALRCOGNNO() {
        return hfFNCLTYMTRALRCOGNNO;
    }

    public void setHfFNCLTYMTRALRCOGNNO(String hfFNCLTYMTRALRCOGNNO) {
        this.hfFNCLTYMTRALRCOGNNO = hfFNCLTYMTRALRCOGNNO;
    }

    public String getAplcRAWMTRLNM() {
        return aplcRAWMTRLNM;
    }

    public void setAplcRAWMTRLNM(String aplcRAWMTRLNM) {
        this.aplcRAWMTRLNM = aplcRAWMTRLNM;
    }

    public String getIftknATNTMATRCN() {
        return iftknATNTMATRCN;
    }

    public void setIftknATNTMATRCN(String iftknATNTMATRCN) {
        this.iftknATNTMATRCN = iftknATNTMATRCN;
    }

}