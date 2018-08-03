package com.girl.coder.vocabictionary.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.girl.coder.vocabictionary.MainApplication;
import com.girl.coder.vocabictionary.R;
import com.girl.coder.vocabictionary.database.WordList;
import com.girl.coder.vocabictionary.model.DisplayWordData;
import com.girl.coder.vocabictionary.view.adapters.DictionaryAdapter;
import com.girl.coder.vocabictionary.view.listeners.ResultDisplay;
import com.girl.coder.vocabictionary.viewmodel.DictionaryViewModel;
import com.girl.coder.vocabictionary.viewmodel.FavoriteListViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class DictionaryFragment extends Fragment implements DictionaryAdapter.WordAsFavoriteListener {

    private DictionaryViewModel mDictionaryViewModel;
    private FavoriteListViewModel mFavoriteListViewModel;


    private static final String TAG = DictionaryFragment.class.getSimpleName();
    private static final String IS_FAV = "is_fav";


    //Binding Strings
    @BindString(R.string.lang)
    String mLang;
    @BindString(R.string.empty_value)
    String mEmptyText;
    @BindString(R.string.no_search_text)
    String mNoSearchText;
    @BindString(R.string.word_removed_fav)
    String mWordRemovedFromFav;
    @BindString(R.string.word_added_fav)
    String mWordAddedToFav;

    //Binding Views
    @BindView(R.id.et_search)
    EditText mETSearch;
    @BindView(R.id.rv_dictionary_data)
    RecyclerView mDictionaryRv;
    @BindView(R.id.tv_message)
    TextView mTVMessage;
    @BindView(R.id.loadingBar)
    ProgressBar mLoadingBar;


    private Unbinder mUnbinder;

    private DictionaryAdapter mDictionaryAdapter;

    private Context mContext;

    private boolean isFav;

    private String wordSearched;

    @Inject
    ViewModelProvider.Factory mFactory;


    public static DictionaryFragment newInstance() {
        return new DictionaryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MainApplication) getActivity().getApplication())
                .getOxfordComponent()
                .inject(this);
        mDictionaryViewModel = ViewModelProviders.of(this, mFactory)
                .get(DictionaryViewModel.class);
        mFavoriteListViewModel = ViewModelProviders.of(this, mFactory)
                .get(FavoriteListViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dictionary, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mContext = container.getContext();

        if(savedInstanceState!=null){
            isFav = savedInstanceState.getBoolean(IS_FAV);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLoadingBar.setVisibility(View.GONE);
        mDictionaryRv.setVisibility(View.GONE);

        mDictionaryViewModel.getWordListDictData().observe(this,
                new Observer<ResultDisplay<List<DisplayWordData>>>() {
            @Override
            public void onChanged(ResultDisplay<List<DisplayWordData>> listResultDisplay) {
                switch (listResultDisplay.state) {
                    case ResultDisplay.STATE_SUCCESS:
                        mLoadingBar.setVisibility(View.GONE);
                        mTVMessage.setVisibility(View.GONE);
                        mDictionaryRv.setVisibility(View.VISIBLE);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                        mDictionaryRv.setLayoutManager(layoutManager);
                        Log.d(TAG, " " + listResultDisplay.data);
                        isFav(listResultDisplay.data.get(0).getWord());
                        mDictionaryAdapter = new DictionaryAdapter(listResultDisplay.data, DictionaryFragment.this, isFav);
                        mDictionaryRv.setAdapter(mDictionaryAdapter);

                        break;
                    case ResultDisplay.STATE_ERROR:
                        mLoadingBar.setVisibility(View.GONE);
                        mDictionaryRv.setVisibility(View.GONE);
                        mTVMessage.setVisibility(View.VISIBLE);
                        mTVMessage.setText(listResultDisplay.errorMessage);
                        break;
                    case ResultDisplay.STATE_LOADING:
                        mDictionaryRv.setVisibility(View.GONE);
                        mTVMessage.setVisibility(View.GONE);
                        mLoadingBar.setVisibility(View.VISIBLE);
                        break;
                    default:
                        mLoadingBar.setVisibility(View.GONE);
                        mTVMessage.setVisibility(View.VISIBLE);
                        mTVMessage.setText(mNoSearchText);
                }
                //mDictionaryViewModel.getWordListDictData().removeObserver(this);
            }
        });
    }

    private void isFav(final String wordSearched) {

        mFavoriteListViewModel.checkWordInDb(wordSearched).observe(DictionaryFragment.this,
                new Observer<WordList>() {
                    @Override
                    public void onChanged(@Nullable WordList wordList) {
                        isFav = wordList != null;
                        //mFavoriteListViewModel.checkWordInDb(wordSearched).removeObserver(this);
                    }
                });
    }

    @OnClick(R.id.ib_search)
    public void searchWord() {
        wordSearched = mETSearch.getText().toString();

        if (validateInput(wordSearched)) {
            wordSearched = wordSearched.trim().toLowerCase();

            mFavoriteListViewModel.setWordId(wordSearched);

            isFav(wordSearched);
            mDictionaryViewModel.getWordResults(mLang, wordSearched);
            mETSearch.clearFocus();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mFavoriteListViewModel.getWordId()!=null){
            isFav(mFavoriteListViewModel.getWordId());
        }
    }

    private boolean validateInput(String word) {

        if (TextUtils.isEmpty(word)) {
            mETSearch.setBackgroundResource(R.color.colorError);
            mETSearch.setError(mEmptyText);
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onWordClick(boolean isClicked, DisplayWordData wordData) {
        if (isClicked) {
            mFavoriteListViewModel.insertDataToDatabase(wordData);
            Toast.makeText(getActivity(), mWordAddedToFav, Toast.LENGTH_SHORT).show();
            isFav = true;

        } else {
            int received = mFavoriteListViewModel.deleteDataFromDatabase(wordData);
            if (received > 0) {
                Toast.makeText(getActivity(), mWordRemovedFromFav, Toast.LENGTH_SHORT).show();
                isFav = false;
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_FAV,isFav);
    }
}
