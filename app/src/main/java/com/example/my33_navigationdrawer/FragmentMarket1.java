package com.example.my33_navigationdrawer;

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
import androidx.fragment.app.FragmentManager;


import com.example.my33_navigationdrawer.DTO.MarketDTO.MarketList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class FragmentMarket1 extends Fragment {

    private TextView tvMa1Regdate, tvMa1Title, tvMa1Content;
    private Button btnMList1Back;
    private FragmentManager fragmentManager;


    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static FragmentMarket1 newInstance(Bundle bundle) {
        FragmentMarket1 fragmentMarket1 = new FragmentMarket1();
        fragmentMarket1.setArguments(bundle);
        return fragmentMarket1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_market_1,
                container, false);

        Bundle bundle = getArguments();
        if (bundle == null) {
            Toast.makeText(getActivity(), "arguments is null " , Toast.LENGTH_LONG).show();
        } else {
            List<MarketList> list = (List<MarketList>) bundle.getSerializable("marketLists");
            int num = bundle.getInt("position");
            MarketList dto = list.get(num);
            Log.d("list", "onCreateView: "+list+"============"+num);

            tvMa1Regdate = rootView.findViewById(R.id.tvMa1Regdate);
            tvMa1Title = rootView.findViewById(R.id.tvMa1Title);
            tvMa1Content = rootView.findViewById(R.id.tvMa1Content);
            btnMList1Back = rootView.findViewById(R.id.btnMList1Back);


            btnMList1Back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager = getParentFragmentManager();
                    fragmentManager.popBackStack();
                }
            });

//            String timevalue = dto.getMRegdate();
//            Date date = new Date(timevalue);

            //밀리세컨즈를 받아 옴.
            String timevalue = dto.getMRegdate();

            // seconds 로 계산할지, milliseconds로 계산할지에 따라 2가지로 나뉨.
            long seconds= Long.parseLong(timevalue)/1000;
            long millis = Long.parseLong(timevalue);

            //첫번째 miili로 계산
            Date date = new Date(millis);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String formattedDate = sdf.format(date);

            //두번째 seconds로 계산
//            LocalDateTime datetime = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                datetime = LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.UTC);
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
//                formattedDate = datetime.format(formatter);
//            }

            tvMa1Regdate.setText(formattedDate);
            tvMa1Title.setText(dto.getMTitle());
            tvMa1Content.setText(dto.getMContent());
        }
        return rootView;
    }
}
