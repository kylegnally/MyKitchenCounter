package com.knally0045.mykitchencounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kyleg on 11/23/2017.
 */

public class IngredientFragment extends Fragment {

    private Button mAddIngredientButton;
    private Button mGetNutritionButton;
    private EditText mIngredientString;
    private TextView mIngredientsList;
    private LinearLayout mNewIngredientLayout;
    private Context mContext;
    private ArrayList<String> mFinalIngredients;

    private String mOneIngredient;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        mFinalIngredients = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.new_ingredient, container, false);

        mNewIngredientLayout = (LinearLayout) v.findViewById(R.id.new_ingredient_layout);

        mAddIngredientButton = (Button) v.findViewById(R.id.add_ingredient_button);
        mAddIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOneIngredient = mIngredientString.getText().toString();
                mContext = getActivity();

                // create a new Fetch task and run it
                new FetchMatchesTask().execute();
                mIngredientString.setText(null);

                // set the focus back on this view
                view.requestFocus();
            }
        });

        mGetNutritionButton = (Button) v.findViewById(R.id.get_nutrition_button);
        mGetNutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext = getActivity();

                // start a new Get Nutrition background task and run it
                new FetchNutritionTask().execute();
            }
        });

        mIngredientString = (EditText) v.findViewById(R.id.new_ingredient);
        mIngredientsList = (TextView) v.findViewById(R.id.ingredients_list);
        mIngredientsList.setText(R.string.ingredients_title);

        return v;
    }

    private class FetchNutritionTask extends AsyncTask<Void,Void,ArrayList<IngredientNutrition>> {
        @Override
        protected ArrayList<IngredientNutrition> doInBackground(Void... voids) {

            // get the actual nutrition information
            return new GetNutritionResults().fetchNutrition(mFinalIngredients, mContext);
        }
    }

    private class FetchMatchesTask extends AsyncTask<Void,Void,ArrayList<PossibleIngredientMatch>> {
        @Override
        protected ArrayList<PossibleIngredientMatch> doInBackground(Void... voids) {

            // get the NDB numbers we will use to query the server for nutrition information
            return new NDBFetcher().fetchMatches(mOneIngredient, mContext);
        }

        @Override
        protected void onPostExecute(final ArrayList<PossibleIngredientMatch> dbMatches) {
            IngredientSearch search = IngredientSearch.get(getActivity());
            search.getPossibleIngredientMatches();

            // create an AlertDialog builder for showing us the possible ingredient matches
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

            // set the title
            builder.setTitle(R.string.dialog_title);

            // ArrayLists to hold the ingredient names and NDB numbers
            ArrayList<String> itemNames = new ArrayList<>();
            ArrayList<String> itemNdb = new ArrayList<>();

            // arrays to hold the names and NDB numbers as we iterate through the list
            final String[] names = new String[dbMatches.size()];
            final String[] ndbnos = new String[dbMatches.size()];
            int i = 0;

            // for each item in dbMatches
            for (PossibleIngredientMatch item : dbMatches) {

                // add the name and NDB number to their ArrayList
                itemNames.add(item.getIngredientName());
                itemNdb.add(item.getIngredientNDB());

                // and set the name and NDB number of the arrays at index i to the same information
                names[i] = item.getIngredientName();
                ndbnos[i] = item.getIngredientNDB();
                i++;
            }

            // add the items to the Alert dialog builder
            builder.setItems(names, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    // set a working string to the index of names
                    String choice = names[i];

                    // set a working index variable to the same index number
                    int choiceIndex = i;

                    // set a working string to hold the NDB number at index choiceindex
                    String ndb = ndbnos[choiceIndex];

                    // append the ingredient name held in choice to the TextView
                    mIngredientsList.append(String.format(getResources().getString(R.string.alertdialog_choice), choice));

                    // add the NDB number of the chosen ingredient from the Alert Dialog to the ArrayList
                    // holding all the NDB numbers for our ingredients
                    mFinalIngredients.add(ndb);
                }
            });

            // call the builder create method
            AlertDialog dialog = builder.create();

            // show the dialog
            dialog.show();

        }
    }
}
