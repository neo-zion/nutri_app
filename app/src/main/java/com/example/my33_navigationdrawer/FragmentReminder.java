package com.example.my33_navigationdrawer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.my33_navigationdrawer.reminder.AlarmReceiver;
import com.example.my33_navigationdrawer.utils.PreferenceManager;

import java.util.ArrayList;

public class FragmentReminder extends Fragment {
    private static final String TAG = "FragmentReminder";
    private Button button, backbtn, btnCancel, btnNotification;
    private Context mContext;
    private PendingIntent pendingIntent;
    private Spinner remSpinner1, remSpinner2;
    private ArrayList<String> arrayList, arrayList1;
    private ArrayAdapter<String> arrayAdapter, arrayAdapter1;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private AlarmManager alarmManager;
    private PackageManager pm;
    private ComponentName receiver;
    private Intent alarmIntent;
    private TextView notificationMessage;

    private final static String BACK = "허리";
    private final static String NECK = "목";
    private final static String SHOULDER = "어깨";
    private static String BODYPART;
    public static String MESSAGE;

    private final static int TEN_SECONDS = 10 * 1000;
    private final static int FIFTEEN_MINUTES = 15 * 60 * 1000;
    private static int INTERVAL;

    public FragmentReminder() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_reminder,
                container, false);

        fragmentManager = getParentFragmentManager();
        mContext = getContext();
        button = rootView.findViewById(R.id.button);
        backbtn = rootView.findViewById(R.id.reminderBack);
        remSpinner1 = rootView.findViewById(R.id.remSpinner1);
        remSpinner2 = rootView.findViewById(R.id.remSpinner2);
        btnCancel = rootView.findViewById(R.id.btnCancel);
        btnNotification = rootView.findViewById(R.id.btnNotification);
        notificationMessage = rootView.findViewById(R.id.notificationMessage);
        transaction = fragmentManager.beginTransaction();
        final String nickname = PreferenceManager.getString(mContext, "nickname");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        arrayList = new ArrayList<>();
        arrayList.add("선택");
        arrayList.add("10초");
        arrayList.add("15분");
        arrayList.add("30분");
        arrayList.add("1시간");


        arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, arrayList);

        remSpinner1.setAdapter(arrayAdapter);

        remSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    INTERVAL = TEN_SECONDS;
                    Log.d(TAG, "인터벌은 10초 " + INTERVAL);
                } else if (position == 2) {
                    INTERVAL = FIFTEEN_MINUTES;
                    Log.d(TAG, "인터벌은 15분 " + INTERVAL);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arrayList1 = new ArrayList<>();
        arrayList1.add("선택");
        arrayList1.add("허리");
        arrayList1.add("목");
        arrayList1.add("어깨");
        arrayAdapter1 = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, arrayList1);

        remSpinner2.setAdapter(arrayAdapter1);

        remSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    BODYPART = BACK;
                    Log.d(TAG, "알림부위는 허리 ");
                    MESSAGE = nickname+"님! 허리를 10초간 펴세요";

                } else if (position == 2) {
                    BODYPART = NECK;
                    Log.d(TAG, "알림부위는 목 ");
                    MESSAGE = nickname+"님! 목을 천천히 돌려주세요";
                } else if (position == 3) {
                    BODYPART = SHOULDER;
                    Log.d(TAG, "알림부위는 어깨 ");
                    MESSAGE = nickname+"님! 어깨를 10초간 돌려주세요";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        final Button button = rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                diaryNotification();
                button.setVisibility(View.GONE);
                btnCancel.setVisibility(View.VISIBLE);
                Log.d(TAG, "선택한 인터벌은 ====> " + INTERVAL);
                if (INTERVAL == TEN_SECONDS) {
                    Toast.makeText(mContext, "10초마다 "+BODYPART +" 알람을 시작합니다.", Toast.LENGTH_SHORT).show();
                } else if (INTERVAL == FIFTEEN_MINUTES) {
                    Toast.makeText(mContext, "15분마다 "+BODYPART +" 알람을 시작합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmCancel();
                button.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.GONE);
                Toast.makeText(mContext, "알람을 종료합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BODYPART =="허리") {
                    notificationMessage.setText("스트레칭 시간입니다.\n"+nickname+"님 허리를 10초간 펴세요");
                } else if (BODYPART == "목") {
                    notificationMessage.setText("스트레칭 시간입니다.\n"+nickname+"님 목을 천천히 돌려주세요");
                } else if (BODYPART == "어깨") {
                    notificationMessage.setText("스트레칭 시간입니다.\n"+nickname+"님 어깨를 10초간 돌려주세요");
                }
            }
        });



        return rootView;
    }

    void diaryNotification() {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        //Boolean dailyNotify = true; // 무조건 알람을 사용


            pm = mContext.getPackageManager();
            //ComponentName receiver = new ComponentName(mContext, DeviceBootReceiver.class);
            alarmIntent = new Intent(mContext, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(mContext, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

            if (alarmManager != null) {
                Log.d(TAG, "선택한 인터벌은 ====> " + INTERVAL);
                alarmManager.cancel(pendingIntent);
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + INTERVAL,
                        INTERVAL, pendingIntent);
            }

            // 부팅 후 실행되는 리시버 사용가능하게 설정
            /*pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);*/


    }

    void alarmCancel() {

        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (sender != null) {
            am.cancel(sender);
            sender.cancel();
        }


    }

}




