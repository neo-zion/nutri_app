package com.example.my33_navigationdrawer.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my33_navigationdrawer.DTO.Setting;
import com.example.my33_navigationdrawer.R;

import java.util.ArrayList;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder>
                               implements OnItemClickListener_Settings { // 4
    OnItemClickListener_Settings listener;  // 5

    ArrayList<Setting> items = new ArrayList<>();
    public Context mContext;

    // 생성자
    public SettingsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    // 메인에서 아이템 추가 메소드
    public void addItem(Setting item){
        items.add(item);
    }

    // 메인에서 아이템 모두 제거 메소드
    public void allRemoveItem(){
        items.clear();
    }

    // 메인에서 선택한 아이템 가져오기
    public Setting getItem(int position){
        return items.get(position);
    }

    // 6
    @Override
    public void onItemClick(ViewHolder holderm, View view, int position) {
        if(listener != null){
            listener.onItemClick(holderm, view, position);
        }
    }

    // 메인에서 호출할 클릭메소드 : 8
    public void setOnItemClickListener(OnItemClickListener_Settings listener){
        this.listener = listener;
    }

    // 화면과 연결
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.setting_item, parent, false);

        return new ViewHolder(itemView, this); // 7
    }

    // 연결된 화면을 만들어 데이터와 연결
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d("main:adapter", "onBindViewHolder: " + position);

        final Setting item = items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imageView;

        // 2
        public ViewHolder(@NonNull View itemView, final OnItemClickListener_Settings listener ) {
            super(itemView);

            tvName = itemView.findViewById(R.id.settingText);
            imageView = itemView.findViewById(R.id.settingImage);

            // 3
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this,
                                view, position);
                    }
                }
            });
        }

        public void setItem(Setting item){
            tvName.setText(item.getName());
            imageView.setImageResource(item.getResId());
        }

    }

}
