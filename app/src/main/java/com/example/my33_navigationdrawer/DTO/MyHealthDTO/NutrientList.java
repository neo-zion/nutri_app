package com.example.my33_navigationdrawer.DTO.MyHealthDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NutrientList {
    @SerializedName("nutrient")
    @Expose
    private List<Nutrient> nutrient = null;

    public List<Nutrient> getNutrient() {
        return nutrient;
    }

    public void setNutrient(List<Nutrient> nutrient) {
        this.nutrient = nutrient;
    }

}