package com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewed;

public class MostViewedHelperClass {

    int imageViewMostViewed;
    String name;

    public int getImageView() {
        return imageViewMostViewed;
    }

    public String getTextView() {
        return name;
    }

    public MostViewedHelperClass(int image, String name) {
        this.imageViewMostViewed = image;
        this.name = name;
    }
}
