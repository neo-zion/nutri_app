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


import com.example.my33_navigationdrawer.Adapter.Search.OnItemClickListener2;
import com.example.my33_navigationdrawer.Adapter.Search.SearchAdapter2;
import com.example.my33_navigationdrawer.DTO.SearchDTO.MList;

import java.util.ArrayList;
import java.util.HashSet;

public class FragmentSearchResult2 extends Fragment {

    private RecyclerView recyclerView;
    private SearchAdapter2 listAdapter;
    private FragmentSearchResult2_1 fragmentSearchResult2_1;
    private Context mContext;
    private static final String TAG = "FragmentSearchResult2";
    private TextView tvSearch2;


    public static FragmentSearchResult2 newInstance(Bundle bundle) {
        FragmentSearchResult2 fragmentSearchResult2 = new FragmentSearchResult2();
        fragmentSearchResult2.setArguments(bundle);
        return fragmentSearchResult2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_searchresult2,
                container, false);
        mContext = container.getContext();
        final Bundle bundle = getArguments();
        String search = bundle.getString("search");
        Log.d(TAG, "결과2번째 search: " + search);
        ArrayList<MList> mList = (ArrayList<MList>) bundle.getSerializable("mList");

        //검색어 표시
        tvSearch2 = rootView.findViewById(R.id.tvSearch2);
        String sourceString = "\""+search + "\"의 검색 결과";
        tvSearch2.setText(sourceString);


        recyclerView = rootView.findViewById(R.id.listView2);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //Hashset을 통해 중복데이터 제거
        final ArrayList<MList> viewList2 = new ArrayList<>(new HashSet<>(mList));



        listAdapter = new SearchAdapter2(viewList2);
        recyclerView.setAdapter(listAdapter);

        fragmentSearchResult2_1 = new FragmentSearchResult2_1();

        listAdapter.setOnItemClickListener(new OnItemClickListener2() {
            @Override
            public void onItemClick(SearchAdapter2.ViewHolder holderm, View view, int position) {
                bundle.putSerializable("viewlist2", viewList2);
                bundle.putInt("position",position);
                Log.d(TAG, "onItemClick: ========================"+ viewList2);
                Log.d(TAG, "onItemClick: ========================"+ position);
                Log.d(TAG, "onItemClick: ========================"+ bundle);
                fragmentSearchResult2_1.setArguments(bundle);

                ((MainActivity2)getActivity()).replaceFragment(fragmentSearchResult2_1.newInstance(bundle));
            }
        });

        return rootView;
    }

}
