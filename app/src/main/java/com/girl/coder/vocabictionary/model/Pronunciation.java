package com.girl.coder.vocabictionary.model;

import com.google.gson.annotations.SerializedName;

public class Pronunciation {

    @SerializedName("audioFile")
    private String mAudioLink;

    @SerializedName("phoneticSpelling")
    private String mPhoneticSpelling;

    public String getAudioLink() {
        return mAudioLink;
    }

    public void setAudioLink(String audioLink) {
        mAudioLink = audioLink;
    }

    public String getPhoneticSpelling() {
        return mPhoneticSpelling;
    }

    public void setPhoneticSpelling(String phoneticSpelling) {
        mPhoneticSpelling = phoneticSpelling;
    }
}
