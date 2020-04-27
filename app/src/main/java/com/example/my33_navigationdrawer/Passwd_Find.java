package com.example.my33_navigationdrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my33_navigationdrawer.api.RestMethods;
import com.example.my33_navigationdrawer.retrofit2.RestClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.my33_navigationdrawer.utils.CommonMethod.isNetworkConnected;

public class Passwd_Find extends AppCompatActivity {
    private static final String TAG = "Passwd_Find";
    private Button btnFindClose, btnFindOk;
    private EditText etFindEmail;
    private ProgressDialog progressDialog;
    private RestMethods restMethods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwd_find);



        btnFindClose = findViewById(R.id.btnFindClose);
        btnFindOk = findViewById(R.id.btnFindOk);
        etFindEmail = findViewById(R.id.etFindEmail);

        btnFindOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

        btnFindClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendMail() {
        if(isNetworkConnected(Passwd_Find.this) == true) {
            //Check email feild is not empty
            if (TextUtils.isEmpty(etFindEmail.getText().toString())) {
                etFindEmail.setError("이메일을 입력하세요");
                etFindEmail.requestFocus();
                return;
            }
            //Check email format is valid
            if (!isValidEmail(etFindEmail.getText().toString())) {
                etFindEmail.setError("Email is invalid!");
                return;
            }
            // Set up progress before call 프로그레스 다이알로그
            progressDialog = new ProgressDialog(Passwd_Find.this);
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

            //Builds HTTP Client for API Calls
            restMethods = RestClient.buildHTTPClient();
            restMethods.pwFind(etFindEmail.getText().toString()).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {

                    if (response.isSuccessful()) {
                        // close it after response
                        progressDialog.dismiss();
                        int result = response.body();
                        if (result == 1) {
                            Log.d(TAG, "onResponse: 임시비번메일 발송 성공 ");
                            Toast.makeText(getApplicationContext(), "임시비밀번호를 담은 메일이 발송되었습니다. 확인바랍니다.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Passwd_Find.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d(TAG, "onResponse: 임시비번메일 발송 실패 ");
                            Toast.makeText(getApplicationContext(), "임시비밀번호 메일 발송 실패",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // close it after response
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "임시비밀번호 메일이 발송되지 않았습니다.  .",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    // close it after response
                    progressDialog.dismiss();
                    Log.v(TAG, "No Response!");


                    t.printStackTrace();
                    //Response failed
                    Log.e(TAG, "Response: " + t.getMessage());
                }

            });


        } else {
            Toast.makeText(Passwd_Find.this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }


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
