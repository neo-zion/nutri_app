package com.example.my33_navigationdrawer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class FragmentSearchMain extends Fragment {
    private static final String TAG = "FragmentSearchMain";
    private Fragment selected = null;
    private FragmentSearchResult1 fragmentSearchResult1;
    private FragmentSearchResult2 fragmentSearchResult2;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_searchmain,
                container, false);
        Bundle bundle = getArguments();

        TabLayout tabs = rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("식약처"));
        tabs.addTab(tabs.newTab().setText("개별 인정"));
        fragmentSearchResult1 = new FragmentSearchResult1();
        fragmentSearchResult2 = new FragmentSearchResult2();

        fragmentSearchResult1.setArguments(bundle);
        fragmentSearchResult2.setArguments(bundle);

        fragmentManager = getChildFragmentManager();
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.search_container, fragmentSearchResult1);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d(TAG, "onTabSelected: 선택된 탭 : " + position );

                if (position == 0) {
                    selected = fragmentSearchResult1;
                } else if (position == 1) {

                    selected = fragmentSearchResult2;
                }
                FragmentTransaction tabTransaction = fragmentManager.beginTransaction();
                tabTransaction.replace(R.id.search_container, selected);
                tabTransaction.addToBackStack(null);
                tabTransaction.commitAllowingStateLoss();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }
}
