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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.girl.coder.vocabictionary.MainApplication;
import com.girl.coder.vocabictionary.R;
import com.girl.coder.vocabictionary.database.WordList;
import com.girl.coder.vocabictionary.view.adapters.FavoriteAdapter;
import com.girl.coder.vocabictionary.viewmodel.FavoriteListViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordListFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    FavoriteListViewModel mFavoriteListViewModel;
    private List<WordList> mWordListData;

    @BindString(R.string.no_fav_text)
    String mNoFavoriteYet;
    @BindString(R.string.word_removed_fav)
    String mWordRemoved;
    @BindView(R.id.rv_favList)
    RecyclerView mFavoriteRv;
    @BindView(R.id.tv_message)
    TextView mErrorMessage;
    private FavoriteAdapter mFavoriteAdapter;
    private Unbinder mUnbinder;

    private Context mContext;

    public WordListFragment() {
        // Required empty public constructor
    }

    public static WordListFragment newInstance() {
        return new WordListFragment();
    }

    /*------------------------Lifecycle-------------------*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApplication) getActivity().getApplication())
                .getOxfordComponent()
                .inject(this);

        mFavoriteListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(FavoriteListViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        mContext = container.getContext();

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFavoriteRv.setVisibility(View.GONE);
        mErrorMessage.setVisibility(View.VISIBLE);

        mFavoriteListViewModel.getWordListItems().observe(this, new Observer<List<WordList>>() {
            @Override
            public void onChanged(@Nullable List<WordList> wordLists) {
                if (wordLists == null) {
                    mFavoriteRv.setVisibility(View.GONE);
                    mErrorMessage.setVisibility(View.VISIBLE);

                } else {
                    mErrorMessage.setVisibility(View.GONE);
                    mFavoriteRv.setVisibility(View.VISIBLE);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                    mFavoriteRv.setLayoutManager(layoutManager);
                    mFavoriteAdapter = new FavoriteAdapter(wordLists);
                    mFavoriteRv.setAdapter(mFavoriteAdapter);
                }
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            //Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                List<WordList> mList = mFavoriteAdapter.getWordLists();
                mFavoriteListViewModel.deleteWordItemFromFavorite(mList.get(position));
                Toast.makeText(getContext(), mWordRemoved, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mFavoriteRv);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
