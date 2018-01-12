package com.knally0045.mykitchencounter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kyleg on 12/15/2017.
 */

// class to hold nutrition information for one ingredient
public class IngredientNutrition {

    private String mName;
    private Double mWeight;
    private Double mCalories;
    private Double mProtein;
    private Double mFat;

    public IngredientNutrition(String name, Double weight, Double calories, Double protein, Double fat) {
        this.mName = name;
        this.mWeight = weight;
        this.mCalories = calories;
        this.mProtein = protein;
        this.mFat = fat;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Double getWeight() {
        return mWeight;
    }

    public void setWeight(Double weight) {
        mWeight = weight;
    }

    public Double getCalories() {
        return mCalories;
    }

    public void setCalories(Double calories) {
        mCalories = calories;
    }

    public Double getProtein() {
        return mProtein;
    }

    public void setProtein(Double protein) {
        mProtein = protein;
    }

    public Double getFat() {
        return mFat;
    }

    public void setFat(Double fat) {
        mFat = fat;
    }
}
