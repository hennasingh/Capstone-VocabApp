package com.girl.coder.vocabictionary.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.girl.coder.vocabictionary.MainApplication;
import com.girl.coder.vocabictionary.R;
import com.girl.coder.vocabictionary.database.AppExecutors;
import com.girl.coder.vocabictionary.database.WordList;
import com.girl.coder.vocabictionary.database.WordListRepository;

import java.util.List;

import javax.inject.Inject;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    @Inject
    WordListRepository mWordListRepository;

    private Context context;
    private List<WordList> mWordList;


    ListRemoteViewsFactory(Context context, Intent intent) {

        this.context = context;
    }

    @Override
    public void onCreate() {

        ((MainApplication) context.getApplicationContext())
                .getOxfordComponent()
                .inject(this);
    }

    @Override
    public void onDataSetChanged() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mWordList = mWordListRepository.getWidgetList();
            }
        });
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mWordList == null) return 0;
        return mWordList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mWordList == null) return null;

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        WordList word = mWordList.get(position);
        remoteViews.setTextViewText(R.id.tv_word, word.getWordId());
        remoteViews.setTextViewText(R.id.tv_category, word.getCategory());
        remoteViews.setTextViewText(R.id.tv_definition, word.getDefinition());
        remoteViews.setTextViewText(R.id.tv_sentence, word.getSentence());

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
