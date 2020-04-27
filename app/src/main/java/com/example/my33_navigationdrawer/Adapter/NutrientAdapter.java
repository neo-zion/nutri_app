package com.example.my33_navigationdrawer.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my33_navigationdrawer.DTO.MyHealthDTO.Nutrient;
import com.example.my33_navigationdrawer.R;

import java.util.ArrayList;

public class NutrientAdapter extends RecyclerView.Adapter<NutrientAdapter.ViewHolder> implements OnItemClickListener_Nutrient {

    OnItemClickListener_Nutrient listener3;


    ArrayList<Nutrient> items =null;

    public NutrientAdapter(ArrayList<Nutrient> itemDTOS) {
        items = itemDTOS;
    }


    public Nutrient getItem(int position) {
        return items.get(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView2 = inflater.inflate(R.layout.nutrient_item, parent, false);

        return new ViewHolder(itemView2, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d("main:adapter", "onBindViewHolder: " + position);


        final Nutrient item2 = items.get(position);
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
    public void setOnItemClickListener(OnItemClickListener_Nutrient listener3){
        this.listener3 = listener3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

      TextView tvNutrientFront, tvNutrientBack, tvAverage_intake,tvRecommended_intake, tvSufficient_intake, tvMaximum_intake;


        // 만드는 순서 2
        public ViewHolder(@NonNull View itemView, final NutrientAdapter listener3) {
            super(itemView);

            tvNutrientFront = itemView.findViewById(R.id.tvNutrientFront);
            tvNutrientBack = itemView.findViewById(R.id.tvNutrientBack);
            tvAverage_intake = itemView.findViewById(R.id.tvAverage_intake);
            tvRecommended_intake = itemView.findViewById(R.id.tvRecommended_intake);
            tvSufficient_intake = itemView.findViewById(R.id.tvSufficient_intake);
            tvMaximum_intake = itemView.findViewById(R.id.tvMaximum_intake);

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
        public void setItem(Nutrient item) {

            String nutrientFull = item.getNutrient();
            int idx = nutrientFull.indexOf("("); ;
            String firstNutri = nutrientFull.substring(0, idx);
            String secondNutri = nutrientFull.substring(nutrientFull.indexOf("("));

            tvNutrientFront.setText(firstNutri);
            tvNutrientBack.setText(secondNutri);

            tvAverage_intake.setText(item.getAverageIntake());
            tvRecommended_intake.setText(item.getRecommendedIntake());
            tvSufficient_intake.setText(item.getSufficientIntake());
            tvMaximum_intake.setText(item.getMaximumIntake());


        }
    }
}
