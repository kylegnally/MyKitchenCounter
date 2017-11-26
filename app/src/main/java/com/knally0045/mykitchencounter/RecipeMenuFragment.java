package com.knally0045.mykitchencounter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by kyleg on 11/20/2017.
 */

public class RecipeMenuFragment extends Fragment {

    private ImageButton mNewRecipeButton;
    private ImageButton mBrowseSavedRecipesButton;
    private TextView mNewRecipeTextView;
    private TextView mBrowseRecipesTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);

        mNewRecipeButton = (ImageButton) v.findViewById(R.id.new_recipe_button);
        mNewRecipeTextView = (TextView) v.findViewById(R.id.new_recipe_text);
        mNewRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment ingredientsFragment = new IngredientFragment();
                FragmentTransaction ingredientsTransaction = getFragmentManager().beginTransaction();
                ingredientsTransaction.replace(R.id.fragment_container, ingredientsFragment);
                ingredientsTransaction.addToBackStack(null);
                ingredientsTransaction.commit();
            }
        });

        mBrowseSavedRecipesButton = (ImageButton) v.findViewById(R.id.recipe_book_button);
        mBrowseRecipesTextView = (TextView) v.findViewById(R.id.browse_recipe_text);
        mBrowseSavedRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return v;
    }
}
