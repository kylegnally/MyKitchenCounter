package com.knally0045.mykitchencounter;

import java.util.ArrayList;

/**
 * Created by kyleg on 12/13/2017.
 */

public class PossibleIngredientMatch {

    private String mIngredientName;
    private String mIngredientNDB;

    public PossibleIngredientMatch(String name, String ndb) {
        this.mIngredientName = name;
        this.mIngredientNDB = ndb;
    }

    public String getIngredientName() { return mIngredientName; }

    public void setIngredientName(String ingredientName) {
        mIngredientName = ingredientName;
    }

    public String getIngredientNDB() {
        return mIngredientNDB;
    }

    public void setIngredientNDB(String ingredientNDB) {
        mIngredientNDB = ingredientNDB;
    }
}
