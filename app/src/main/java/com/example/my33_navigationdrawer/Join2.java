package com.example.my33_navigationdrawer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my33_navigationdrawer.DTO.MemberDTO.MemberInfo;
import com.example.my33_navigationdrawer.api.RestMethods;
import com.example.my33_navigationdrawer.retrofit2.RestClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.my33_navigationdrawer.utils.CommonMethod.isNetworkConnected;

public class Join2 extends AppCompatActivity {
    private static final String TAG = "Join2";
    private Button btnBack2, btnJoinOk;
    private Intent intent;
    private EditText etDateofBirth;
    private RadioGroup rg;
    private RestMethods restMethods;
    private ProgressDialog progressDialog;
    private MemberInfo memberInfo;
    private View join2View;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);

        //Builds HTTP Client for API Calls
        restMethods = RestClient.buildHTTPClient();

        setContent();

        intent = getIntent();
        memberInfo = (MemberInfo) intent.getSerializableExtra("memberInfo");

        //라디오 버튼 클릭 여부
        int rbtnState = rg.getCheckedRadioButtonId();
        Log.d(TAG, "onCreate: " + rbtnState);


        etDateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBirthDayPicker();
            }
        });

        btnJoinOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected(Join2.this) == true) {
                    if (rg.getCheckedRadioButtonId() == -1) {
                        // no radio buttons are checked
                        Toast.makeText(getApplicationContext(), "성별을 선택하세요", Toast.LENGTH_SHORT).show();
                        Log.d("QAOD", "성별선택 안함.");
                    } else {
                        // one of the radio buttons is checked
                        Log.d("QAOD", "성별선택 되었음");
                    }

                    int selectedId = rg.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = findViewById(selectedId);

                    // Set up progress before call 프로그레스 다이알로그
                    progressDialog = new ProgressDialog(Join2.this);
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

                    String email = memberInfo.getEmail();
                    String passwd = memberInfo.getPasswd();
                    String nickname = memberInfo.getNickname();
                    String gender = selectedRadioButton.getText().toString();
                    String dateofbirth = etDateofBirth.getText().toString();

                    Log.d(TAG, "값은  이메일: " + email + " passwd: " + passwd + " nickname: " + nickname);
                    Log.d(TAG, " 값은  gender: " + gender + "  생일: " + dateofbirth);

                    restMethods.join(email, passwd, nickname, gender, dateofbirth).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.isSuccessful()) {
                                progressDialog.dismiss();
                                Log.v(TAG, "Yes!");
                                //Response was successfull
                                int result = response.body();
                                Log.i(TAG, "회원가입 반환값은====> " + result);
                                if (result == 1) {
                                    Snackbar.make(join2View, "회원가입 인증메일이 발송되었습니다. 인증 후 로그인 바랍니다.", Snackbar.LENGTH_LONG).show();
                                }


                                Intent intent = new Intent(Join2.this, MainActivity.class);
                                intent.putExtra("Join", true);
                                startActivity(intent);
                                finish();
                            } else {
                                // close it after response
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.",
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

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);


                }else {
                    Toast.makeText(Join2.this, "인터넷이 연결되어 있지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Join.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void setContent() {
        rg = findViewById(R.id.radioGender);
        etDateofBirth= findViewById(R.id.dateOfBirth);
        btnJoinOk = findViewById(R.id.btnJoinOk);
        btnBack2 = findViewById(R.id.btnBack2);
        join2View = findViewById(R.id.join2);
    }

    private void showBirthDayPicker() {
        Calendar calender = Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);

        final Dialog birthdayDialog = new Dialog(this);
        birthdayDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        birthdayDialog.setContentView(R.layout.dialog_birthday);

        Button okBtn = birthdayDialog.findViewById(R.id.birthday_btn_ok);
        Button cancelBtn = birthdayDialog.findViewById(R.id.birthday_btn_cancel);

        final NumberPicker np = birthdayDialog.findViewById(R.id.birthdayPicker);
        np.setMinValue(year - 100);
        np.setMaxValue(year - 15);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(np, android.R.color.white );
        np.setWrapSelectorWheel(false);
        np.setValue(year-20);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDateofBirth.setText(String.valueOf(np.getValue()));
                birthdayDialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthdayDialog.dismiss();
            }
        });

        birthdayDialog.show();
    }


    private void setDividerColor(NumberPicker picker, int color) {
        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }




}
