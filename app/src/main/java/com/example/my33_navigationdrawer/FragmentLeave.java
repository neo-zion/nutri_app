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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.my33_navigationdrawer.api.RestMethods;
import com.example.my33_navigationdrawer.retrofit2.RestClient;
import com.example.my33_navigationdrawer.utils.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.my33_navigationdrawer.utils.CommonMethod.isNetworkConnected;

public class FragmentLeave extends Fragment {

    private static final String TAG = "FragmentChangePw";
    private Button backbtn, leaveConfirm;
    private ProgressDialog progressDialog;
    private RestMethods restMethods;
    private Context mContext;
    private TextView etPasswd;
    private Bundle bundle;
    private String email;
    private FragmentManager fragmentManager;
    private CheckBox leaveCheck;


    public static FragmentLeave newInstance(Bundle bundle) {
        FragmentLeave fragmentLeave = new FragmentLeave();
        fragmentLeave.setArguments(bundle);
        return fragmentLeave;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_leave,
                container, false);

        backbtn = rootView.findViewById(R.id.leaveBack);
        leaveConfirm = rootView.findViewById(R.id.leaveConfirm);
        leaveCheck = rootView.findViewById(R.id.leaveCheck);

        etPasswd = rootView.findViewById(R.id.etPasswd);

        mContext = getContext();
        fragmentManager = getParentFragmentManager();
        //Builds HTTP Client for API Calls
        restMethods = RestClient.buildHTTPClient();

       email = PreferenceManager.getString(mContext, "email");


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.popBackStack();
            }
        });

        leaveConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected(mContext) == true){

                    String passwd = etPasswd.getText().toString().trim();


                    if(!leaveCheck.isChecked()) {
                        leaveCheck.setError("탈퇴를 원하시면 동의를 체크 바랍니다. ");
                        leaveCheck.requestFocus();
                        return;
                    }
                    if(passwd.isEmpty()) {
                        etPasswd.setError("비밀번호를 입력하세요");
                        etPasswd.requestFocus();
                        return;
                    }


                    // Set up progress before call 프로그레스 다이알로그
                    progressDialog = new ProgressDialog(mContext);
                    // 프로그레스 다이알로그
                    //progressDoalog.setMax(100);

                    //프로그레스 다이알로그 메세지 설정
                    progressDialog.setMessage("로딩중입니다...");

                    //progressDoalog.setTitle("ProgressDialog bar ");

                    // 프로그레스다이알로그 취소여부 결정
                    //progressDialog.setCancelable(true);

                    //프로그레스다이알로그 스타일 설정
                    progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
                    // show it
                    progressDialog.show();


                    Log.d(TAG, "이메일은 " + email);

                    restMethods.leave(email,passwd).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.isSuccessful()) {
                                // close it after response
                                progressDialog.dismiss();
                                if (response.body()==1) {
                                    Log.v(TAG, "회원탈퇴 성공 ");
                                    Toast.makeText(mContext, "정상적으로 회원탈퇴되셨습니다.", Toast.LENGTH_LONG).show();
                                    PreferenceManager.clear(mContext);
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    Log.v(TAG, "회원탈퇴 실패 ");
                                }
                            } else {
                                // close it after response
                                progressDialog.dismiss();
                                Log.v(TAG, "통신 불량!");
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            // close it after response
                            progressDialog.dismiss();
                            Log.v(TAG, "통신 실패!");

                            t.printStackTrace();
                            //Response failed
                            Log.e(TAG, "Response: " + t.getMessage());
                        }
                    });


                }else {
                    Toast.makeText(mContext, "인터넷이 연결되어 있지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });





        return rootView;
    }
}