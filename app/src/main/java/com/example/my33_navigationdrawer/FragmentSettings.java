package com.example.my33_navigationdrawer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my33_navigationdrawer.Adapter.OnItemClickListener_Settings;
import com.example.my33_navigationdrawer.Adapter.SettingsAdapter;
import com.example.my33_navigationdrawer.DTO.Setting;

public class FragmentSettings extends Fragment {

    private RecyclerView recyclerView;
    private Context mContext;
    private FragmentNotice fragmentNotice;
    private FragmentMypage fragmentMypage;
    private FragmentSupport fragmentSupport;
    private Button settingsBack;
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_settings,
                container, false);

        settingsBack = rootView.findViewById(R.id.settingsBack);
        settingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        fragmentNotice = new FragmentNotice();
        fragmentMypage = new FragmentMypage();
        fragmentSupport = new FragmentSupport();

        recyclerView = rootView.findViewById(R.id.settingRecycle);

        mContext = container.getContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        final SettingsAdapter adapter = new SettingsAdapter(mContext);

        adapter.addItem(new Setting("공지사항", R.drawable.sound));
        adapter.addItem(new Setting("내 정보", R.drawable.user));
        adapter.addItem(new Setting("고객지원", R.drawable.list));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener_Settings() {
            @Override
            public void onItemClick(SettingsAdapter.ViewHolder holderm, View view, int position) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction =fragmentManager.beginTransaction();
                if (position ==0) {
                    transaction.replace(R.id.container, fragmentNotice);
                    transaction.addToBackStack(null);
                    transaction.commitAllowingStateLoss();
                } else if (position == 1){
                    transaction.replace(R.id.container, fragmentMypage);
                    transaction.addToBackStack(null);
                    transaction.commitAllowingStateLoss();
                } else if (position == 2) {
                    transaction.replace(R.id.container, fragmentSupport);
                    transaction.addToBackStack(null);
                    transaction.commitAllowingStateLoss();
                }

            }
        });

        return rootView;
    }

}
