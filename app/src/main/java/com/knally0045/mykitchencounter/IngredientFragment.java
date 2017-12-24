package com.knally0045.mykitchencounter;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kyleg on 11/23/2017.
 */

public class IngredientFragment extends Fragment {

    private Button mAddIngredientButton;
    private FloatingActionButton mFloatingAddIngredientButton;
    private Button mGetNutritionButton;
    private EditText mIngredientString;

    private RecyclerView mIngredientsRecyclerView;
    private IngredientAdapter mIngredientAdapter;

    private RelativeLayout mNewIngredientLayout;
    private Context mContext;

    private ArrayList<String> mFinalIngredients;
    private IngredientNames mIngredientNames;
    private ArrayList<IngredientNutrition> mIngredientNutritions;
    private String mOneIngredient;
    private AlertDialog mWaitDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        mFinalIngredients = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    private class IngredientHolder extends RecyclerView.ViewHolder {
        private TextView mIngredientTextView;
        private NamesOfIngredients mNamesOfIngredients;

        public IngredientHolder(View itemView) {
            super(itemView);
            mIngredientTextView = (TextView) itemView.findViewById(R.id.list_item_single_ingredient);
            mIngredientTextView.setTypeface(ResourcesCompat.getFont(mContext, R.font.bradhitc));
            mIngredientTextView.setTextColor(Color.BLACK);
        }

        public void bindIngredient(NamesOfIngredients namesOfIngredients) {
            mNamesOfIngredients = namesOfIngredients;
            if (mNamesOfIngredients != null) {
                mIngredientTextView.setText(namesOfIngredients.getIngredientName());
            } else mIngredientTextView.setText(R.string.no_ingredients_found);
        }

    }

    private class IngredientAdapter extends RecyclerView.Adapter<IngredientHolder> {
        private ArrayList<NamesOfIngredients> mNamesOfIngredients;
        public IngredientAdapter(ArrayList<NamesOfIngredients> namesOfIngredients) {
            mNamesOfIngredients = namesOfIngredients;
        }

        @Override
        public IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_ingredient, parent, false);
            return new IngredientHolder(view);
        }

        @Override
        public void onBindViewHolder(IngredientHolder holder, int position) {
            NamesOfIngredients namesOfIngredients = mNamesOfIngredients.get(position);
            holder.bindIngredient(namesOfIngredients);
        }

        @Override
        public int getItemCount() {
            return mNamesOfIngredients.size();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.new_ingredient, container, false);

        mNewIngredientLayout = (RelativeLayout) v.findViewById(R.id.new_ingredient_layout);

        mFloatingAddIngredientButton = (FloatingActionButton) v.findViewById(R.id.floating_add_ingredient);
        mFloatingAddIngredientButton.setEnabled(false);
        mFloatingAddIngredientButton.setOnClickListener(new View.OnClickListener() {
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
        mGetNutritionButton.setEnabled(false);
        mGetNutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext = getActivity();
                RecipeNutrition nutrition = RecipeNutrition.get(getActivity());
                nutrition.setIngredientNutritions(mIngredientNutritions);

                // start a new Get Nutrition background task and run it
                new FetchNutritionTask().execute();
            }
        });

        mIngredientString = (EditText) v.findViewById(R.id.new_ingredient);
        mIngredientString.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mFloatingAddIngredientButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mIngredientsRecyclerView = (RecyclerView) v.findViewById(R.id.ingredient_recycler_view);
        mIngredientsRecyclerView.setNestedScrollingEnabled(false);
        mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return v;
    }

    private void updateUI() {

        IngredientNames ingredientNames = IngredientNames.get(getActivity());
        ArrayList<NamesOfIngredients> namesOfIngredients = ingredientNames.getIngredientNamesList();

        if (mIngredientAdapter == null) {
            mIngredientAdapter = new IngredientAdapter(namesOfIngredients);
            mIngredientsRecyclerView.setAdapter(mIngredientAdapter);
        } else {
            mIngredientAdapter.notifyDataSetChanged();
        }
    }

    private class FetchNutritionTask extends AsyncTask<Void,Void,ArrayList<IngredientNutrition>> {

        @Override
        protected void onPreExecute() {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(R.string.please_wait_string);
            AlertDialog waitDialog = builder.create();
            mWaitDialog = waitDialog;
            waitDialog.show();
        }

        @Override
        protected ArrayList<IngredientNutrition> doInBackground(Void... voids) {

            // get the actual nutrition information
            return new GetNutritionResults().fetchNutrition(mFinalIngredients, mContext);
        }

        @Override
        protected void onPostExecute(ArrayList<IngredientNutrition> nutritions) {
            mIngredientNutritions = nutritions;
            mWaitDialog.dismiss();
            Fragment nutritionFragment = new RecipeNutritionFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    nutritionFragment, "com.knally0045.mykitchencounter.nutritionFragment")
                    .addToBackStack(null)
                    .commit();
        }
    }

    private class FetchMatchesTask extends AsyncTask<Void,Void,ArrayList<PossibleIngredientMatch>> {
        @Override
        protected ArrayList<PossibleIngredientMatch> doInBackground(Void... voids) {

            // get the NDB numbers we will use to query the server for nutrition information
            return new NDBFetcher().fetchMatches(mOneIngredient, mContext);
        }

        @Override
        protected void onPostExecute(ArrayList<PossibleIngredientMatch> dbMatches) {
            IngredientSearch search = IngredientSearch.get(getActivity());
            final IngredientNames ingredientNames = IngredientNames.get(getActivity());
            search.getPossibleIngredientMatches();
            ingredientNames.getIngredientNamesList();

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
                    String name = names[choiceIndex];

                    // add the NDB number of the chosen ingredient from the Alert Dialog to the ArrayList
                    // holding all the NDB numbers for our ingredients
                    mFinalIngredients.add(ndb);
                    ingredientNames.addIngredientName(name);
                    mGetNutritionButton.setEnabled(true);
                    updateUI();
                }

            });

            // call the builder create method
            AlertDialog dialog = builder.create();

            // show the dialog
            dialog.show();

        }
    }
}
