package com.girl.coder.vocabictionary.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WordListDao {

    @Query("SELECT * FROM words ORDER BY word_id ASC")
    LiveData<List<WordList>> getWordListItems();

    @Insert
    void insertWord(WordList wordEntry);

    @Query("DELETE FROM words WHERE word_id= :wordId")
    int deleteWord(String wordId);

    @Query("SELECT * FROM WORDS WHERE word_id = :wordId")
    LiveData<WordList> getWordItemById(String wordId);

    @Delete
    void deleteWordFromFav(WordList wordList);

    @Query("SELECT * FROM words ORDER BY word_id ASC")
    List<WordList> getWidgetList();

}
