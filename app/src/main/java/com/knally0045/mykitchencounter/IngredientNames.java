package com.knally0045.mykitchencounter;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by kyleg on 12/22/2017.
 */

public class IngredientNames {

    private static IngredientNames mIngredientNames;
    private ArrayList<NamesOfIngredients> mIngredientNamesList;

    public static IngredientNames get(Context context) {
        if (mIngredientNames == null) {
            mIngredientNames = new IngredientNames(context);
        }
        return mIngredientNames;
    }

    private IngredientNames(Context context) {
        mIngredientNamesList = new ArrayList<>();
    }

    public ArrayList<NamesOfIngredients> getIngredientNamesList() {
        return mIngredientNamesList;
    }

    public void addIngredientName(String name) {
        mIngredientNamesList.add(new NamesOfIngredients(name));
    }
}
