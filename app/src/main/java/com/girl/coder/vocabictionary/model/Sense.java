package com.girl.coder.vocabictionary.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sense {

    @SerializedName("antonyms")
    private List<Antonyms> mAntonyms;

    @SerializedName("synonyms")
    private List<Synonyms> mSynonyms;

    @SerializedName("definitions")
    private List<String> mDefinitions;

    @SerializedName("examples")
    private List<Example> mExamples;


    public List<String> getDefinitions() {
        return mDefinitions;
    }

    public void setDefinitions(List<String> definitions) {
        mDefinitions = definitions;
    }

    public List<Example> getExamples() {
        return mExamples;
    }

    public void setExamples(List<Example> examples) {
        mExamples = examples;
    }

    public List<Antonyms> getAntonyms() {
        return mAntonyms;
    }

    public void setAntonyms(List<Antonyms> antonyms) {
        mAntonyms = antonyms;
    }

    public List<Synonyms> getSynonyms() {
        return mSynonyms;
    }

    public void setSynonyms(List<Synonyms> synonyms) {
        mSynonyms = synonyms;
    }
}
