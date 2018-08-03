package com.girl.coder.vocabictionary.view.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.girl.coder.vocabictionary.R;
import com.girl.coder.vocabictionary.view.fragments.DictionaryFragment;
import com.girl.coder.vocabictionary.view.fragments.ThesaurusFragment;
import com.girl.coder.vocabictionary.view.fragments.WordListFragment;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;
    private static final String DICT_FRAG = "dict_frag";

    @BindString(R.string.title_dictionary)
    String mDictionaryTitle;
    @BindString(R.string.title_thesaurus)
    String mThesaurusTitle;
    @BindString(R.string.title_favorites)
    String mFavoriteTitle;

    private ActionBar mActionBar;
    private static final String THES_FRAG = "thes_frag";
    private static final String FAV_FRAG = "fav_frag";
    private static final String SAVE_TAG = "save_tag";
    @BindView(R.id.flContent)
    FrameLayout mFrameLayout;
    FragmentManager mFragmentManager;
    Fragment mFragment;
    private String mTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        ButterKnife.bind(this);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        mActionBar = getSupportActionBar();

        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mTag = savedInstanceState.getString(SAVE_TAG);
            switch (mTag) {
                case DICT_FRAG:
                    mActionBar.setTitle(mDictionaryTitle);
                    mFragment = mFragmentManager.findFragmentByTag(mTag);
                    if (mFragment == null) {
                        mFragment = DictionaryFragment.newInstance();
                    }
                    break;
                case THES_FRAG:
                    mActionBar.setTitle(mThesaurusTitle);
                    mFragment = mFragmentManager.findFragmentByTag(mTag);
                    if (mFragment == null) {
                        mFragment = ThesaurusFragment.newInstance();
                    }
                    break;
                case FAV_FRAG:
                    mActionBar.setTitle(mFavoriteTitle);
                    mFragment = mFragmentManager.findFragmentByTag(mTag);
                    if (mFragment == null) {
                        mFragment = WordListFragment.newInstance();
                    }
                    break;
            }
            addFragment(mFragment, mTag);
        } else {
            //load the dictionary fragment by default
            mActionBar.setTitle(mDictionaryTitle);
            mFragment = mFragmentManager.findFragmentByTag(DICT_FRAG);
            mTag = DICT_FRAG;
            if (mFragment == null) {
                mFragment = DictionaryFragment.newInstance();
            }
            addFragment(mFragment, DICT_FRAG);
        }

    }

    private void addFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.flContent, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.nav_dictionary:
                mActionBar.setTitle(mDictionaryTitle);
                fragment = mFragmentManager.findFragmentByTag(DICT_FRAG);
                mTag = DICT_FRAG;
                if (fragment == null) {
                    fragment = DictionaryFragment.newInstance();
                }
                addFragment(fragment, mTag);
                return true;

            case R.id.nav_thesaurus:
                mActionBar.setTitle(mThesaurusTitle);
                fragment = mFragmentManager.findFragmentByTag(THES_FRAG);
                mTag = THES_FRAG;
                if (fragment == null) {
                    fragment = ThesaurusFragment.newInstance();
                }
                addFragment(fragment, mTag);
                return true;

            case R.id.nav_fav:
                mActionBar.setTitle(mFavoriteTitle);
                fragment = mFragmentManager.findFragmentByTag(FAV_FRAG);
                mTag = FAV_FRAG;
                if (fragment == null) {
                    fragment = WordListFragment.newInstance();
                }
                addFragment(fragment, mTag);
                return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_TAG, mTag);
    }
}
