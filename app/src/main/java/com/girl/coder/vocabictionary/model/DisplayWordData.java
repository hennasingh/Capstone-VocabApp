package com.girl.coder.vocabictionary.model;

import java.util.List;

public class DisplayWordData {

    private String mWord;
    private String mCategory;
    private String mPhonetics;
    private List<Sense> mSenses;


    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        mWord = word;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getPhonetics() {
        return mPhonetics;
    }

    public void setPhonetics(String phonetics) {
        mPhonetics = phonetics;
    }

    public List<Sense> getSenses() {
        return mSenses;
    }

    public void setSenses(List<Sense> senses) {
        mSenses = senses;
    }
}
