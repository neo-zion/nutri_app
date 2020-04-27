package com.example.my33_navigationdrawer;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my33_navigationdrawer.DTO.MemberDTO.MemberInfo;
import com.example.my33_navigationdrawer.api.RestMethods;
import com.example.my33_navigationdrawer.retrofit2.RestClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.my33_navigationdrawer.utils.CommonMethod.isNetworkConnected;


public class Join extends AppCompatActivity {

    private static final String TAG = "Join";
    private Button btnBack, emailChecked, nickChecked, terms, PrivacyPolicy, btnJoinNext;
    private EditText etEmail, etPasswd, etNickname;
    private CheckBox allTermsCheck;
    private MemberInfo memberInfo;
    private int RequestCode = 1004;
    private RestMethods restMethods;
    private View joinView;
    private boolean emailValidate = false;
    private boolean nickValidate = false;
    private AlertDialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        //Builds HTTP Client for API Calls
        restMethods = RestClient.buildHTTPClient();

        setContent();


        emailChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected(Join.this) == true){
                String email = etEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    etEmail.setError("이메일을 입력하세요");
                    etEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("올바른 이메일 주소를 입력하세요.");
                    etEmail.requestFocus();
                    return;
                }
                restMethods.emailCheck(email).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            Log.v(TAG, "Yes!");
                            //Response was successfull
                            int result = response.body();
                            Log.i(TAG, "이메일중복체크 반환값은====> " + result);
                            if (result == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Join.this);
                                dialog = builder.setMessage("사용 가능한 이메일입니다.")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                                etEmail.setEnabled(false);//아이디값을 바꿀 수 없도록 함
                                emailValidate = true;//검증완료
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Join.this);
                                dialog = builder.setMessage("이미 사용중인 이메일입니다.")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                            }

                        } else {
                            Toast.makeText(Join.this, "통신불량입니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        t.printStackTrace();
                        //Response failed
                        Log.e(TAG, "Response: " + t.getMessage());
                    }
                });

            } else {
                    Toast.makeText(Join.this, "인터넷이 연결되어 있지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        final String nickNameCheck = "/^[가-힣a-zA-Z0-9]{2,10}$/";
        nickChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected(Join.this) == true){
                String nickname = etNickname.getText().toString().trim();
                if (nickname.isEmpty()) {
                    etNickname.setError("닉네임을 입력하세요");
                    etNickname.requestFocus();
                    return;
                }
                if (Pattern.matches(nickNameCheck, nickname)) {
                    etNickname.setError("닉네임은 특수문자를 제외한 2~15자 입력가능합니다.");
                    etNickname.requestFocus();
                    return;
                }
                restMethods.nickCheck(nickname).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            Log.v(TAG, "Yes!");
                            //Response was successfull
                            int result = response.body();
                            Log.i(TAG, "닉네임중복체크 반환값은====> " + result);
                            if (result == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Join.this);
                                dialog = builder.setMessage("사용 가능한 닉네임입니다.")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                                etNickname.setEnabled(false);//아이디값을 바꿀 수 없도록 함
                                nickValidate = true;//검증완료
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Join.this);
                                dialog = builder.setMessage("이미 사용중인 닉네임입니다.")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                            }

                        } else {
                            Toast.makeText(Join.this, "통신불량입니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        t.printStackTrace();
                        //Response failed
                        Log.e(TAG, "Response: " + t.getMessage());
                    }
                });

            } else {
                    Toast.makeText(Join.this, "인터넷이 연결되어 있지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnJoinNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emailValidate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Join.this);
                    dialog = builder.setMessage("이메일 중복체크 하시기 바랍니다.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }

                if(!nickValidate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Join.this);
                    dialog = builder.setMessage("닉네임 중복체크 하시기 바랍니다.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }
                if(!allTermsCheck.isChecked()) {
                    allTermsCheck.setError("회원가입을 위해서는 약관동의가 필요합니다. ");
                    allTermsCheck.requestFocus();
                    return;
                }



                String email =etEmail.getText().toString().trim();
                String passwd =etPasswd.getText().toString().trim();
                String nickname = etNickname.getText().toString().trim();



        if(email.isEmpty()) {
            etEmail.setError("이메일을 입력하세요");
            etEmail.requestFocus();
            return;
        }

         if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("올바른 이메일 주소를 입력하세요.");
            etEmail.requestFocus();
            return;
         }
         if(passwd.isEmpty()) {
            etPasswd.setError("패스워드를 입력하세요");
            etPasswd.requestFocus();
            return;
         }
         if(passwd.length() < 4) {
                etPasswd.setError("비밀번호는 4자이상이어야 합니다.");
                etPasswd.requestFocus();
            return;
         }
         if(nickname.isEmpty()) {
             etNickname.setError("닉네임을 입력하세요");
             etNickname.requestFocus();
             return;
          }



               memberInfo = new MemberInfo(email, passwd, nickname);

                Intent intent = new Intent(getApplicationContext(), Join2.class);
                intent.putExtra("memberInfo", memberInfo);
                startActivityForResult(intent, RequestCode);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    void setContent() {
        emailChecked = findViewById(R.id.emailChecked);
        nickChecked = findViewById(R.id.nickChecked);
        terms = findViewById(R.id.terms);
        PrivacyPolicy = findViewById(R.id.PrivacyPolicy);

        etEmail =findViewById(R.id.etEmail);
        etPasswd = findViewById(R.id.etPasswd);
        etNickname = findViewById(R.id.etNickName);

        btnBack = findViewById(R.id.btnBack);
        btnJoinNext = findViewById(R.id.btnJoinNext);

        allTermsCheck = findViewById(R.id.allTermsCheck);
    }
}
