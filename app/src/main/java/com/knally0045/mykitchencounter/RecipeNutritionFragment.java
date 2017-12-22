package com.knally0045.mykitchencounter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kyleg on 12/16/2017.
 */

// this class will also need a RacyclerView
public class RecipeNutritionFragment extends IngredientFragment {

    private LinearLayout mNutritionLayout;
    private TextView mNutritionTextView;
    private TextView mIngredientsLabel;
    private TextView mTotalsLabel;
    private TextView mNutritionTotals;
    private RecipeNutrition mRecipeNutrition;
    private ArrayList<IngredientNutrition> mNutritions;
    private Context mContext;

    private Double mCalories;
    private Double mTotalCalories = 0.0;
    private Double mFat;
    private Double mTotalFat = 0.0;
    private Double mProtein;
    private Double mTotalProtein = 0.0;

    private static final String TAG = "nutrition_fragment_tag";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nutrition_layout, container, false);

        mNutritionLayout = (LinearLayout) v.findViewById(R.id.nutrition_linearlayout);
        mNutritionTextView = (TextView) v.findViewById(R.id.ingredient_nutrition);
        mIngredientsLabel = (TextView) v.findViewById(R.id.ingredient_label_textview);
        mIngredientsLabel.setText(R.string.ingredients_label);
        mTotalsLabel = (TextView) v.findViewById(R.id.nutrition_totals_label);
        mTotalsLabel.setText(R.string.total_nutrition_label);
        mNutritionTotals = (TextView) v.findViewById(R.id.nutrition_totals);

        getResults();

        mNutritionTotals.setText(String.format(getResources().getString(R.string.nutrition_totals), mTotalCalories.toString(), mTotalFat.toString(), mTotalProtein.toString()));
        return v;
    }

    public void getResults() {
        ArrayList<IngredientNutrition> ingredients = new ArrayList<>();
        mRecipeNutrition = RecipeNutrition.get(getActivity());
        mNutritions = mRecipeNutrition.getIngredientNutritions();

        // breakpoint must be set prior to this line or app will crash
        for (IngredientNutrition ingredient : mNutritions) {
            ingredients.add(ingredient);
            mCalories = ingredient.getCalories();
            mTotalCalories += mCalories;
            mFat = ingredient.getFat();
            mTotalFat += mFat;
            mProtein = ingredient.getProtein();
            mTotalProtein += mProtein;
            mNutritionTextView.append(String.format(getResources().getString(R.string.nutrients_list),
                    ingredient.getName(),
                    ingredient.getCalories().toString(),
                    ingredient.getFat().toString(),
                    ingredient.getProtein().toString()));
        }
    }
}
