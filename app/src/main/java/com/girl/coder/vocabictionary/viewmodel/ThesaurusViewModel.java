package com.girl.coder.vocabictionary.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.girl.coder.vocabictionary.model.DisplayWordData;
import com.girl.coder.vocabictionary.network.repository.WebRepository;
import com.girl.coder.vocabictionary.view.listeners.ResultDisplay;

import java.util.List;

import javax.inject.Inject;

public class ThesaurusViewModel extends ViewModel {

    private WebRepository mWebRepo;


    @Inject
    ThesaurusViewModel(WebRepository webRepo) {
        mWebRepo = webRepo;
    }

    public void getWordResults(String lang, String word) {
        mWebRepo.getThesaurusResult(lang, word);
    }


    public LiveData<ResultDisplay<List<DisplayWordData>>> getWordListThesData() {
        return mWebRepo.getWordListThesObservable();
    }

}
