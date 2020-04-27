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


import com.example.my33_navigationdrawer.DTO.NoticeDTO.NoticeList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class FragmentNotice1 extends Fragment {

    private TextView tvNo1Regdate, tvNo1Title, tvNo1Content;
    private Button backbtn;


    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static FragmentNotice1 newInstance(Bundle bundle) {
        FragmentNotice1 fragmentNotice1 = new FragmentNotice1();
        fragmentNotice1.setArguments(bundle);
        return fragmentNotice1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_notice_1,
                container, false);
        backbtn = rootView.findViewById(R.id.noti1Back);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        Bundle bundle = getArguments();
        if (bundle == null) {
            Toast.makeText(getActivity(), "arguments is null " , Toast.LENGTH_LONG).show();
        } else {
            List<NoticeList> list = (List<NoticeList>) bundle.getSerializable("noticeLists");
            int num = bundle.getInt("position");
            NoticeList dto = list.get(num);
            Log.d("list", "onCreateView: "+list+"============"+num);

            tvNo1Regdate = rootView.findViewById(R.id.tvNo1Regdate);
            tvNo1Title = rootView.findViewById(R.id.tvNo1Title);
            tvNo1Content = rootView.findViewById(R.id.tvNo1Content);

            //밀리세컨즈를 받아 옴.
            String timevalue = dto.getRegdate();

            // seconds 로 계산할지, milliseconds로 계산할지에 따라 2가지 방법 가능
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

            tvNo1Regdate.setText(formattedDate);
            tvNo1Title.setText(dto.getTitle());
            tvNo1Content.setText(dto.getContent());

        }

        return rootView;
    }
}
