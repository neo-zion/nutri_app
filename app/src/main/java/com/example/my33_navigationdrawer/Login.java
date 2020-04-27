package com.example.my33_navigationdrawer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my33_navigationdrawer.DTO.MemberDTO.MemberDTO;
import com.example.my33_navigationdrawer.api.RestMethods;
import com.example.my33_navigationdrawer.retrofit2.RestClient;
import com.example.my33_navigationdrawer.utils.PreferenceManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.my33_navigationdrawer.utils.CommonMethod.loginDTO;


public class Login extends AppCompatActivity {

    private String TAG = Login.class.getSimpleName();

    private Button btnLoginBack, btnFindPasswd, btnLoginOk;
    private EditText etLoginEmail, etLoginPasswd;
    private ProgressDialog progressDialog;
    private RestMethods restMethods;
    private String image_path;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





        //Binds UI to Activity
        setContent();

        etLoginPasswd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    doLogin();
                    return true;
                }
                return false;
            }
        });

        btnLoginOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Will validate & call Login API
                doLogin();
            }
        });


        btnLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnFindPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Passwd_Find.class);
                startActivity(intent);
            }
        });
    }

    void setContent() {
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPasswd = findViewById(R.id.etLoginPasswd);
        btnLoginOk = findViewById(R.id.btnLoginOk);
        btnLoginBack = findViewById(R.id.btnLoginBack);
        btnFindPasswd = findViewById(R.id.btnFindPasswd);
        etLoginPasswd = findViewById(R.id.etLoginPasswd);


    }
    /**
     * Do login.
     * <p>
     * <p>This will validate inputs and then do Login.</p>
     */
    /**
     * Do login.
     * <p>
     * <p>This will validate inputs and then do Login.</p>
     */
    void doLogin() {

        //Check email feild is not empty
        if (TextUtils.isEmpty(etLoginEmail.getText().toString())) {
            etLoginEmail.setError("이메일을 입력하세요");
            etLoginEmail.requestFocus();
            return;
        }

            //Check email format is valid
       if (!isValidEmail(etLoginEmail.getText().toString())) {
            etLoginEmail.setError("올바른 이메일 주소를 입력하세요");
            return;
       }

        //Check password field is not empty
        if (TextUtils.isEmpty(etLoginPasswd.getText().toString())) {
            etLoginPasswd.setError("패스워드를 입력하세요");
            etLoginPasswd.requestFocus();
            return;
        }

        // Set up progress before call 프로그레스 다이알로그
        progressDialog = new ProgressDialog(Login.this);
        // 프로그레스 다이알로그
        //progressDialog.setMax(100);
        //프로그레스 다이알로그 메세지 설정
        progressDialog.setMessage("로딩중입니다...");
        //progressDialog.setTitle("ProgressDialog bar ");
        // 프로그레스다이알로그 취소여부 결정
        //progressDialog.setCancelable(true);
        //프로그레스다이알로그 스타일 설정
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        // show it
        progressDialog.show();

        //TODO Add more validations
        //Builds HTTP Client for API Calls
        restMethods = RestClient.buildHTTPClient();
        //Show loading and call HTTP client's login method
        restMethods.login(etLoginEmail.getText().toString(), etLoginPasswd.getText().toString()).enqueue(new Callback<MemberDTO>() {
            @Override
            public void onResponse(@NonNull Call<MemberDTO> call, @NonNull Response<MemberDTO> response) {
                if(response.isSuccessful()) {
                    // close it after response
                    progressDialog.dismiss();
                    //Response was successfull
                    String y = "Y";
                    Log.i(TAG, "Response: " + response.code()+response.body());
                    MemberDTO memberDTO = response.body();
                    loginDTO = memberDTO.getMemberInfo();
                    Log.d(TAG, "login결과 " + loginDTO);
                    if (loginDTO != null) {
                    if (!(loginDTO.getUser_key().equals(y))) {
                        Log.v(TAG, "메일 미인증 회원입니다.");
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        dialog = builder.setMessage("메일 인증 후 로그인 바랍니다.")
                                .setNegativeButton("OK", null)
                                .create();
                        dialog.show();
                        return;
                    } else {
                     Log.v(TAG, "메일인증 완료된 회원입니다.");
                     String nickname = loginDTO.getNickname();
                     String email = loginDTO.getEmail();
                     String dateofbirth = loginDTO.getDateofbirth();
                     image_path = loginDTO.getImage_path();
                     int gender = loginDTO.getGender();

                     //닉네임 이메일 이미지 경로 저장


                     PreferenceManager.setString(Login.this, "nickname", nickname);
                     PreferenceManager.setString(Login.this, "email", email);
                     PreferenceManager.setString(Login.this, "dateofbirth", dateofbirth);
                     PreferenceManager.setInt(Login.this, "gender", gender);

                        if(image_path != null && !image_path.isEmpty()){
                          PreferenceManager.setString(Login.this, "image_path", image_path);
                        }

                        progressDialog.dismiss();

                    Intent intent = new Intent(Login.this, MainActivity2.class);
                    startActivity(intent);
                    finish();}
                    } else {// 아이디나 비밀번호 오류로 loginDTO null
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        dialog = builder.setMessage("아이디 또는 비밀번호가 다릅니다.")
                                .setNegativeButton("OK", null)
                                .create();
                        dialog.show();
                        return;
                    }
                } else {
                    // close it after response
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 다릅니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MemberDTO> call, @NonNull Throwable t) {
                // close it after response
                progressDialog.dismiss();
                Log.v(TAG, "No Response!");

                t.printStackTrace();
                //Response failed
                Log.e(TAG, "Response: " + t.getMessage());

            }
        });
    }
    /**
     * validate your email address format.
     */
    public boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}