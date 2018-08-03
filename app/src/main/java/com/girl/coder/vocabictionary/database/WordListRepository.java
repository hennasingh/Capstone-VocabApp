package com.girl.coder.vocabictionary.database;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.girl.coder.vocabictionary.model.DisplayWordData;
import com.girl.coder.vocabictionary.model.Sense;

import java.util.List;

import javax.inject.Inject;

/**
 * Repository here will get data from the database and as well from the Web
 */
public class WordListRepository {

    private final WordListDao mWordListDao;

    private int returnedValue;

    @Inject
    public WordListRepository(WordListDao wordListDao) {
        mWordListDao = wordListDao;
    }

    public LiveData<List<WordList>> getWordListData() {
        return mWordListDao.getWordListItems();
    }


    public LiveData<WordList> getWordById(String wordId) {
        return mWordListDao.getWordItemById(wordId);
    }

    public List<WordList> getWidgetList() {
        return mWordListDao.getWidgetList();
    }

    public void deleteWordFromFavorite(final WordList word) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mWordListDao.deleteWordFromFav(word);
            }
        });
    }

    public int deleteListItem(DisplayWordData wordEntry) {

        final String word = wordEntry.getWord();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                returnedValue = mWordListDao.deleteWord(word);
            }
        });
        return returnedValue;
    }

    private WordList extractDbData(DisplayWordData wordEntry) {
        String word = wordEntry.getWord();
        String category = wordEntry.getCategory();
        String sentence;
        List<Sense> senses = wordEntry.getSenses();
        String definition = senses.get(0).getDefinitions().get(0);
        if (senses.get(0).getExamples() != null) {
            sentence = senses.get(0).getExamples().get(0).getSentence();
        } else {
            sentence = "No Sentence Found";
        }
        return new WordList(word, category, definition, sentence);

    }

    public void insertListItem(DisplayWordData wordEntry) {
        WordList mDbWordList = extractDbData(wordEntry);
        new insertAsyncTask(mWordListDao).execute(mDbWordList);

    }

    private static class insertAsyncTask extends AsyncTask<WordList, Void, Void> {

        private WordListDao mWordDao;

        insertAsyncTask(WordListDao wordListDao) {
            mWordDao = wordListDao;

        }

        @Override
        protected Void doInBackground(WordList... wordLists) {
            mWordDao.insertWord(wordLists[0]);
            return null;
        }

    }


}
