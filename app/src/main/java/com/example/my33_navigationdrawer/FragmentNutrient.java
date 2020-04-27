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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my33_navigationdrawer.Adapter.NutrientAdapter;
import com.example.my33_navigationdrawer.DTO.MyHealthDTO.Nutrient;
import com.example.my33_navigationdrawer.DTO.MyHealthDTO.NutrientList;
import com.example.my33_navigationdrawer.api.RestMethods;
import com.example.my33_navigationdrawer.retrofit2.RestClient;
import com.example.my33_navigationdrawer.utils.PreferenceManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNutrient extends Fragment {

    private static final String TAG = "FragmentNutrient";
    private RecyclerView recyclerView;
    private Context mContext;
    private RestMethods restMethods;
    private ArrayList<Nutrient> lists;
    private NutrientAdapter listAdapter;
    private ProgressDialog progressDialog;
    private Button backbtn;
    private TextView tvNickname, tvGender, tvDateofbirth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_nutrient,
                                            container, false);

        mContext = container.getContext();
        tvGender = rootView.findViewById(R.id.tvGender);
        tvDateofbirth = rootView.findViewById(R.id.tvDateofbirth);
        tvNickname = rootView.findViewById(R.id.tvNickname);
        recyclerView = rootView.findViewById(R.id.nutrientRecycle);


        String nickname1 =PreferenceManager.getString(mContext, "nickname") +"님의";
        tvNickname.setText(nickname1);

        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


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
        backbtn= rootView.findViewById(R.id.nutrientBack);

        getNutrientLists();
        final FragmentManager fragmentManager = getParentFragmentManager();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        return rootView;
    }

    private void getNutrientLists() {
        //Builds HTTP Client for API Calls
        restMethods = RestClient.buildHTTPClient();
        String nickname = PreferenceManager.getString(mContext, "nickname");
        restMethods.nutrient(nickname).enqueue(new Callback<NutrientList>() {
            @Override
            public void onResponse(Call<NutrientList> call, Response<NutrientList> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+ response.body().toString());
                    NutrientList nutrientList = response.body();
                    lists = (ArrayList<Nutrient>) nutrientList.getNutrient();

                    tvGender.setText(lists.get(0).getGender());
                    tvDateofbirth.setText(lists.get(0).getAge());

                    listAdapter = new NutrientAdapter(lists);
                    recyclerView.setAdapter(listAdapter);
                    // close it after response
                    progressDialog.dismiss();
                    Log.v(TAG, "통신성공!");

                } else {
                    // close it after response
                    progressDialog.dismiss();
                    Log.v(TAG, "통신 불량!");
                }
            }

            @Override
            public void onFailure(Call<NutrientList> call, Throwable t) {
                // close it after response
                progressDialog.dismiss();
                Log.v(TAG, "통신 실패!");

                t.printStackTrace();
                //Response failed
                Log.e(TAG, "Response: " + t.getMessage());
            }
        });

    }
}
