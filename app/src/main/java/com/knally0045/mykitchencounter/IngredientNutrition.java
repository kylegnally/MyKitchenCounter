package com.knally0045.mykitchencounter;

/**
 * Created by kyleg on 12/15/2017.
 */

public class IngredientNutrition {

    private String mName;
    private String mCalories;
    private String mProtein;
    private String mFat;

    public IngredientNutrition(String name, String calories, String protein, String fat) {
        this.mName = name;
        this.mCalories = calories;
        this.mProtein = protein;
        this.mFat = fat;
    }

    public IngredientNutrition() {

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCalories() {
        return mCalories;
    }

    public void setCalories(String calories) {
        mCalories = calories;
    }

    public String getProtein() {
        return mProtein;
    }

    public void setProtein(String protein) {
        mProtein = protein;
    }

    public String getFat() {
        return mFat;
    }

    public void setFat(String fat) {
        mFat = fat;
    }
}
