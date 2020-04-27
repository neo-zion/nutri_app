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

import com.example.my33_navigationdrawer.DTO.StreamDTO.VList;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import kr.co.prnd.YouTubePlayerView;

public class FragmentStreamView1 extends Fragment {

    private TextView tvSt1Regdate, tvSt1Title, tvSt1Content;
    private Button backbtn, playbtn;
    private YouTubePlayerView youTubePlayerView;

    private static final String youtubeDeveloperKey = "AIzaSyBQ-tCiyj3hUfxigg52-wSQg7g7CJFFF4E";



    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static FragmentStreamView1 newInstance(Bundle bundle) {
        FragmentStreamView1 fragmentStreamView1 = new FragmentStreamView1();
        fragmentStreamView1.setArguments(bundle);
        return fragmentStreamView1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_streamview_1,
                container, false);


        backbtn = rootView.findViewById(R.id.stream1Back);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        Bundle bundle = getArguments();
        if (bundle == null) {
            Toast.makeText(getActivity(), "arguments is null ", Toast.LENGTH_LONG).show();
        } else {
            List<VList> list = (List<VList>) bundle.getSerializable("videoLists");
            int num = bundle.getInt("position");
            final VList dto = list.get(num);
            Log.d("list", "onCreateView: " + list + "============" + num);
            tvSt1Regdate = rootView.findViewById(R.id.tvSt1Regdate);
            tvSt1Title = rootView.findViewById(R.id.tvSt1Title);
            youTubePlayerView = rootView.findViewById(R.id.you_tube_player_view);


            //밀리세컨즈를 받아 옴.
            String timevalue = dto.getRegdate();

            // seconds 로 계산할지, milliseconds로 계산할지에 따라 2가지 방법 가능
            long seconds = Long.parseLong(timevalue) / 1000;
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

            tvSt1Regdate.setText(formattedDate);
            tvSt1Title.setText(dto.getTitle());
            String videoID = dto.getContent();
            youTubePlayerView.play(videoID, new YouTubePlayerView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });
        }

            return rootView;
        }

    }
