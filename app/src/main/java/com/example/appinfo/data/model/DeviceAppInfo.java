package com.example.appinfo.data.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import java.io.ByteArrayOutputStream;

public class DeviceAppInfo implements Parcelable {

    private final String name;
    private final String packageName;
    private final byte[] iconBitmapBytes;

    public DeviceAppInfo(String name, String packageName, Drawable icon) {
        this.name = name;
        this.packageName = packageName;
        this.iconBitmapBytes = convertBitmapToByteArray(getBitmapFromDrawable(icon)); // Storing Bitmap as byte[]: storing icon bitmap as a byte[], which is more suitable for serialization and deserialization.
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public byte[] getIconBitmapBytes() {
        return iconBitmapBytes;
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // else is an instance of AdaptiveIconDrawable
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmapFromDrawable) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapFromDrawable.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static final Creator<DeviceAppInfo> CREATOR = new Creator<DeviceAppInfo>() {
        @Override
        public DeviceAppInfo createFromParcel(Parcel in) {
            return new DeviceAppInfo(in);
        }

        @Override
        public DeviceAppInfo[] newArray(int size) {
            return new DeviceAppInfo[size];
        }
    };

    protected DeviceAppInfo(Parcel in) {
        name = in.readString();
        packageName = in.readString();
        iconBitmapBytes = in.createByteArray(); // Read byte[] from Parcel
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(packageName);
        dest.writeByteArray(iconBitmapBytes); // Write byte[] to Parcel
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @BindingAdapter("android:bitmap_drawable")
    public static void setBitmapDrawable(ImageView imageView, byte[] iconBitmapBytes) {
        Bitmap bitmapIcon = BitmapFactory.decodeByteArray(iconBitmapBytes, 0, iconBitmapBytes.length); // Convert byte[] back to Bitmap
        imageView.setImageBitmap(bitmapIcon);
    }
}