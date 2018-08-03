package com.girl.coder.vocabictionary.model;

import com.google.gson.annotations.SerializedName;

public class Antonyms {

    @SerializedName("text")
    private String mText;

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }
}
