package com.example.greenhouse;

public class HelpData {
        private String mMainTitle;
        private int mSubTitle;
        private int mImageResourceHelpId;

    public HelpData(String mMainTitle, int mSubTitle, int mImageResourceHelpId) {
        this.mMainTitle = mMainTitle;
        this.mSubTitle = mSubTitle;
        this.mImageResourceHelpId = mImageResourceHelpId;
    }

    public String getMainTitle() {
        return mMainTitle;
    }

    public void setMainTitle(String mMainTitle) {
        this.mMainTitle = mMainTitle;
    }

    public int getSubTitle() {
        return mSubTitle;
    }

    public void setSubTitle(int mSubTitle) {
        this.mSubTitle = mSubTitle;
    }

    public int getImageResourceHelpId() {
        return mImageResourceHelpId;
    }

    public void setImageResourceHelpId(int mImageResourceHelpId) {
        this.mImageResourceHelpId = mImageResourceHelpId;
    }
}
