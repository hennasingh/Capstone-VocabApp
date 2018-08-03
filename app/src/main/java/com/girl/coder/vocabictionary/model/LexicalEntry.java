package com.girl.coder.vocabictionary.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LexicalEntry {

    @SerializedName("entries")
    private List<Entry> mEntries;

    @SerializedName("lexicalCategory")
    private String lexicalCategory;

    @SerializedName("pronunciations")
    private List<Pronunciation> mPronunciations;

    @SerializedName("text")
    private String mWord;


    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        mWord = word;
    }

    public List<Entry> getEntries() {
        return mEntries;
    }

    public void setEntries(List<Entry> entries) {
        mEntries = entries;
    }

    public String getLexicalCategory() {
        return lexicalCategory;
    }

    public void setLexicalCategory(String lexicalCategory) {
        this.lexicalCategory = lexicalCategory;
    }

    public List<Pronunciation> getPronunciations() {
        return mPronunciations;
    }

    public void setPronunciations(List<Pronunciation> pronunciations) {
        mPronunciations = pronunciations;
    }
}
