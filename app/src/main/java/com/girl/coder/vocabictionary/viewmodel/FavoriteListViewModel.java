package com.girl.coder.vocabictionary.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.girl.coder.vocabictionary.database.WordList;
import com.girl.coder.vocabictionary.database.WordListRepository;
import com.girl.coder.vocabictionary.model.DisplayWordData;

import java.util.List;

public class FavoriteListViewModel extends ViewModel {

    private WordListRepository mFavoriteRepo;

    private MutableLiveData<String> wordSearched = new MutableLiveData<>();

    private LiveData<WordList> wordData = Transformations.switchMap(wordSearched,
            new Function<String, LiveData<WordList>>() {
        @Override
        public LiveData<WordList> apply(String input) {
            return mFavoriteRepo.getWordById(input);
        }
    });

    FavoriteListViewModel(WordListRepository favRepo) {
        mFavoriteRepo = favRepo;
    }

    public LiveData<List<WordList>> getWordListItems() {
        return mFavoriteRepo.getWordListData();
    }

    public LiveData<WordList> checkWordInDb(String word) {

        if (!word.equals(wordSearched.getValue())) {
            wordSearched.setValue(word);
        }
        return mFavoriteRepo.getWordById(word);
    }

    public LiveData<WordList> getFavLiveData() {

        return wordData;
    }

    public void insertDataToDatabase(DisplayWordData word) {
        mFavoriteRepo.insertListItem(word);
    }

    public int deleteDataFromDatabase(DisplayWordData word) {
        return mFavoriteRepo.deleteListItem(word);

    }

    public void deleteWordItemFromFavorite(WordList word) {
        mFavoriteRepo.deleteWordFromFavorite(word);
    }

    public void setWordId(String word) {
        wordSearched.setValue(word);
    }

    public String getWordId() {
        return wordSearched.getValue();
    }

}
