package com.girl.coder.vocabictionary.view.adapters;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binaryfork.spanny.Spanny;
import com.girl.coder.vocabictionary.R;
import com.girl.coder.vocabictionary.model.Antonyms;
import com.girl.coder.vocabictionary.model.DisplayWordData;
import com.girl.coder.vocabictionary.model.Example;
import com.girl.coder.vocabictionary.model.Sense;
import com.girl.coder.vocabictionary.model.Synonyms;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ThesaurusAdapter extends RecyclerView.Adapter<ThesaurusAdapter.ThesaurusViewHolder> {

    private static final String TAG = ThesaurusAdapter.class.getSimpleName();
    private List<DisplayWordData> mWordDetails;

    public ThesaurusAdapter(List<DisplayWordData> wordDetails) {
        mWordDetails = wordDetails;
    }

    @NonNull
    @Override
    public ThesaurusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.thesaurus_list_item, parent, false);
        return new ThesaurusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThesaurusViewHolder thesHolder, int position) {

        List<Sense> senseList;
        List<String> definition;
        List<Example> exList;
        List<Antonyms> antList;
        List<Synonyms> synList;

        Spanny spanText = new Spanny("");
        DisplayWordData wordThesData = mWordDetails.get(position);
        thesHolder.mTvWord.setText(wordThesData.getWord());
        thesHolder.mTvCategory.setText(wordThesData.getCategory());

        senseList = wordThesData.getSenses();
        for (int i = 0; i < senseList.size(); i++) {
            Sense senseData = senseList.get(i);
            exList = senseData.getExamples();
            antList = senseData.getAntonyms();
            synList = senseData.getSynonyms();
            if (exList != null) {
                spanText = new Spanny(i + 1 + " ");
                for (int j = 0; j < exList.size(); j++) {
                    spanText.append(exList.get(j).getSentence())
                            .append("\n\n");
                }
            }
            if (synList != null) {
                spanText.append(thesHolder.mSynonymLabel, new StyleSpan(Typeface.BOLD))
                        .append("- ");
                for (int k = 0; k < synList.size(); k++) {
                    Synonyms synonyms = synList.get(k);
                    if (k == synList.size() - 1) {
                        spanText.append(synonyms.getText(), new StyleSpan(Typeface.ITALIC),
                                new ForegroundColorSpan(thesHolder.orange))
                                .append("\n\n");
                    } else {
                        spanText.append(synonyms.getText(), new StyleSpan(Typeface.ITALIC),
                                new ForegroundColorSpan(thesHolder.orange))
                                .append(", ");
                    }
                }
            } else {
                spanText.append(thesHolder.mSynonymLabel, new StyleSpan(Typeface.BOLD))
                        .append("- ")
                        .append(thesHolder.mNoSynonyms);
            }

            if (antList != null) {
                spanText.append(thesHolder.mAntonymLabel, new StyleSpan(Typeface.BOLD))
                        .append("- ");
                for (int an = 0; an < antList.size(); an++) {
                    Antonyms antonyms = antList.get(an);
                    if (an == antList.size() - 1) {
                        spanText.append(antonyms.getText(), new StyleSpan(Typeface.ITALIC),
                                new ForegroundColorSpan(thesHolder.ilac))
                                .append("\n\n");
                    } else {
                        spanText.append(antonyms.getText(), new StyleSpan(Typeface.ITALIC),
                                new ForegroundColorSpan(thesHolder.ilac))
                                .append(", ");
                    }
                }
            } else {
                spanText.append(thesHolder.mAntonymLabel, new StyleSpan(Typeface.BOLD))
                        .append("- ")
                        .append(thesHolder.mNoAntonymns)
                        .append("\n\n");
            }
            Log.d(TAG, spanText.toString());
            thesHolder.mTvWordDetails.append(spanText);
        }
    }

    @Override
    public int getItemCount() {
        if (mWordDetails == null) {
            return 0;
        }
        return mWordDetails.size();
    }

    class ThesaurusViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_word)
        TextView mTvWord;
        @BindView(R.id.tv_category)
        TextView mTvCategory;
        @BindView(R.id.tv_wordDetails)
        TextView mTvWordDetails;

        //Resource Bindings
        @BindColor(R.color.orange)
        int orange;
        @BindColor(R.color.ilac)
        int ilac;
        @BindString(R.string.no_antonyms)
        String mNoAntonymns;
        @BindString(R.string.no_synonyms)
        String mNoSynonyms;
        @BindString(R.string.synonyms_label)
        String mSynonymLabel;
        @BindString(R.string.antonyms_label)
        String mAntonymLabel;

        ThesaurusViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
