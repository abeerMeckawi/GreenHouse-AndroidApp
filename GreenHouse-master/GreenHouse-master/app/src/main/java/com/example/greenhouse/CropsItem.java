package com.example.greenhouse;

public class CropsItem {
    private String mCropsName;
    private int mCropsImage;

    public CropsItem() {
    }

    public CropsItem(String mCropsName, int mCropsImage) {
        this.mCropsName = mCropsName;
        this.mCropsImage = mCropsImage;
    }

    public String getCropsName() {
        return mCropsName;
    }

    public void setCropsName(String mCropsName) {
        this.mCropsName = mCropsName;
    }

    public int getCropsImage() {
        return mCropsImage;
    }

    public void setCropsImage(int mCropsImage) {
        this.mCropsImage = mCropsImage;
    }
}
