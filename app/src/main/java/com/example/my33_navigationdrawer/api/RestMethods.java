package com.example.my33_navigationdrawer.api;


import com.example.my33_navigationdrawer.DTO.MarketDTO.MarketDTO;
import com.example.my33_navigationdrawer.DTO.MemberDTO.MemberDTO;
import com.example.my33_navigationdrawer.DTO.MemberDTO.MemberInfo;
import com.example.my33_navigationdrawer.DTO.MyHealthDTO.NutrientList;
import com.example.my33_navigationdrawer.DTO.NoticeDTO.NoticeDTO;
import com.example.my33_navigationdrawer.DTO.SDiaryDTO.Daily;
import com.example.my33_navigationdrawer.DTO.SDiaryDTO.Monthly;
import com.example.my33_navigationdrawer.DTO.SDiaryDTO.Weekly;
import com.example.my33_navigationdrawer.DTO.SearchDTO.SearchDTO;
import com.example.my33_navigationdrawer.DTO.StreamDTO.StreamDTO;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RestMethods {

    //Call<받는 타입> 메소드가 넘겨주는 반환타입(json)을 확인하고 맞춰줘야 함.

    //TODO Replace with your API's Login Method
    @FormUrlEncoded
    @POST("member/loginApp")
    Call<MemberDTO> login(@Field("email") String email, @Field("passwd") String password);

    @FormUrlEncoded
    @POST("member/pwFindApp")
    Call<Integer> pwFind(@Field("email") String email);

    @GET("member/emailCheck")
    Call<Integer> emailCheck(@Query("email") String email);

    @GET("member/nickCheck")
    Call<Integer> nickCheck(@Query("nickname") String nickname);

    @FormUrlEncoded
    @POST("member/signupApp")
    Call<Integer> join(@Field("email") String email, @Field("passwd") String password, @Field("nickname") String nickname, @Field("gender") String gender, @Field("dateofbirth") String dateofbirth);

    @Multipart
    @POST("member/modifyMemberApp")
    Call<Integer> modifyMember(@Part("nickname") RequestBody nickname, @Part("image_path") RequestBody image_path , @Part MultipartBody.Part uploadFile);

    @FormUrlEncoded
    @POST("member/update_pwApp")
    Call<Integer> changePw(@Field("email") String email, @Field("old_pw") String old_pw, @Field("passwd") String passwd);

    @FormUrlEncoded
    @POST("member/delmemberApp")
    Call<Integer> leave(@Field("email") String email, @Field("passwd")String passwd);

    @GET("notice_board/listApp")
    Call<NoticeDTO> noticeList();

    @GET("market_board/listApp")
    Call<MarketDTO> marketList();

    @GET("hff_board/AllListApp")
    Call<SearchDTO> search(@Query("search") String search);

    @GET("myhealth/sDiaryDailyApp")
    Call<Daily> sDiaryDaily(@Query("nickname")String nickname);

    @GET("myhealth/sDiaryWeeklyApp")
    Call<Weekly> sDiaryWeekly(@Query("nickname")String nickname);

    @GET("myhealth/sDiaryMonthlyApp")
    Call<Monthly> sDiaryMonthly(@Query("nickname")String nickname);

    @GET("myhealth/nutrientApp")
    Call<NutrientList> nutrient(@Query("nickname")String nickname);

    @GET("video/listApp")
    Call<StreamDTO> streamView();

}