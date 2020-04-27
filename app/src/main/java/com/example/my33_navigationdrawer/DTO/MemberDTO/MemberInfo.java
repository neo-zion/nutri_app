package com.example.my33_navigationdrawer.DTO.MemberDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MemberInfo implements Serializable {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("passwd")
    @Expose
    private String passwd;
    @SerializedName("gender")
    @Expose
    private int gender;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("dateofbirth")
    @Expose
    private String dateofbirth;
    @SerializedName("indate")
    @Expose
    private String indate;
    @SerializedName("useyn")
    @Expose
    private String useyn;
    @SerializedName("image_path")
    @Expose
    private String image_path;
    @SerializedName("user_key")
    @Expose
    private String user_key;


    public MemberInfo(String email, String passwd, String nickname) {
        this.email = email;
        this.passwd = passwd;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public String getUseyn() {
        return useyn;
    }

    public void setUseyn(String useyn) {
        this.useyn = useyn;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }
}