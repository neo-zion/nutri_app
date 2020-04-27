package com.example.my33_navigationdrawer.DTO.MyHealthDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nutrient {
    @SerializedName("nutrient")
    @Expose
    private String nutrient;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("average_intake")
    @Expose
    private String averageIntake;
    @SerializedName("recommended_intake")
    @Expose
    private String recommendedIntake;
    @SerializedName("sufficient_intake")
    @Expose
    private String sufficientIntake;
    @SerializedName("maximum_intake")
    @Expose
    private String maximumIntake;

    public String getNutrient() {
        return nutrient;
    }

    public void setNutrient(String nutrient) {
        this.nutrient = nutrient;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAverageIntake() {
        return averageIntake;
    }

    public void setAverageIntake(String averageIntake) {
        this.averageIntake = averageIntake;
    }

    public String getRecommendedIntake() {
        return recommendedIntake;
    }

    public void setRecommendedIntake(String recommendedIntake) {
        this.recommendedIntake = recommendedIntake;
    }

    public String getSufficientIntake() {
        return sufficientIntake;
    }

    public void setSufficientIntake(String sufficientIntake) {
        this.sufficientIntake = sufficientIntake;
    }

    public String getMaximumIntake() {
        return maximumIntake;
    }

    public void setMaximumIntake(String maximumIntake) {
        this.maximumIntake = maximumIntake;
    }

}
