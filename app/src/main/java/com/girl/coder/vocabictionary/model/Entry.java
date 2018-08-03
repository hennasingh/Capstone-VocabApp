package com.girl.coder.vocabictionary.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Entry {

    @SerializedName("senses")
    private List<Sense> mSenses;

    public List<Sense> getSenses() {
        return mSenses;
    }

    public void setSenses(List<Sense> senses) {
        mSenses = senses;
    }
}
