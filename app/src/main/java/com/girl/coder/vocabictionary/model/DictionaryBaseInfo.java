package com.girl.coder.vocabictionary.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DictionaryBaseInfo {

    @SerializedName("results")
    private List<Word> wordResults;

    public List<Word> getWordResults() {
        return wordResults;
    }

    public void setWordResults(List<Word> wordResults) {
        this.wordResults = wordResults;
    }
}
