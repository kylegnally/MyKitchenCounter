package com.knally0045.mykitchencounter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by kyleg on 11/23/2017.
 */

public class IngredientFragment extends Fragment {

    private ImageButton mAddIngredientButton;
    private EditText mIngredientString;
    private RecyclerView mIngredientRecyclerView;
    private Recipe mRecipe;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recipe_ingredient_fragment, container, false);

        mIngredientString = (EditText) v.findViewById(R.id.new_ingredient);
        mAddIngredientButton = (ImageButton) v.findViewById(R.id.add_ingredient_button);

//        mIngredientRecyclerView = (RecyclerView) v.findViewById(R.id.ingredient_recycler_view);
//        mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        UpdateUI();
        return v;
    }

    public void UpdateUI() {

    }

}
