package com.girl.coder.vocabictionary.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "words")
public class WordList {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "word_id")
    private String wordId;

    private String category;

    private String definition;

    private String sentence;


    WordList(@NonNull String wordId, String category, String definition, String sentence) {
        this.wordId = wordId;
        this.category = category;
        this.definition = definition;
        this.sentence = sentence;
    }

    @NonNull
    public String getWordId() {
        return wordId;
    }

    public void setWordId(@NonNull String wordId) {
        this.wordId = wordId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
