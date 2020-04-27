package com.example.my33_navigationdrawer.Adapter.Search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my33_navigationdrawer.DTO.SearchDTO.IList;
import com.example.my33_navigationdrawer.R;

import java.util.ArrayList;


public class SearchAdapter1 extends RecyclerView.Adapter<SearchAdapter1.ViewHolder> implements OnItemClickListener {

    private OnItemClickListener listener;

    private ArrayList<IList> items =null;

    public SearchAdapter1(ArrayList<IList> itemDTOS) {
        items = itemDTOS;
    }

    public IList getItem(int position) {
        return items.get(position);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.search_item, parent, false);

        return new ViewHolder(itemView, this); //7
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d("main:adapter", "onBindViewHolder: " + position);

        final IList item = items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemClick(ViewHolder holderm, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holderm, view, position);
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItem;



        // 만드는 순서 2
        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            tvItem = itemView.findViewById(R.id.tvItem);

            // 만드는 순서 3
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });


        }

        public void setItem(IList item) {

            tvItem.setText(item.getPrdctNM());
        }

    }
}
