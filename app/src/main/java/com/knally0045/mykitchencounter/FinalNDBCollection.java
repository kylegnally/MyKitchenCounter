package com.knally0045.mykitchencounter;

import java.util.ArrayList;

/**
 * Created by kyleg on 12/15/2017.
 */

public class FinalNDBCollection {

    private ArrayList<String> mFinalNDBArraylist;
    private String mFinalNDBMember;

    public FinalNDBCollection() {

    }

    public ArrayList<String> getFinalNDBArraylist() {
        return mFinalNDBArraylist;
    }

    public void setFinalNDBArraylist(ArrayList<String> finalNDBArraylist) {
        mFinalNDBArraylist = finalNDBArraylist;
    }

    public String getFinalNDBMember() {
        return mFinalNDBMember;
    }

    public void setFinalNDBMember(String finalNDBMember) {
        mFinalNDBMember = finalNDBMember;
    }

    public ArrayList<String> addToArrayList(String number) {
        mFinalNDBArraylist.add(number);
        return mFinalNDBArraylist;
    }
}
