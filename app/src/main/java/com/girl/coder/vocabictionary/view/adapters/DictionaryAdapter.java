package com.girl.coder.vocabictionary.view.adapters;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.binaryfork.spanny.Spanny;
import com.girl.coder.vocabictionary.R;
import com.girl.coder.vocabictionary.model.DisplayWordData;
import com.girl.coder.vocabictionary.model.Example;
import com.girl.coder.vocabictionary.model.Sense;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/*
src- https://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView
https://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-type
spanny lib- https://github.com/binaryfork/Spanny
 */
public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryAdapterViewHolder> {


    private List<DisplayWordData> mWordDetails;
    private boolean isFavorite;


    private WordAsFavoriteListener mWordAsFavoriteListener;

    public DictionaryAdapter(List<DisplayWordData> wordDetails, WordAsFavoriteListener listener, boolean isFav) {
        mWordDetails = wordDetails;
        mWordAsFavoriteListener = listener;
        isFavorite = isFav;

    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
        notifyDataSetChanged();
    }

    public void setData(List<DisplayWordData> data) {
        mWordDetails = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryAdapterViewHolder dictHolder, int position) {

        List<Sense> senseList;
        List<String> definition;
        List<Example> exList;

        Spanny spanText = new Spanny("");


                final DisplayWordData wordDictData = mWordDetails.get(position);
                if (position == 0) {
                    dictHolder.mIbFavorite.setVisibility(View.VISIBLE);
                    if (isFavorite) {
                        dictHolder.mIbFavorite.setImageResource(R.drawable.ic_favorite_filled);
                    } else {
                        dictHolder.mIbFavorite.setImageResource(R.drawable.ic_favorite_border);
                    }
                } else {
                    dictHolder.mIbFavorite.setVisibility(View.GONE);
                }

                dictHolder.mTvWord.setText(wordDictData.getWord());
                dictHolder.mTvCategory.setText(wordDictData.getCategory());
                dictHolder.mTvPhonetic.setText(wordDictData.getPhonetics());

                senseList = wordDictData.getSenses();
                for (int i = 0; i < senseList.size(); i++) {
                    Sense senseData = senseList.get(i);
                    definition = senseData.getDefinitions();
                    if (definition != null) {
                        spanText = new Spanny(i + 1 + ". ");
                        for (int j = 0; j < definition.size(); j++) {
                            spanText.append(definition.get(j))
                                    .append("\n\n");
                        }
                    }
                    exList = senseData.getExamples();
                    if (exList != null) {
                        for (int k = 0; k < exList.size(); k++) {
                            Example egText = exList.get(k);
                            spanText.append(egText.getSentence(), new StyleSpan(Typeface.ITALIC),
                                    new ForegroundColorSpan(dictHolder.orange))
                                    .append("\n\n");
                        }
                    }
                    dictHolder.mTvDefinition.append(spanText);
                }
    }

    @NonNull
    @Override
    public DictionaryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.word_dictionary_item, parent, false);
        return new DictionaryAdapterViewHolder(view, mWordAsFavoriteListener);
    }

    public interface WordAsFavoriteListener {
        void onWordClick(boolean isClicked, DisplayWordData data);
    }

    @Override
    public int getItemCount() {
        if (mWordDetails == null) {
            return 0;
        }
        return mWordDetails.size();
    }

    class DictionaryAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_word)
        TextView mTvWord;
        @BindView(R.id.tv_category)
        TextView mTvCategory;
        @BindView(R.id.tv_phonetic)
        TextView mTvPhonetic;
        @BindView(R.id.tv_definition)
        TextView mTvDefinition;
        @BindView(R.id.ib_fav)
        ImageButton mIbFavorite;

        @BindColor(R.color.orange)
        int orange;

        WordAsFavoriteListener mListener;

        DictionaryAdapterViewHolder(View itemView, WordAsFavoriteListener favoriteListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mListener = favoriteListener;
            mIbFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isFavorite) {
                        mListener.onWordClick(false, mWordDetails.get(0));
                        isFavorite = false;
                        notifyDataSetChanged();
                    } else {
                        mListener.onWordClick(true, mWordDetails.get(0));
                        isFavorite = true;
                        notifyDataSetChanged();
                    }
                }
            });
        }

    }


}
