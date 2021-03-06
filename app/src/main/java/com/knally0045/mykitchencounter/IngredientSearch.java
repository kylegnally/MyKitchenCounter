package com.knally0045.mykitchencounter;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by kyleg on 11/17/2017.
 */

public class IngredientSearch {

    private static IngredientSearch mIngredientSearch;
    private ArrayList<PossibleIngredientMatch> mPossibleIngredientMatches;

    // sigleton
    public static IngredientSearch get(Context context) {
        if (mIngredientSearch == null) {
            mIngredientSearch = new IngredientSearch(context);
        }
        return mIngredientSearch;
    }

    private IngredientSearch(Context context) {
        mPossibleIngredientMatches = new ArrayList<>();
    }

    public ArrayList<PossibleIngredientMatch> getPossibleIngredientMatches() {
        return mPossibleIngredientMatches;
    }

}
