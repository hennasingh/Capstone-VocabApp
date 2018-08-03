package com.girl.coder.vocabictionary.dependencyinjection;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.girl.coder.vocabictionary.database.WordListDao;
import com.girl.coder.vocabictionary.database.WordListDatabase;
import com.girl.coder.vocabictionary.database.WordListRepository;
import com.girl.coder.vocabictionary.dependencyinjection.scope.UserScope;

import dagger.Module;
import dagger.Provides;

/**
 * Modules are responsible for creating/satisfying dependencies
 */
@Module
public class RoomModule {

    private final WordListDatabase mDatabase;
    private final String DB_NAME = "wordList.db";

    public RoomModule(Application application) {
        mDatabase = Room.databaseBuilder(
                application,
                WordListDatabase.class,
                DB_NAME
        ).build();
    }

    @Provides
    @UserScope
    WordListDao provideWordListDao() {
        return mDatabase.WordListDao();
    }

    @Provides
    @UserScope
    WordListRepository provideWordListRepository(WordListDao wordListDao) {
        return new WordListRepository(wordListDao);
    }

}
