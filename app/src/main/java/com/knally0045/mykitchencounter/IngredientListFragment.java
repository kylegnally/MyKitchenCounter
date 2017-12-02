package com.knally0045.mykitchencounter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

/**
 * Created by kyleg on 12/2/2017.
 */

public class IngredientListFragment extends Fragment {

    private RecyclerView mIngredientRecyclerView;
    // private IngredientAdapter mAdapter;
    private Recipe mRecipe;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        mIngredientRecyclerView = (RecyclerView) view.findViewById(R.id.ingredient_recycler_view);
        mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecipe = Recipe.get(mContext);
        // UpdateUI();
        return view;

    }
}
