package com.knally0045.mykitchencounter;

/**
 * Created by kyleg on 12/15/2017.
 */

public class IngredientNutrition {

    private int mCalories;
    private int mProtein;
    private int mFat;

    public IngredientNutrition(int calories, int protein, int fat) {
        this.mCalories = calories;
        this.mProtein = protein;
        this.mFat = fat;
    }

    public int getCalories() {
        return mCalories;
    }

    public void setCalories(int calories) {
        mCalories = calories;
    }

    public int getProtein() {
        return mProtein;
    }

    public void setProtein(int protein) {
        mProtein = protein;
    }

    public int getFat() {
        return mFat;
    }

    public void setFat(int fat) {
        mFat = fat;
    }
}
