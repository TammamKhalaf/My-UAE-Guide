package com.tammamkhalaf.myuaeguide.HelperClasses;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.tammamkhalaf.myuaeguide.R;

public class SliderAdapter extends PagerAdapter {
    Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int images[] = {
            R.drawable.search_place,
            R.drawable.Make_a_call,
            R.drawable.add_missing_place,
            R.drawable.sit_back_and_relax
    };

    int headings[] = {
            R.string.first_slide_title,
            R.string.second_slide_title,
            R.string.third_slide_title,
            R.string.first_slide_title
    };

    int descriptions[] = {
            R.string.first_slide_desc,
            R.string.second_slide_desc,
            R.string.third_slide_desc,
            R.string.first_slide_desc
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull @org.jetbrains.annotations.NotNull View view, @NonNull @org.jetbrains.annotations.NotNull Object object) {
        return false;
    }
}
