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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my33_navigationdrawer.Adapter.OnItemClickListener_StreamView;
import com.example.my33_navigationdrawer.Adapter.StreamViewAdapter;
import com.example.my33_navigationdrawer.DTO.StreamDTO.StreamDTO;
import com.example.my33_navigationdrawer.DTO.StreamDTO.VList;
import com.example.my33_navigationdrawer.api.RestMethods;
import com.example.my33_navigationdrawer.retrofit2.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentStreamView extends Fragment {
    private static final String TAG = "FragmentStreamView";
    private RecyclerView recyclerView;
    private Context mContext;
    private RestMethods restMethods;
    private ArrayList<VList> videoLists;
    private StreamViewAdapter listAdapter;
    private ProgressDialog progressDialog;
    private FragmentStreamView1 fragmentStreamView1;
    private Button backbtn;
    private Bundle bundle;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_streamview,
                container, false);

        mContext = container.getContext();


        recyclerView = rootView.findViewById(R.id.streamRecycle);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        bundle = new Bundle();
        fragmentStreamView1 = new FragmentStreamView1();
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
        backbtn= rootView.findViewById(R.id.streamBack);

        getVideoLists();
        final FragmentManager fragmentManager = getParentFragmentManager();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        return rootView;
    }

    private void getVideoLists() {
        //Builds HTTP Client for API Calls
        restMethods = RestClient.buildHTTPClient();

        restMethods.streamView().enqueue(new Callback<StreamDTO>() {
            @Override
            public void onResponse(Call<StreamDTO> call, Response<StreamDTO> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+ response.body().toString());
                    StreamDTO streamDTO = response.body();
                    videoLists = (ArrayList<VList>) streamDTO.getVList();
                    listAdapter = new StreamViewAdapter(videoLists, mContext);
                    recyclerView.setAdapter(listAdapter);
                    // close it after response
                    progressDialog.dismiss();
                    Log.v(TAG, "통신성공!");




                    listAdapter.setOnItemClickListener(new OnItemClickListener_StreamView() {
                        @Override
                        public void onItemClick(StreamViewAdapter.ViewHolder holderm, View view, int position) {
                            bundle.putSerializable("videoLists", videoLists);
                            bundle.putInt("position",position);
                            ((MainActivity2)getActivity()).replaceFragment(fragmentStreamView1.newInstance(bundle));
                        }
                    });

                } else {
                    // close it after response
                    progressDialog.dismiss();
                    Log.v(TAG, "통신 불량!");
                }
            }

            @Override
            public void onFailure(Call<StreamDTO> call, Throwable t) {
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