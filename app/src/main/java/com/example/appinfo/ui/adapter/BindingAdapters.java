package com.example.appinfo.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {

    @BindingAdapter("android:bitmap_drawable")
    public static void setBitmapDrawable(ImageView imageView, byte[] iconBitmapBytes) {
        Bitmap bitmapIcon = BitmapFactory.decodeByteArray(iconBitmapBytes, 0, iconBitmapBytes.length); // Convert byte[] back to Bitmap
        imageView.setImageBitmap(bitmapIcon);
    }
}
