package com.example.appinfo.data.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DeviceAppInfo implements Parcelable {

    private final String name;
    private final String packageName;
    private final int versionCode;
    private final String versionName;
    private final int targetSDKVersion;
    private final String path;
    private final long firstInstallTime;
    private final long lastUpdateTime;
    private final byte[] iconBitmapBytes;

    public DeviceAppInfo(String name, String packageName, int versionCode, String versionName, int targetSDKVersion, String path, long firstInstallTime, long lastUpdateTime, Drawable icon) {
        this.name = name;
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.targetSDKVersion = targetSDKVersion;
        this.path = path;
        this.firstInstallTime = firstInstallTime;
        this.lastUpdateTime = lastUpdateTime;
        this.iconBitmapBytes = convertBitmapToByteArray(getBitmapFromDrawable(icon)); // Storing Bitmap as byte[]: storing icon bitmap as a byte[], which is more suitable for serialization and deserialization.
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public int getTargetSDKVersion() {
        return targetSDKVersion;
    }

    public String getPath() {
        return path;
    }

    public long getFirstInstallTime() {
        return firstInstallTime;
    }

    public String getFirstInstallTimeInDateFormat() {
        return getDateFormat(getFirstInstallTime());
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public String getLastUpdateTimeInDateFormat() {
        return getDateFormat(getLastUpdateTime());
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
        versionCode = in.readInt();
        versionName = in.readString();
        targetSDKVersion = in.readInt();
        path = in.readString();
        firstInstallTime = in.readLong();
        lastUpdateTime = in.readLong();
        iconBitmapBytes = in.createByteArray(); // Read byte[] from Parcel
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(packageName);
        dest.writeInt(versionCode);
        dest.writeString(versionName);
        dest.writeInt(targetSDKVersion);
        dest.writeString(path);
        dest.writeLong(firstInstallTime);
        dest.writeLong(lastUpdateTime);
        dest.writeByteArray(iconBitmapBytes); // Write byte[] to Parcel
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private String getDateFormat(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }
}