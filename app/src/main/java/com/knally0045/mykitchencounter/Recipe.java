package com.knally0045.mykitchencounter;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by kyleg on 11/17/2017.
 */

public class Recipe {

    private static Recipe sRecipe;

    private ArrayList<Ingredient> mIngredients;

    public static Recipe get (Context context) {
        if (sRecipe == null) {
            sRecipe = new Recipe(context);
        }

        return sRecipe;
    }

    private Recipe(Context context) {
        mIngredients = new ArrayList<>();
    }

}
