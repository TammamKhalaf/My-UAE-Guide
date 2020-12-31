package com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter;

public class MostViewedHelperClass {

    int imageViewMostViewed;
    String s;

    public int getImageView() {
        return imageViewMostViewed;
    }

    public String getTextView() {
        return s;
    }

    public MostViewedHelperClass(int image, String s) {
        this.imageViewMostViewed = image;
        this.s = s;
    }
}
