package com.knally0045.mykitchencounter;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by kyleg on 11/17/2017.
 */

public class IngredientSearch {

    private static IngredientSearch mIngredientSearch;
    //private NDBFetcher mNDBFetcher;
    private String mSearchString;

    public static IngredientSearch get(Context context, String searchString) {
        if (mIngredientSearch == null) {
            mIngredientSearch = new IngredientSearch(context, searchString);
        }
        return mIngredientSearch;
    }

    private IngredientSearch(final Context context, String searchString) {
        NDBFetcher mNDBFetcher = new NDBFetcher();
        mSearchString = searchString;
        //mNDBFetcher.fetchMatches(mSearchString, context);

    }

}


