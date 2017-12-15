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
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kyleg on 11/23/2017.
 */

public class IngredientFragment extends Fragment {

    private Button mAddIngredientButton;
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
                //IngredientSearch mIngredientSearch = IngredientSearch.get(mContext);
                new FetchMatchesTask().execute();
                mIngredientString.setText(null);
                view.requestFocus();
            }
        });

        mIngredientString = (EditText) v.findViewById(R.id.new_ingredient);
        mIngredientsList = (TextView) v.findViewById(R.id.ingredients_list);
        mIngredientsList.setText(R.string.ingredients_title);

        return v;
    }

    private class FetchMatchesTask extends AsyncTask<Void,Void,ArrayList<PossibleIngredientMatch>> {
        @Override
        protected ArrayList<PossibleIngredientMatch> doInBackground(Void... voids) {
            return new NDBFetcher().fetchMatches(mOneIngredient, mContext);
        }

        @Override
        protected void onPostExecute(final ArrayList<PossibleIngredientMatch> dbMatches) {
            IngredientSearch search = IngredientSearch.get(getActivity());
            search.getPossibleIngredientMatches();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(R.string.dialog_title);
            ArrayList<String> itemNames = new ArrayList<>();
            ArrayList<String> itemNdb = new ArrayList<>();

            final String[] names = new String[dbMatches.size()];
            final String[] ndbnos = new String[dbMatches.size()];
            int i = 0;
            for (PossibleIngredientMatch item : dbMatches) {
                itemNames.add(item.getIngredientName());
                itemNdb.add(item.getIngredientNDB());
                names[i] = item.getIngredientName();
                ndbnos[i] = item.getIngredientNDB();
                i++;
            }

            builder.setItems(names, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String choice = names[i];
                    int choiceIndex = i;
                    String ndb = ndbnos[choiceIndex];
                    mIngredientsList.append(String.format(getResources().getString(R.string.alertdialog_choice), choice));
                    mFinalIngredients.add(ndb);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }
}
