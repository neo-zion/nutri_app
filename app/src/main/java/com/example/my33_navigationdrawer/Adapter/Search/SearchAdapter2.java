package com.example.my33_navigationdrawer.Adapter.Search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my33_navigationdrawer.DTO.SearchDTO.MList;
import com.example.my33_navigationdrawer.R;

import java.util.ArrayList;


public class SearchAdapter2 extends RecyclerView.Adapter<SearchAdapter2.ViewHolder> implements OnItemClickListener2 {

    OnItemClickListener2 listener2;


    ArrayList<MList> items =null;
    public Context mContext;

    public SearchAdapter2(ArrayList<MList> itemDTOS) {
        items = itemDTOS;
    }


    public MList getItem(int position) {
        return items.get(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView2 = inflater.inflate(R.layout.search_item, parent, false);

        return new ViewHolder(itemView2, this); //7
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d("main:adapter", "onBindViewHolder: " + position);

        final MList item2 = items.get(position);
        holder.setItem(item2);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemClick(ViewHolder holderm, View view, int position) {
        if (listener2 != null) {
            listener2.onItemClick(holderm, view, position);
        }
    }
    public void setOnItemClickListener(OnItemClickListener2 listener2){
        this.listener2 = listener2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;


        // 만드는 순서 2
        public ViewHolder(@NonNull View itemView, final SearchAdapter2 listener2) {
            super(itemView);

            name = itemView.findViewById(R.id.tvItem);

            // 만드는 순서 3
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener2 != null) {
                        int position = getAdapterPosition();
                        listener2.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });


        }
        public void setItem(MList item) {
             name.setText(item.getAplcRAWMTRLNM());
        }
    }
}
