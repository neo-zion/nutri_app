package com.example.my33_navigationdrawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.my33_navigationdrawer.Adapter.MarketAdapter;
import com.example.my33_navigationdrawer.Adapter.OnItemClickListener_Market;
import com.example.my33_navigationdrawer.DTO.MarketDTO.MarketDTO;
import com.example.my33_navigationdrawer.DTO.MarketDTO.MarketList;
import com.example.my33_navigationdrawer.api.RestMethods;
import com.example.my33_navigationdrawer.retrofit2.RestClient;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMarket extends Fragment {
    private static final String TAG = "FragmentMarket";
    private RecyclerView recyclerView;
    private Context mContext;
    private RestMethods restMethods;
    private ArrayList<MarketList> marketLists;
    private MarketAdapter marketAdapter;
    private Bundle bundle;
    private FragmentMarket1 fragmentMarket1;
    private ProgressDialog progressDialog;
    private Button btnMListBack;
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_market,
                                            container, false);

        mContext = container.getContext();
        btnMListBack = rootView.findViewById(R.id.btnMListBack);

        btnMListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        recyclerView = rootView.findViewById(R.id.marketRecycle);
        recyclerView.setNestedScrollingEnabled(false);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//       LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        fragmentMarket1 = new FragmentMarket1();
        bundle = new Bundle();

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


        getLists();


        return rootView;
    }

    public void getLists() {
        //Builds HTTP Client for API Calls
        restMethods = RestClient.buildHTTPClient();

        restMethods.marketList().enqueue(new Callback<MarketDTO>() {
            @Override
            public void onResponse(Call<MarketDTO> call, Response<MarketDTO> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+ response.body().toString());
                    MarketDTO marketDTO = response.body();
                    marketLists = (ArrayList<MarketList>) marketDTO.getMarketList();
                    marketAdapter = new MarketAdapter(marketLists);
                    recyclerView.setAdapter(marketAdapter);
                    // close it after response
                    progressDialog.dismiss();
                    Log.v(TAG, "Yes!");

                    marketAdapter.setOnItemClickListener(new OnItemClickListener_Market() {
                        @Override
                        public void onItemClick(MarketAdapter.ViewHolder holderm, View view, int position) {
                            bundle.putSerializable("marketLists", marketLists);
                            bundle.putInt("position",position);
                            ((MainActivity2)getActivity()).replaceFragment(fragmentMarket1.newInstance(bundle));
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<MarketDTO> call, Throwable t) {
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
