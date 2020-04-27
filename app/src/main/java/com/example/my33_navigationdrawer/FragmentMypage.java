package com.example.my33_navigationdrawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.my33_navigationdrawer.api.RestMethods;
import com.example.my33_navigationdrawer.retrofit2.RestClient;
import com.example.my33_navigationdrawer.utils.PreferenceManager;

public class FragmentMypage extends Fragment {

    private static final String TAG = "FragmentMypage";
    private Button btnBack, mpLogout, modifyInfo, changePw, btnLeave;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Context mContext;
    private ImageView iv_img;
    private ProgressDialog progressDialog;
    private RestMethods restMethods;
    private TextView tv_nickname, tv_gender, tv_dateofbirth;
    private FragmentChangePw fragmentChangePw;
    private FragmentModifiyInfo fragmentModifiyInfo;
    private Bundle bundle;
    private FragmentLeave fragmentLeave;
    private String nickname, dateofbirth, image_path;
    int gender;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mypage,
                container, false);

        bundle = new Bundle();
        tv_nickname = rootView.findViewById(R.id.tv_nickname);
        tv_gender = rootView.findViewById(R.id.tv_gender);
        tv_dateofbirth = rootView.findViewById(R.id.tv_dateofbirth);
        iv_img = rootView.findViewById(R.id.iv_img);
        mpLogout = rootView.findViewById(R.id.mpLogout);
        btnBack = rootView.findViewById(R.id.mypageBack);
        modifyInfo = rootView.findViewById(R.id.modifyInfo);
        changePw = rootView.findViewById(R.id.changePw);
        btnLeave = rootView.findViewById(R.id.btnLeave);
        fragmentModifiyInfo = new FragmentModifiyInfo();
        fragmentChangePw = new FragmentChangePw();
        fragmentLeave = new FragmentLeave();

        fragmentManager = getParentFragmentManager();
        transaction = fragmentManager.beginTransaction();



        mContext = getContext();
        //Builds HTTP Client for API Calls
        restMethods = RestClient.buildHTTPClient();


        nickname = PreferenceManager.getString(mContext, "nickname");
        dateofbirth = PreferenceManager.getString(mContext, "dateofbirth");
        image_path = PreferenceManager.getString(mContext, "image_path");
        gender = PreferenceManager.getInt(mContext, "gender");

        tv_nickname.setText(nickname);
        tv_dateofbirth.setText(dateofbirth);
        if (gender == 0 ){
            tv_gender.setText("남");
        } else if(gender == 1){
            tv_gender.setText("여");
        }

        Log.d(TAG, "image_path: " + image_path);
        if(image_path != null && !image_path.isEmpty()){
            Glide.with(mContext).load(image_path).into(iv_img);
        }




        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        modifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.container, fragmentModifiyInfo);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();

            }
        });

        changePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.container, fragmentChangePw);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });

        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.container, fragmentLeave);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });

        mpLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.clear(mContext);
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                Toast.makeText(mContext, "로그아웃되셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }
}
