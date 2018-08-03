package com.girl.coder.vocabictionary.network.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.girl.coder.vocabictionary.model.DictionaryBaseInfo;
import com.girl.coder.vocabictionary.model.DisplayWordData;
import com.girl.coder.vocabictionary.model.Entry;
import com.girl.coder.vocabictionary.model.LexicalEntry;
import com.girl.coder.vocabictionary.model.Pronunciation;
import com.girl.coder.vocabictionary.model.Sense;
import com.girl.coder.vocabictionary.model.Word;
import com.girl.coder.vocabictionary.network.ApiManager;
import com.girl.coder.vocabictionary.view.listeners.ResultDisplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class WebRepository {

    private static final String TAG = WebRepository.class.getSimpleName();

    private ApiManager mApiManager;

    private MutableLiveData<ResultDisplay<List<DisplayWordData>>> wordListDictObservable = new MutableLiveData<>();

    private MutableLiveData<ResultDisplay<List<DisplayWordData>>> wordListThesObservable = new MutableLiveData<>();

    @Inject
    public WebRepository(ApiManager apiManager) {
        mApiManager = apiManager;
    }

    //Dictionary Query
    public void getDictionaryResult(final String lang, final String word) {

        wordListDictObservable.setValue(ResultDisplay.loading(Collections.<DisplayWordData>emptyList()));

        mApiManager.getDictionaryResult(lang, word, new Callback<DictionaryBaseInfo>() {
            @Override
            public void onResponse(Call<DictionaryBaseInfo> call, Response<DictionaryBaseInfo> response) {

                if (response.isSuccessful()) {
                    List<Word> wordResults = response.body().getWordResults();
                    Log.d(TAG, wordResults.toString());
                    if (wordResults.size() > 0) {
                        List<LexicalEntry> lexicalEntries = new ArrayList<>();

                        for (Word word : wordResults) {
                            lexicalEntries.addAll(word.getLexicalEntries());
                            Log.d(TAG, lexicalEntries.toString());
                        }
                        List<Entry> entries;
                        List<Pronunciation> pronunciations;
                        List<DisplayWordData> displayDataList = new ArrayList<>();
                        List<Sense> senses;

                        String category, word;
                        if (lexicalEntries.size() > 0) {

                            for (LexicalEntry lexiEntry : lexicalEntries) {
                                entries = lexiEntry.getEntries();
                                pronunciations = lexiEntry.getPronunciations();
                                category = lexiEntry.getLexicalCategory();
                                word = lexiEntry.getWord();


                                if (entries.size() > 0) {
                                    for (Entry entryData : entries) {
                                        senses = entryData.getSenses();
                                        if (senses.size() > 0) {
                                            DisplayWordData singleData = new DisplayWordData();
                                            singleData.setSenses(senses);
                                            singleData.setCategory(category);
                                            singleData.setWord(word);
                                            if(pronunciations!=null) {
                                                singleData.setPhonetics(pronunciations.get(0).getPhoneticSpelling());
                                            }else{
                                                singleData.setPhonetics("");
                                            }
                                            displayDataList.add(singleData);
                                            Log.d(TAG, "List of Entries" + displayDataList);
                                        }
                                    }
                                }
                            }
                        }
                        wordListDictObservable.setValue(ResultDisplay.success(displayDataList));
                    }
                } else {
                    String errorM;
                    if (response.code() == 404) {
                        errorM = "Word Not found";
                    } else if (response.code() == 500) {
                        errorM = "Internal Error. An Error occurred while processing the data, please try again later";
                    } else {
                        errorM = "The error is " + " " + response.code() + " " + response.message();
                    }
                    wordListDictObservable.setValue(ResultDisplay.error(errorM, Collections.<DisplayWordData>emptyList()));
                }
            }

            @Override
            public void onFailure(Call<DictionaryBaseInfo> call, Throwable throwable) {
                String errorM;
                if (throwable instanceof HttpException) {
                    errorM = "There is a server Error, please try again";
                } else if (throwable instanceof IOException) {
                    errorM = "No internet connectivity";
                } else {
                    errorM = throwable.getMessage();
                }
                wordListDictObservable.setValue(ResultDisplay.error(errorM, Collections.<DisplayWordData>emptyList()));
            }
        });
    }

    //Thesaurus Query
    public void getThesaurusResult(String lang, String word) {

        wordListThesObservable.setValue(ResultDisplay.loading(Collections.<DisplayWordData>emptyList()));

        mApiManager.getThesaurusResult(lang, word, new Callback<DictionaryBaseInfo>() {
            @Override
            public void onResponse(Call<DictionaryBaseInfo> call, Response<DictionaryBaseInfo> response) {
                if (response.isSuccessful()) {
                    List<Word> wordResults = response.body().getWordResults();
                    Log.d(TAG, wordResults.toString());
                    if (wordResults.size() > 0) {
                        List<LexicalEntry> lexicalEntries = new ArrayList<>();

                        for (Word word : wordResults) {
                            lexicalEntries.addAll(word.getLexicalEntries());
                            Log.d(TAG, lexicalEntries.toString());
                        }
                        List<Entry> entries;
                        List<DisplayWordData> displayDataList = new ArrayList<>();
                        List<Sense> senses;

                        String category, word;
                        if (lexicalEntries.size() > 0) {

                            for (LexicalEntry lexiEntry : lexicalEntries) {
                                entries = lexiEntry.getEntries();
                                category = lexiEntry.getLexicalCategory();
                                word = lexiEntry.getWord();

                                if (entries.size() > 0) {
                                    for (Entry entryData : entries) {
                                        senses = entryData.getSenses();
                                        if (senses.size() > 0) {
                                            DisplayWordData singleData = new DisplayWordData();
                                            singleData.setSenses(senses);
                                            singleData.setCategory(category);
                                            singleData.setWord(word);
                                            displayDataList.add(singleData);
                                            Log.d(TAG, "List of Entries" + displayDataList);
                                        }
                                    }
                                }
                            }
                        }
                        wordListThesObservable.setValue(ResultDisplay.success(displayDataList));
                    }
                } else {
                    String errorM = "The error is- ";
                    errorM = errorM + response.code() + " " + response.message();
                    wordListThesObservable.setValue(ResultDisplay.error(errorM, Collections.<DisplayWordData>emptyList()));
                }
            }

            @Override
            public void onFailure(Call<DictionaryBaseInfo> call, Throwable t) {
                String errorM = "The error is- ";
                errorM = errorM + t.getMessage();
                wordListThesObservable.setValue(ResultDisplay.error(errorM, Collections.<DisplayWordData>emptyList()));
            }
        });
    }

    public MutableLiveData<ResultDisplay<List<DisplayWordData>>> getWordListDictObservable() {
        return wordListDictObservable;
    }

    public MutableLiveData<ResultDisplay<List<DisplayWordData>>> getWordListThesObservable() {
        return wordListThesObservable;
    }

}
