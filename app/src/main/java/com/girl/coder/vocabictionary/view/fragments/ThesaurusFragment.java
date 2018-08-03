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

import com.girl.coder.vocabictionary.MainApplication;
import com.girl.coder.vocabictionary.R;
import com.girl.coder.vocabictionary.model.DisplayWordData;
import com.girl.coder.vocabictionary.view.adapters.ThesaurusAdapter;
import com.girl.coder.vocabictionary.view.listeners.ResultDisplay;
import com.girl.coder.vocabictionary.viewmodel.ThesaurusViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThesaurusFragment extends Fragment {


    private static final String TAG = ThesaurusFragment.class.getSimpleName();
    //Binding Strings
    @BindString(R.string.lang)
    String mLang;
    @BindString(R.string.empty_value)
    String mEmptyText;
    @BindString(R.string.no_search_text)
    String mNoSearchText;
    //Binding Views
    @BindView(R.id.et_search)
    EditText mETSearch;
    @BindView(R.id.rv_thesaurus_data)
    RecyclerView mThesaurusRv;
    @BindView(R.id.tv_message)
    TextView mTVMessage;
    @BindView(R.id.loadingBar)
    ProgressBar mLoadingBar;
    @Inject
    ViewModelProvider.Factory mFactory;
    private ThesaurusViewModel mThesaurusViewModel;
    private Unbinder mUnbinder;
    private ThesaurusAdapter mThesaurusAdapter;
    private Context mContext;


    public static ThesaurusFragment newInstance() {
        return new ThesaurusFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MainApplication) getActivity().getApplication())
                .getOxfordComponent()
                .inject(this);
        mThesaurusViewModel = ViewModelProviders.of(this, mFactory)
                .get(ThesaurusViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thesaurus, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        mContext = container.getContext();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLoadingBar.setVisibility(View.GONE);
        mThesaurusRv.setVisibility(View.GONE);

        mThesaurusViewModel.getWordListThesData().observe(this,
                new Observer<ResultDisplay<List<DisplayWordData>>>() {
            @Override
            public void onChanged(ResultDisplay<List<DisplayWordData>> listResultDisplay) {
                switch (listResultDisplay.state) {
                    case ResultDisplay.STATE_SUCCESS:
                        mLoadingBar.setVisibility(View.GONE);
                        mTVMessage.setVisibility(View.GONE);
                        mThesaurusRv.setVisibility(View.VISIBLE);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                        mThesaurusRv.setLayoutManager(layoutManager);
                        Log.d(TAG, " " + listResultDisplay.data);
                        mThesaurusAdapter = new ThesaurusAdapter(listResultDisplay.data);
                        mThesaurusRv.setAdapter(mThesaurusAdapter);
                        break;
                    case ResultDisplay.STATE_ERROR:
                        mLoadingBar.setVisibility(View.GONE);
                        mThesaurusRv.setVisibility(View.GONE);
                        mTVMessage.setVisibility(View.VISIBLE);
                        mTVMessage.setText(listResultDisplay.errorMessage);
                        break;
                    case ResultDisplay.STATE_LOADING:
                        mThesaurusRv.setVisibility(View.GONE);
                        mTVMessage.setVisibility(View.GONE);
                        mLoadingBar.setVisibility(View.VISIBLE);
                        break;
                    default:
                        mTVMessage.setVisibility(View.VISIBLE);
                        mTVMessage.setText(mNoSearchText);
                }
            }
        });

    }

    @OnClick(R.id.ib_search)
    public void searchWord() {
        String word = mETSearch.getText().toString();
        if (validateInput(word)) {

            word = word.trim().toLowerCase();
            mThesaurusViewModel.getWordResults(mLang, word);
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
}
