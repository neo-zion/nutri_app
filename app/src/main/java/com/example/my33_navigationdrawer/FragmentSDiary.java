package com.example.my33_navigationdrawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.my33_navigationdrawer.DTO.SDiaryDTO.Daily;
import com.example.my33_navigationdrawer.DTO.SDiaryDTO.DailyList;
import com.example.my33_navigationdrawer.DTO.SDiaryDTO.Monthly;
import com.example.my33_navigationdrawer.DTO.SDiaryDTO.MonthlyList;
import com.example.my33_navigationdrawer.DTO.SDiaryDTO.Weekly;
import com.example.my33_navigationdrawer.DTO.SDiaryDTO.WeeklyList;
import com.example.my33_navigationdrawer.api.RestMethods;
import com.example.my33_navigationdrawer.retrofit2.RestClient;
import com.example.my33_navigationdrawer.utils.MyValueFormatter;
import com.example.my33_navigationdrawer.utils.PreferenceManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSDiary extends Fragment {
    private static final String TAG = "FragmentSDiary";

    private RestMethods restMethods;

    private Button sDiaryBack;
    private FragmentManager fragmentManager;
    private BarChart barChart;
    private ArrayList<DailyList> dailyLists;
    private static ArrayList<WeeklyList> weeklyLists;
    private ArrayList<MonthlyList> monthlyLists;
    private ProgressDialog progressDialog;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private Spinner spinner;
    private Context  mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sdiary,
                container, false);
        mContext = getContext();
        //Builds HTTP Client for API Calls
        restMethods = RestClient.buildHTTPClient();
        spinner = rootView.findViewById(R.id.spinner);

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


        sDiaryBack = rootView.findViewById(R.id.sDiaryBack);
        barChart = rootView.findViewById(R.id.barChart);
        barChart.setNoDataText("오늘도 힘내!"); // 데이터가 없을 때 나오는 화면
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(true);


        arrayList = new ArrayList<>();
        arrayList.add("일별");
        arrayList.add("주별");
        arrayList.add("월별");

        arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, arrayList);

        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(arrayAdapter.getPosition("일별")); //SDiary에서 처음 나올 그래프 선택

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext,arrayList.get(position)+"이 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    dailyChart();
                } else if (position == 1) {
                    weeklyChart();
                } else if (position == 2) {
                    monthlyChart();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        sDiaryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack();
            }
        });

        return rootView;
    }


    public void dailyChart() {
        String nickname = PreferenceManager.getString(mContext, "nickname");
        restMethods.sDiaryDaily(nickname).enqueue(new Callback<Daily>() {
            @Override
            public void onResponse(Call<Daily> call, Response<Daily> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+ response.body().toString());
                    // close it after response
                    progressDialog.dismiss();
                    Log.v(TAG, "통신성공!");
                    Daily daily = response.body();
                    dailyLists= (ArrayList<DailyList>) daily.getDailyList();

                    if (dailyLists.size() != 0) {
                        //차트 데이터 셋에 담겨질 데이터
                        List<BarEntry> entriesGroup1 = new ArrayList<>();
                        List<BarEntry> entriesGroup2 = new ArrayList<>();
                        Log.d(TAG, "onResponse: " + dailyLists.get(0).getPushup());
                        Log.d(TAG, "onResponse: " + dailyLists.get(0).getStanddown());

                        // fill the lists
                        for (int i = 0; i < dailyLists.size(); i++) {  //entriesGroup에 데이터를 담는 과정
                            entriesGroup1.add(new BarEntry(i, dailyLists.get(i).getPushup()));
                            entriesGroup2.add(new BarEntry(i, dailyLists.get(i).getStanddown()));
                        }

                        ArrayList days = new ArrayList();
                        for (int i = 0; i < dailyLists.size(); i++) {
                            days.add(dailyLists.get(i).getUpdatedate().substring(5, 10));
                        }

                        BarDataSet set1 = new BarDataSet(entriesGroup1, "푸쉬업");//BarDataSet 선언
                        set1.setColors(Color.DKGRAY); //BarChart에서 Bar Color 설정
                        BarDataSet set2 = new BarDataSet(entriesGroup2, "스쿼트");
                        set2.setColors(Color.CYAN);

                        BarData data = new BarData(set1, set2); //BarDataSet을 담는 그릇 여러개의 Bar 데이터가 들어갈 수 있습니다.
                        data.setValueFormatter(new MyValueFormatter()); // 뒷 소수점 없앰.

                        float groupSpace = 0.1f;
                        float barSpace = 0.02f; // x2 dataset
                        float barWidth = 0.43f; // x2 dataset
                        // (0.02 + 0.43) * 2 + 0.1 = 1.00 -> interval per "group"
                        data.setBarWidth(barWidth); // set the width of each bar
                        barChart.setData(data);
                        barChart.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping


                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                        xAxis.setGranularity(1);
                        xAxis.setAxisMinimum(0);
                        xAxis.setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * days.size());

//                    YAxis leftAxis = barChart.getAxisLeft();
//                    YAxis rightAxis = barChart.getAxisRight();
//                    leftAxis.setAxisMinimum(0f); //오른쪽 y축 최소값 설정
//                    leftAxis.setAxisMaximum(40f); //왼쪽y축 최대값 설정
//                    rightAxis.setAxisMinimum(0f); //오른쪽 y축 최소값 설정
//                    rightAxis.setAxisMaximum(40f); //오른쪽y축 최대값 설정
//                    leftAxis.setSpaceTop(40f);
//                    rightAxis.setSpaceTop(40f);
                        barChart.setAutoScaleMinMaxEnabled(true);

                        float v = dailyLists.size();
                        barChart.moveViewToX(v); //배열의 사이즈만큼 X축을 오른쪽으로 이동시켜 보여줌.
                        barChart.setVisibleXRangeMaximum(10); // 보여지는 X축의 데이터 개수


                        barChart.invalidate(); // refresh
                    }
                } else {
                    // close it after response
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "통신불량입니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Daily> call, Throwable t) {
                // close it after response
                progressDialog.dismiss();
                Log.v(TAG, "No Response!");

                t.printStackTrace();
                //Response failed
                Log.e(TAG, "Response: " + t.getMessage());
            }
        });


    }
    public void weeklyChart() {
        String nickname = PreferenceManager.getString(mContext, "nickname");
        restMethods.sDiaryWeekly(nickname).enqueue(new Callback<Weekly>() {
            @Override
            public void onResponse(Call<Weekly> call, Response<Weekly> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+ response.body().toString());
                    // close it after response
                    progressDialog.dismiss();
                    Log.v(TAG, "통신성공!");
                    barChart.invalidate(); // refresh
                    barChart.clear();
                    Weekly weekly = response.body();
                    weeklyLists = (ArrayList<WeeklyList>) weekly.getWeeklyList();
                    if (weeklyLists.size() != 0) {
                        //차트 데이터 셋에 담겨질 데이터
                        ArrayList<BarEntry> entriesGroup1 = new ArrayList<>();
                        ArrayList<BarEntry> entriesGroup2 = new ArrayList<>();
                        Log.d(TAG, "onResponse: " + weeklyLists.get(0).getPushup());
                        Log.d(TAG, "onResponse: " + weeklyLists.get(0).getStanddown());

                        // fill the lists
                        for (int i = 0; i < weeklyLists.size(); i++) {  //entriesGroup에 데이터를 담는 과정
                            entriesGroup1.add(new BarEntry(i, weeklyLists.get(i).getPushup()));
                            entriesGroup2.add(new BarEntry(i, weeklyLists.get(i).getStanddown()));
                        }

                        ArrayList weeks = new ArrayList();
                        for (int i = 0; i < weeklyLists.size(); i++) {
                            weeks.add(weeklyLists.get(i).getUpdatedate());
                        }

                        BarDataSet set1 = new BarDataSet(entriesGroup1, "푸쉬업");//BarDataSet 선언
                        set1.setColors(Color.DKGRAY); //BarChart에서 Bar Color 설정
                        BarDataSet set2 = new BarDataSet(entriesGroup2, "스쿼트");
                        set2.setColors(Color.CYAN);

                        BarData data = new BarData(set1, set2); //BarDataSet을 담는 그릇 여러개의 Bar 데이터가 들어갈 수 있습니다.
                        data.setValueFormatter(new MyValueFormatter()); // 뒷 소수점 없앰.

                        float groupSpace = 0.1f;
                        float barSpace = 0.02f; // x2 dataset
                        float barWidth = 0.43f; // x2 dataset
                        // (0.02 + 0.43) * 2 + 0.1 = 1.00 -> interval per "group"
                        data.setBarWidth(barWidth); // set the width of each bar
                        barChart.setData(data);
                        barChart.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping

                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(weeks));
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                        xAxis.setGranularity(1);
                        xAxis.setAxisMinimum(0);
                        xAxis.setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * weeks.size());

//                    YAxis leftAxis = barChart.getAxisLeft();
//                    YAxis rightAxis = barChart.getAxisRight();
//                    leftAxis.setAxisMinimum(0f); //오른쪽 y축 최소값 설정
//                    leftAxis.setAxisMaximum(40f); //왼쪽y축 최대값 설정
//                    rightAxis.setAxisMinimum(0f); //오른쪽 y축 최소값 설정
//                    rightAxis.setAxisMaximum(40f); //오른쪽y축 최대값 설정
//                    leftAxis.setSpaceTop(40f);
//                    rightAxis.setSpaceTop(40f);
                        barChart.setAutoScaleMinMaxEnabled(true);

                        barChart.invalidate(); // refresh
                    }
                } else {
                    // close it after response
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "통신불량입니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Weekly> call, Throwable t) {
                // close it after response
                progressDialog.dismiss();
                Log.v(TAG, "No Response!");

                t.printStackTrace();
                //Response failed
                Log.e(TAG, "Response: " + t.getMessage());
            }
        });

    }

    public void monthlyChart() {
        String nickname = PreferenceManager.getString(mContext, "nickname");
        restMethods.sDiaryMonthly(nickname).enqueue(new Callback<Monthly>() {
            @Override
            public void onResponse(Call<Monthly> call, Response<Monthly> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+ response.body().toString());
                    // close it after response
                    progressDialog.dismiss();
                    Log.v(TAG, "통신성공!");
                    Monthly monthly = response.body();
                    monthlyLists = (ArrayList<MonthlyList>) monthly.getMonthlyList();

                    if (monthlyLists.size() != 0) {
                        barChart.setMaxVisibleValueCount(12);

                        //차트 데이터 셋에 담겨질 데이터
                        ArrayList<BarEntry> entriesGroup1 = new ArrayList<>();
                        ArrayList<BarEntry> entriesGroup2 = new ArrayList<>();
                        Log.d(TAG, "onResponse: " + monthlyLists.get(0).getPushup());
                        Log.d(TAG, "onResponse: " + monthlyLists.get(0).getStanddown());

                        // fill the lists
                        for (int i = 0; i < monthlyLists.size(); i++) {  //entriesGroup에 데이터를 담는 과정
                            entriesGroup1.add(new BarEntry(i, monthlyLists.get(i).getPushup()));
                            entriesGroup2.add(new BarEntry(i, monthlyLists.get(i).getStanddown()));
                        }

                        ArrayList months = new ArrayList();
                        for (int i = 0; i < monthlyLists.size(); i++) {
                            months.add(monthlyLists.get(i).getUpdatedate());
                        }

                        BarDataSet set1 = new BarDataSet(entriesGroup1, "푸쉬업");//BarDataSet 선언
                        set1.setColors(Color.DKGRAY); //BarChart에서 Bar Color 설정
                        BarDataSet set2 = new BarDataSet(entriesGroup2, "스쿼트");
                        set2.setColors(Color.CYAN);

                        BarData data = new BarData(set1, set2); //BarDataSet을 담는 그릇 여러개의 Bar 데이터가 들어갈 수 있습니다.
                        data.setValueFormatter(new MyValueFormatter()); // 뒷 소수점 없앰.

                        float groupSpace = 0.1f;
                        float barSpace = 0.02f; // x2 dataset
                        float barWidth = 0.43f; // x2 dataset
                        // (0.02 + 0.43) * 2 + 0.1 = 1.00 -> interval per "group"
                        data.setBarWidth(barWidth); // set the width of each bar
                        barChart.setData(data);
                        barChart.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping

                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                        xAxis.setGranularity(1);
                        xAxis.setAxisMinimum(0);
                        xAxis.setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * months.size());

//                    YAxis leftAxis = barChart.getAxisLeft();
//                    YAxis rightAxis = barChart.getAxisRight();
//                    leftAxis.setAxisMinimum(0f); //오른쪽 y축 최소값 설정
//                    leftAxis.setAxisMaximum(40f); //왼쪽y축 최대값 설정
//                    rightAxis.setAxisMinimum(0f); //오른쪽 y축 최소값 설정
//                    rightAxis.setAxisMaximum(40f); //오른쪽y축 최대값 설정
//                    leftAxis.setSpaceTop(40f);
//                    rightAxis.setSpaceTop(40f);
                        barChart.setAutoScaleMinMaxEnabled(true);

                        barChart.invalidate(); // refresh

                    }
                } else {
                    // close it after response
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "통신불량입니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Monthly> call, Throwable t) {
                // close it after response
                progressDialog.dismiss();
                Log.v(TAG, "No Response!");

                t.printStackTrace();
                //Response failed
                Log.e(TAG, "Response: " + t.getMessage());
            }
        });
    }


}
