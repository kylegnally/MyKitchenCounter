package com.knally0045.mykitchencounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by kyleg on 11/23/2017.
 */

public class IngredientFragment extends Fragment {

    private Button mAddIngredientButton;
    private EditText mIngredientString;
    private LinearLayout mNewIngredientLayout;
    private IngredientSearch mIngredientSearch;
    private Context mContext;

    private String mOneIngredient;
    private String mTemporaryResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.new_ingredient, container, false);

        mNewIngredientLayout = (LinearLayout) v.findViewById(R.id.new_ingredient_layout);
        mAddIngredientButton = (Button) v.findViewById(R.id.add_ingredient_button);
        mAddIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater newInflater = LayoutInflater.from(getContext());
                View view1 = newInflater.inflate(R.layout.single_new_ingredient, container, false);

                mContext = getActivity();
                mIngredientSearch = IngredientSearch.get(mContext);
                new FetchMatchesTask().execute();


                mNewIngredientLayout.addView(view1);
                view1.requestFocus();

                // you will want to add another button to this layout
                // that will also be at the top which gets enabled after a
                // single ingredient and which sends the list as a search to
                // the database to look up NDB numbers from the web service

            }
        });

        mIngredientString = (EditText) v.findViewById(R.id.new_ingredient);
        mIngredientString.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence entry, int start, int before, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mOneIngredient = mIngredientString.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    private class FetchMatchesTask extends AsyncTask<Void,Void,ArrayList<PossibleIngredientMatch>> {
        @Override
        protected ArrayList<PossibleIngredientMatch> doInBackground(Void... voids) {
            return new NDBFetcher().fetchMatches(mOneIngredient, mContext);
        }

        @Override
        protected void onPostExecute(ArrayList<PossibleIngredientMatch> dbMatches) {
            IngredientSearch search = IngredientSearch.get(getActivity());
            //search.setPossibleIngredientMatches(dbMatches);
            search.getPossibleIngredientMatches();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(R.string.dialog_title);
            ArrayList<String> itemNames = new ArrayList<>();
            ArrayList<String> itemNdb = new ArrayList<>();

            for (PossibleIngredientMatch item : dbMatches) {
                itemNames.add(item.getIngredientName());
                itemNdb.add(item.getIngredientNDB());
            }

            builder.setSingleChoiceItems(itemNames.toArray(new String[itemNames.size()]), 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            // populate the alert dialog here

        }
    }



    public void DisableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

}
