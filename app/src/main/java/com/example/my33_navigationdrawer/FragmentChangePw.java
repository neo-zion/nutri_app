package com.example.my33_navigationdrawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class FragmentChangePw extends Fragment {

    private static final String TAG = "FragmentChangePw";
    private Button backbtn, changePwConfirm;
    private ProgressDialog progressDialog;
    private RestMethods restMethods;
    private Context mContext;
    private TextView etOldpw, etNewPw, etNewPwConfirm;
    private String email;
    private FragmentManager fragmentManager;


    public static FragmentChangePw newInstance(Bundle bundle) {
        FragmentChangePw fragmentChangePw = new FragmentChangePw();
        fragmentChangePw.setArguments(bundle);
        return fragmentChangePw;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_changepw,
                container, false);

        backbtn = rootView.findViewById(R.id.changePwBack);
        changePwConfirm = rootView.findViewById(R.id.changePwConfirm);

        etOldpw = rootView.findViewById(R.id.etOldpw);
        etNewPw = rootView.findViewById(R.id.etNewPw);
        etNewPwConfirm = rootView.findViewById(R.id.etNewPwConfirm);

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

        changePwConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected(mContext) == true){
                    String old_pw = etOldpw.getText().toString().trim();
                    String passwd = etNewPw.getText().toString().trim();
                    String passwdConfirm = etNewPwConfirm.getText().toString().trim();
                    Log.d(TAG, "passwd: "+ passwd);
                    Log.d(TAG, "passwdConfirm: "+ passwdConfirm);

                    if(old_pw.isEmpty()) {
                        etOldpw.setError("기존 비밀번호를 입력하세요");
                        etOldpw.requestFocus();
                        return;
                    }
                    if(passwd.isEmpty()) {
                        etNewPw.setError("새로운 비밀번호를 입력하세요");
                        etNewPw.requestFocus();
                        return;
                    }
                    if(passwd.length() < 4) {
                        etNewPw.setError("비밀번호는 4자이상이어야 합니다.");
                        etNewPw.requestFocus();
                        return;
                    }
                    if(!(passwd.equals(passwdConfirm))) {
                        etNewPwConfirm.setError("새로운 비밀번호가 서로 다릅니다.");
                        etNewPwConfirm.requestFocus();
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

                    restMethods.changePw(email,old_pw,passwd).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.isSuccessful()) {
                                // close it after response
                                progressDialog.dismiss();
                                if (response.body()==1) {
                                    Log.v(TAG, "비밀번호 변경 성공 ");
                                    Toast.makeText(mContext, "비밀번호가 변경되었습니다.", Toast.LENGTH_LONG).show();
                                    fragmentManager = getParentFragmentManager();
                                    fragmentManager.popBackStack();

                                } else {
                                    Log.v(TAG, "비밀번호 변경 실패 ");
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