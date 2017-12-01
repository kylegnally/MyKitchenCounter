package com.knally0045.mykitchencounter;

import java.util.UUID;

/**
 * Created by kyleg on 11/17/2017.
 */

public class IngredientSearch {

    public String mSearchString;

    public IngredientSearch(String searchString) {
        this.mSearchString = searchString;
    }

    public String getSearchString() {
        return mSearchString;
    }

    public void setSearchString(String searchString) {
        mSearchString = searchString;
    }
}
