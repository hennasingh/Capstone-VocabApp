package com.girl.coder.vocabictionary.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider.Factory;
import android.support.annotation.NonNull;

import com.girl.coder.vocabictionary.database.WordListRepository;
import com.girl.coder.vocabictionary.network.repository.WebRepository;

public class CustomViewModelFactory implements Factory {

    private final WordListRepository mWordListRepository;
    private final WebRepository mWebRepository;

    public CustomViewModelFactory(WordListRepository wordListRepository, WebRepository webRepository) {
        mWordListRepository = wordListRepository;
        mWebRepository = webRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DictionaryViewModel.class))
            return (T) new DictionaryViewModel(mWebRepository);
        else if (modelClass.isAssignableFrom(ThesaurusViewModel.class))
            return (T) new ThesaurusViewModel(mWebRepository);
        else if (modelClass.isAssignableFrom(FavoriteListViewModel.class))
            return (T) new FavoriteListViewModel(mWordListRepository);
        else {
            throw new IllegalArgumentException("ViewModel not Found " + modelClass);
        }
    }
}
