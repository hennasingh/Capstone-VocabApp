package com.girl.coder.vocabictionary.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {WordList.class}, version = 1, exportSchema = false)
public abstract class WordListDatabase extends RoomDatabase {

    public abstract WordListDao WordListDao();
}
