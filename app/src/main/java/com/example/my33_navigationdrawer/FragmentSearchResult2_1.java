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


import com.example.my33_navigationdrawer.DTO.SearchDTO.MList;

import java.util.List;

public class FragmentSearchResult2_1 extends Fragment {
    private static final String TAG = "FragmentSearchResult2_1";
    private TextView tvAPLC_RAWMTRL_NM, tvFNCLTY_CN, tvIFTKN_ATNT_MATR_CN, tvDAY_INTK_CN, tvHF_FNCLTY_MTRAL_RCOGN_NO, tvPRMS_DT,
            tvINDUTY_NM, tvBSSH_NM, tvADDR;
    private String url, search;

    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static FragmentSearchResult2_1 newInstance(Bundle bundle) {
        FragmentSearchResult2_1 fragmentSearchResult2_1 = new FragmentSearchResult2_1();
        fragmentSearchResult2_1.setArguments(bundle);
        return fragmentSearchResult2_1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_searchresult2_1,
                container, false);


        Bundle bundle = getArguments();

        if (bundle == null) {
            Toast.makeText(getActivity(), "arguments is null " , Toast.LENGTH_LONG).show();
        } else {

            List<MList> list = (List<MList>) bundle.getSerializable("viewlist2");
            int num = bundle.getInt("position");
            search = bundle.getString("search");
            Log.d(TAG, "search: " + search);
            MList dto = list.get(num);
            Log.d("list", "onCreateView: "+list+"============"+num);

            tvAPLC_RAWMTRL_NM = rootView.findViewById(R.id.tvAPLC_RAWMTRL_NM);
            tvFNCLTY_CN = rootView.findViewById(R.id.tvFNCLTY_CN);
            tvIFTKN_ATNT_MATR_CN = rootView.findViewById(R.id.tvIFTKN_ATNT_MATR_CN);
            tvDAY_INTK_CN = rootView.findViewById(R.id.tvDAY_INTK_CN);
            tvHF_FNCLTY_MTRAL_RCOGN_NO = rootView.findViewById(R.id.tvHF_FNCLTY_MTRAL_RCOGN_NO);
            tvPRMS_DT = rootView.findViewById(R.id.tvPRMS_DT);
            tvINDUTY_NM = rootView.findViewById(R.id.tvINDUTY_NM);
            tvBSSH_NM = rootView.findViewById(R.id.tvBSSH_NM);
            tvADDR = rootView.findViewById(R.id.tvADDR);

            tvAPLC_RAWMTRL_NM.setText(dto.getAplcRAWMTRLNM());
            tvFNCLTY_CN.setText(dto.getFncltyCN());
            tvIFTKN_ATNT_MATR_CN.setText(dto.getIftknATNTMATRCN());
            tvDAY_INTK_CN.setText(dto.getDayINTKCN());
            tvHF_FNCLTY_MTRAL_RCOGN_NO.setText(dto.getHfFNCLTYMTRALRCOGNNO());
            tvPRMS_DT.setText(dto.getPrmsDT());
            tvINDUTY_NM.setText(dto.getIndutyNM());
            tvBSSH_NM.setText(dto.getBsshNM());
            tvADDR.setText(dto.getAddr());





        }

        return rootView;
    }
}
