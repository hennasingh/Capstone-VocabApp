package com.girl.coder.vocabictionary.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.girl.coder.vocabictionary.model.DisplayWordData;
import com.girl.coder.vocabictionary.network.repository.WebRepository;
import com.girl.coder.vocabictionary.view.listeners.ResultDisplay;

import java.util.List;

public class DictionaryViewModel extends ViewModel {

    private WebRepository mWebRepo;


    DictionaryViewModel(WebRepository webRepo) {

        mWebRepo = webRepo;
    }

    public void getWordResults(String lang, String word) {

            mWebRepo.getDictionaryResult(lang, word);

    }

    public LiveData<ResultDisplay<List<DisplayWordData>>> getWordListDictData() {
        return mWebRepo.getWordListDictObservable();
    }

}
