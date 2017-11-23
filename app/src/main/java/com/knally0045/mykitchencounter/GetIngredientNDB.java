package com.knally0045.mykitchencounter;

import android.content.Context;

import java.util.UUID;


/**
 * Created by kyleg on 11/23/2017.
 */

public class GetIngredientNDB {

    private String mIngredientSearchString;
    private String mNdbNo;
    private UUID mIngredientUuid;
    private Context mContext;

    public GetIngredientNDB(String ingredient, Context context) {

        mContext = context;
        mIngredientSearchString = context.getString(R.string.ndb_number_url_prefix) + ingredient + context.getString(R.string.ndb_number_url_suffix);
        URLFetcher anIngredient = new URLFetcher();


    }

}
