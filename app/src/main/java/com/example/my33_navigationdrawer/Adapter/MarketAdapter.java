package com.example.my33_navigationdrawer.Adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my33_navigationdrawer.DTO.MarketDTO.MarketList;
import com.example.my33_navigationdrawer.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder> implements OnItemClickListener_Market {

    OnItemClickListener_Market listener3;


    ArrayList<MarketList> items =null;
    LinearLayout parent;

    public MarketAdapter(ArrayList<MarketList> itemDTOS) {
        items = itemDTOS;
    }


    public MarketList getItem(int position) {
        return items.get(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView2 = inflater.inflate(R.layout.market_item, parent, false);

        return new ViewHolder(itemView2, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d("main:adapter", "onBindViewHolder: " + position);

        final MarketList item2 = items.get(position);
        holder.setItem(item2);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemClick(ViewHolder holderm, View view, int position) {
        if (listener3 != null) {
            listener3.onItemClick(holderm, view, position);
        }
    }
    public void setOnItemClickListener(OnItemClickListener_Market listener3){
        this.listener3 = listener3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reg_date;
        TextView title;


        // 만드는 순서 2
        public ViewHolder(@NonNull View itemView, final MarketAdapter listener3) {
            super(itemView);

            parent = itemView.findViewById(R.id.marketParent);
            title = itemView.findViewById(R.id.tvMaTitle);
            reg_date = itemView.findViewById(R.id.tvMaRegdate);

            // 만드는 순서 3
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener3 != null) {
                        int position = getAdapterPosition();
                        listener3.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });


        }
        public void setItem(MarketList item) {
            String timevalue = item.getMRegdate();
            long millis = Long.parseLong(timevalue);

            Date date = new Date(millis);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.KOREAN);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String formattedDate = sdf.format(date);

//            long seconds = Long.parseLong(timevalue)/1000;
//            LocalDateTime datetime = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                datetime = LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.UTC);
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
//                formattedDate = datetime.format(formatter);
//            }

            Random rnd = new Random();
            int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            parent.setBackgroundColor(currentColor);

            reg_date.setText(formattedDate);
            title.setText(item.getMTitle());
        }
    }
}
