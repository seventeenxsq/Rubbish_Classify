package com.example.two_dimentioncodedemo.Adaper;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

public class pageAdapter extends PagerAdapter {

    private List<View> mViews;

    public pageAdapter(List<View> mViews) {
        this.mViews = mViews;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return false;
    }
}
