package com.girl.coder.vocabictionary.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.girl.coder.vocabictionary.R;
import com.girl.coder.vocabictionary.database.WordList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<WordList> mWordLists;

    public FavoriteAdapter(List<WordList> wordLists) {
        mWordLists = wordLists;
    }

    public List<WordList> getWordLists() {
        return mWordLists;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fav_list_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        WordList displayWord = mWordLists.get(position);
        holder.mTvWord.setText(displayWord.getWordId());
        holder.mTvCategory.setText(displayWord.getCategory());
        holder.mTvDefinition.setText(displayWord.getDefinition());
        holder.mTvSentence.setText(displayWord.getSentence());

    }

    @Override
    public int getItemCount() {
        if (mWordLists == null) return 0;
        return mWordLists.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_word)
        TextView mTvWord;
        @BindView(R.id.tv_category)
        TextView mTvCategory;
        @BindView(R.id.tv_definition)
        TextView mTvDefinition;
        @BindView(R.id.tv_sentence)
        TextView mTvSentence;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
