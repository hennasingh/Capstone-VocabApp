package com.girl.coder.vocabictionary.model;

import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("text")
    private String mSentence;

    public String getSentence() {
        return mSentence;
    }

    public void setSentence(String sentences) {
        mSentence = sentences;
    }
}
