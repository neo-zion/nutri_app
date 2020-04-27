package com.example.my33_navigationdrawer.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my33_navigationdrawer.DTO.StreamDTO.VList;
import com.example.my33_navigationdrawer.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

public class StreamViewAdapter extends RecyclerView.Adapter<StreamViewAdapter.ViewHolder> implements OnItemClickListener_StreamView {

    OnItemClickListener_StreamView listener3;
    Context mContext;
    private static final String youtubeDeveloperKey = "AIzaSyBQ-tCiyj3hUfxigg52-wSQg7g7CJFFF4E";

    ArrayList<VList> items =null;

    public StreamViewAdapter(ArrayList<VList> itemDTOS, Context mContext) {
        items = itemDTOS;
        this.mContext = mContext;
    }


    public VList getItem(int position) {
        return items.get(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView2 = inflater.inflate(R.layout.streamview_item, parent, false);

        return new ViewHolder(itemView2, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d("main:adapter", "onBindViewHolder: " + position);


        final VList item2 = items.get(position);
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
    public void setOnItemClickListener(OnItemClickListener_StreamView listener3){
        this.listener3 = listener3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

     TextView tvStreamTitle;
     YouTubeThumbnailView youTubeThumbnailView;


        // 만드는 순서 2
        public ViewHolder(@NonNull View itemView, final StreamViewAdapter listener3) {
            super(itemView);

            tvStreamTitle = itemView.findViewById(R.id.tvStreamTitle);
            youTubeThumbnailView  =  itemView.findViewById(R.id.show_episode_thumbnail);

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
        public void setItem(VList item) {
            tvStreamTitle.setText(item.getTitle());
            final String videoId = item.getContent();

            youTubeThumbnailView.initialize(youtubeDeveloperKey, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    youTubeThumbnailLoader.setVideo(videoId);
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                            youTubeThumbnailLoader.release();
                        }

                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                        }
                    });
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });




        }
    }
}
