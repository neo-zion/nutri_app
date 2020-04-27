package com.example.my33_navigationdrawer.DTO.SearchDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IList {

    @SerializedName("prdct_NM")
    @Expose
    private String prdctNM;
    @SerializedName("cret_DTM")
    @Expose
    private String cretDTM;
    @SerializedName("intk_LIMIT")
    @Expose
    private String intkLIMIT;
    @SerializedName("intk_MEMO")
    @Expose
    private String intkMEMO;
    @SerializedName("ino")
    @Expose
    private Integer ino;
    @SerializedName("skll_IX_IRDNT_RAWMTRL")
    @Expose
    private String skllIXIRDNTRAWMTRL;
    @SerializedName("day_INTK_HIGHLIMIT")
    @Expose
    private String dayINTKHIGHLIMIT;
    @SerializedName("primary_FNCLTY")
    @Expose
    private String primaryFNCLTY;
    @SerializedName("iftkn_ATNT_MATR_CN")
    @Expose
    private String iftknATNTMATRCN;
    @SerializedName("day_INTK_LOWLIMIT")
    @Expose
    private String dayINTKLOWLIMIT;

    public String getPrdctNM() {
        return prdctNM;
    }

    public void setPrdctNM(String prdctNM) {
        this.prdctNM = prdctNM;
    }

    public String getCretDTM() {
        return cretDTM;
    }

    public void setCretDTM(String cretDTM) {
        this.cretDTM = cretDTM;
    }

    public String getIntkLIMIT() {
        return intkLIMIT;
    }

    public void setIntkLIMIT(String intkLIMIT) {
        this.intkLIMIT = intkLIMIT;
    }

    public String getIntkMEMO() {
        return intkMEMO;
    }

    public void setIntkMEMO(String intkMEMO) {
        this.intkMEMO = intkMEMO;
    }

    public Integer getIno() {
        return ino;
    }

    public void setIno(Integer ino) {
        this.ino = ino;
    }

    public String getSkllIXIRDNTRAWMTRL() {
        return skllIXIRDNTRAWMTRL;
    }

    public void setSkllIXIRDNTRAWMTRL(String skllIXIRDNTRAWMTRL) {
        this.skllIXIRDNTRAWMTRL = skllIXIRDNTRAWMTRL;
    }

    public String getDayINTKHIGHLIMIT() {
        return dayINTKHIGHLIMIT;
    }

    public void setDayINTKHIGHLIMIT(String dayINTKHIGHLIMIT) {
        this.dayINTKHIGHLIMIT = dayINTKHIGHLIMIT;
    }

    public String getPrimaryFNCLTY() {
        return primaryFNCLTY;
    }

    public void setPrimaryFNCLTY(String primaryFNCLTY) {
        this.primaryFNCLTY = primaryFNCLTY;
    }

    public String getIftknATNTMATRCN() {
        return iftknATNTMATRCN;
    }

    public void setIftknATNTMATRCN(String iftknATNTMATRCN) {
        this.iftknATNTMATRCN = iftknATNTMATRCN;
    }

    public String getDayINTKLOWLIMIT() {
        return dayINTKLOWLIMIT;
    }

    public void setDayINTKLOWLIMIT(String dayINTKLOWLIMIT) {
        this.dayINTKLOWLIMIT = dayINTKLOWLIMIT;
    }

}