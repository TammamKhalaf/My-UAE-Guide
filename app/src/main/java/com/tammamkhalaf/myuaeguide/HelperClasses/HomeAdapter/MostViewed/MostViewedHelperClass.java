package com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewed;

public class MostViewedHelperClass {

    int imageViewMostViewed;
    String name;
    String description;

    public int getImageView() {
        return imageViewMostViewed;
    }

    public String getTextView() {
        return name;
    }

    public MostViewedHelperClass(int imageViewMostViewed, String name, String description) {
        this.imageViewMostViewed = imageViewMostViewed;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
