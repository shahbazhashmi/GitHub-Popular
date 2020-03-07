package org.gojek.github.utils;

import android.view.View;

import androidx.databinding.BindingAdapter;

/**
 * Created by Shahbaz Hashmi on 2020-03-07.
 */
public class BindingUtil {

    private static final String VISIBILITY = "android:visibility";

    @BindingAdapter({VISIBILITY})
    public static void setVisibility(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }


}
