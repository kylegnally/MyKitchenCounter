package com.knally0045.mykitchencounter;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by kyleg on 12/15/2017.
 */

public class RecipeNutrition {

    private static RecipeNutrition mRecipeNutrition;
    private ArrayList<IngredientNutrition> mIngredientNutritions;

    public static RecipeNutrition get(Context context) {
        if (mRecipeNutrition == null) {
            mRecipeNutrition = new RecipeNutrition(context);
        }
        return mRecipeNutrition;
    }

    private RecipeNutrition(Context context) {
        mIngredientNutritions = new ArrayList<>();
    }

    public ArrayList<IngredientNutrition> getIngredientNutritions() {
        return mIngredientNutritions;
    }

    public void setIngredientNutritions(ArrayList<IngredientNutrition> ingredientNutritions) {
        mIngredientNutritions = ingredientNutritions;
    }

    public void addNutrition(int calories, int protein, int fat) {
        mIngredientNutritions.add(new IngredientNutrition(calories, protein, fat));
    }


}
