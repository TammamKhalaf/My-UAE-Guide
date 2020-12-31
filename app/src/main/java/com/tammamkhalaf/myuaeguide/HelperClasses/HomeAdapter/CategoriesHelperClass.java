package com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter;

import android.graphics.drawable.GradientDrawable;

public class CategoriesHelperClass {
    private GradientDrawable gradientDrawable;
    private int image;
    private String title;

    public GradientDrawable getGradient() {
        return gradientDrawable;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public CategoriesHelperClass(GradientDrawable gradientDrawable, int image, String title) {
        this.gradientDrawable = gradientDrawable;
        this.image = image;
        this.title = title;
    }
}
