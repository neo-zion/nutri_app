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

import com.example.my33_navigationdrawer.Adapter.NoticeAdapter;
import com.example.my33_navigationdrawer.Adapter.OnItemClickListener_Notice;
import com.example.my33_navigationdrawer.DTO.NoticeDTO.NoticeDTO;
import com.example.my33_navigationdrawer.DTO.NoticeDTO.NoticeList;
import com.example.my33_navigationdrawer.api.RestMethods;
import com.example.my33_navigationdrawer.retrofit2.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNotice extends Fragment {
    private static final String TAG = "FragmentNotice";
    private RecyclerView recyclerView;
    private Context mContext;
    private RestMethods restMethods;
    private ArrayList<NoticeList> noticeLists;
    private NoticeAdapter listAdapter;
    private Bundle bundle;
    private FragmentNotice1 fragmentNotice1;
    private ProgressDialog progressDialog;
    private Button backbtn;

    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static FragmentNotice newInstance(Bundle bundle) {
        FragmentNotice fragmentNotice = new FragmentNotice();
        fragmentNotice.setArguments(bundle);
        return fragmentNotice;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_notice,
                                            container, false);

        mContext = container.getContext();


        recyclerView = rootView.findViewById(R.id.noticeRecycle);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        fragmentNotice1 = new FragmentNotice1();
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
        backbtn= rootView.findViewById(R.id.notiBack);

        getLists();
        final FragmentManager fragmentManager = getParentFragmentManager();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        return rootView;
    }

    public void getLists() {
        //Builds HTTP Client for API Calls
        restMethods = RestClient.buildHTTPClient();

        restMethods.noticeList().enqueue(new Callback<NoticeDTO>() {
            @Override
            public void onResponse(Call<NoticeDTO> call, Response<NoticeDTO> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+ response.body().toString());
                    NoticeDTO noticeDTO = response.body();
                    noticeLists = (ArrayList<NoticeList>) noticeDTO.getList();
                    listAdapter = new NoticeAdapter(noticeLists);
                    recyclerView.setAdapter(listAdapter);
                    // close it after response
                    progressDialog.dismiss();
                    Log.v(TAG, "Yes!");


                    listAdapter.setOnItemClickListener(new OnItemClickListener_Notice() {
                    @Override
                    public void onItemClick(NoticeAdapter.ViewHolder holderm, View view, int position) {
                        bundle.putSerializable("noticeLists", noticeLists);
                        bundle.putInt("position",position);
                        ((MainActivity2)getActivity()).replaceFragment(fragmentNotice1.newInstance(bundle));
                    }
                });
            }
            }

            @Override
            public void onFailure(Call<NoticeDTO> call, Throwable t) {
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
