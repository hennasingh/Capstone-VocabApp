package com.girl.coder.vocabictionary.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Word {

    @SerializedName("word")
    private String mWord;

    @SerializedName("lexicalEntries")
    private List<LexicalEntry> mLexicalEntries;

    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        mWord = word;
    }

    public List<LexicalEntry> getLexicalEntries() {
        return mLexicalEntries;
    }

    public void setLexicalEntries(List<LexicalEntry> lexicalEntries) {
        mLexicalEntries = lexicalEntries;
    }
}
