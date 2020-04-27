package com.example.my33_navigationdrawer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.my33_navigationdrawer.DTO.SearchDTO.IList;

import java.util.List;

public class FragmentSearchResult1_1 extends Fragment {

    private TextView tvPrdct_nm, tvPrimary_fnclty, tvIfkn_atnt_matr_cn, tvDay_intk_lowlimit, tvDay_intk_highlimit, tvIntk_limit,
            tvSkll_ix_itdnt_rawmtrl, tvIntk_memo, tvCret_dtm;
    private Bundle bundle;
    private String url, search;



    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static FragmentSearchResult1_1 newInstance(Bundle bundle) {
        FragmentSearchResult1_1 fragmentSearchResult1_1 = new FragmentSearchResult1_1();
        fragmentSearchResult1_1.setArguments(bundle);
        return fragmentSearchResult1_1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_searchresult1_1,
                container, false);


        bundle = getArguments();

        if (bundle == null) {
            Toast.makeText(getActivity(), "arguments is null " , Toast.LENGTH_LONG).show();
        } else {

            List<IList> list = (List<IList>) bundle.getSerializable("viewlist1");
            int num = bundle.getInt("position");
            search = bundle.getString("search");
            IList dto = list.get(num);
            Log.d("list", "onCreateView: "+list+"============"+num);

            tvPrdct_nm = rootView.findViewById(R.id.tvPrdct_nm);
            tvPrimary_fnclty = rootView.findViewById(R.id.tvPrimary_fnclty);
            tvIfkn_atnt_matr_cn = rootView.findViewById(R.id.tvIfkn_atnt_matr_cn);
            tvDay_intk_lowlimit = rootView.findViewById(R.id.tvDay_intk_lowlimit);
            tvDay_intk_highlimit = rootView.findViewById(R.id.tvDay_intk_highlimit);
            tvIntk_limit = rootView.findViewById(R.id.tvIntk_limit);
            tvSkll_ix_itdnt_rawmtrl = rootView.findViewById(R.id.tvSkll_ix_itdnt_rawmtrl);
            tvIntk_memo = rootView.findViewById(R.id.tvIntk_memo);
            tvCret_dtm = rootView.findViewById(R.id.tvCret_dtm);

            tvPrdct_nm.setText(dto.getPrdctNM());
            tvPrimary_fnclty.setText(dto.getPrimaryFNCLTY());
            tvIfkn_atnt_matr_cn.setText(dto.getIftknATNTMATRCN());
            tvDay_intk_lowlimit.setText(dto.getDayINTKLOWLIMIT());
            tvDay_intk_highlimit.setText(dto.getDayINTKHIGHLIMIT());
            tvIntk_limit.setText(dto.getIntkLIMIT());
            tvSkll_ix_itdnt_rawmtrl.setText(dto.getSkllIXIRDNTRAWMTRL());
            tvIntk_memo.setText(dto.getIntkMEMO());
            tvCret_dtm.setText(dto.getCretDTM());



        }

        return rootView;
    }
}
