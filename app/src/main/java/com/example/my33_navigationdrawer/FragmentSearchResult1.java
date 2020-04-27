package com.example.my33_navigationdrawer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my33_navigationdrawer.Adapter.Search.OnItemClickListener;
import com.example.my33_navigationdrawer.Adapter.Search.SearchAdapter1;
import com.example.my33_navigationdrawer.DTO.SearchDTO.IList;

import java.util.ArrayList;
import java.util.HashSet;


public class FragmentSearchResult1 extends Fragment {

    private RecyclerView recyclerView;
    private SearchAdapter1 listAdapter;
    private Bundle bundle;
    private FragmentSearchResult1_1 fragmentSearchResult1_1;
    private Context mContext;
    private static final String TAG = "FragmentSearchResult1";
    private TextView tvSearch1;
    private String search;
    private  ArrayList<IList> viewList1;
    private ArrayList<IList> iList;

    public static FragmentSearchResult1 newInstance(Bundle bundle) {
        FragmentSearchResult1 fragmentSearchResult1 = new FragmentSearchResult1();
        fragmentSearchResult1.setArguments(bundle);
        return fragmentSearchResult1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_searchresult1,
                container, false);
        mContext = container.getContext();
        bundle = getArguments();

        if (bundle != null) {
           search = bundle.getString("search");
        }
        iList = (ArrayList<IList>) bundle.getSerializable("iList");

        tvSearch1 = rootView.findViewById(R.id.tvSearch1);
        String sourceString = "\""+search + "\"의 검색 결과";
        tvSearch1.setText(sourceString);


        recyclerView = rootView.findViewById(R.id.listView);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);




        //Hashset을 통해 중복데이터 제거
        viewList1 = new ArrayList<>(new HashSet<>(iList));

        Log.d("viewList2", "onCreateView: "+ viewList1);


        listAdapter = new SearchAdapter1(viewList1);
        recyclerView.setAdapter(listAdapter);

        fragmentSearchResult1_1 = new FragmentSearchResult1_1();

        listAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(SearchAdapter1.ViewHolder holderm, View view, int position) {
                bundle.putSerializable("viewlist1", viewList1);
                bundle.putInt("position",position);
                Log.d(TAG, "onItemClick: ========================"+ viewList1);
                Log.d(TAG, "onItemClick: ========================"+ position);
                Log.d(TAG, "onItemClick: ========================"+ bundle);

                ((MainActivity2)getActivity()).replaceFragment(fragmentSearchResult1_1.newInstance(bundle));

            }
        });

        return rootView;
    }



}
