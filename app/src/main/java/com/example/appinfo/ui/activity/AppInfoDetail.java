package com.example.appinfo.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appinfo.R;
import com.example.appinfo.data.model.DeviceAppInfo;

public class AppInfoDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info_detail);

        DeviceAppInfo deviceAppInfo = (DeviceAppInfo) getIntent().getParcelableExtra("appInfo");
        Toast.makeText(this, deviceAppInfo.getName(), Toast.LENGTH_SHORT).show();

        byte[] iconBitmapBytes = deviceAppInfo.getIconBitmapBytes();

        Bitmap bitmapIcon = BitmapFactory.decodeByteArray(iconBitmapBytes, 0, iconBitmapBytes.length); // Convert byte[] back to Bitmap
        ((ImageView) (findViewById(R.id.image_view))).setImageBitmap(bitmapIcon);
    }
}