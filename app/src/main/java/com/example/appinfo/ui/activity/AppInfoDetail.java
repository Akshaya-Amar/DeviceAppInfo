package com.example.appinfo.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfo.R;
import com.example.appinfo.data.model.DeviceAppInfo;
import com.example.appinfo.data.model.DeviceAppProfile;
import com.example.appinfo.databinding.ActivityAppInfoDetailBinding;
import com.example.appinfo.ui.adapter.DeviceAppDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class AppInfoDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAppInfoDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_app_info_detail);

        DeviceAppInfo deviceAppInfo = (DeviceAppInfo) getIntent().getParcelableExtra("appInfo");
        binding.setDeviceAppInfo(deviceAppInfo);
        binding.setLifecycleOwner(this);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        String[] titles = {
                "Package Name",
                "Version Name",
                "Version Code",
                "Path",
                "First Install Time",
                "Last Update Time",
                "Target SDK Version"
        };

        String[] descriptions = {
                deviceAppInfo.getPackageName(),
                deviceAppInfo.getVersionName(),
                String.valueOf(deviceAppInfo.getVersionCode()),
                deviceAppInfo.getPath(),
                deviceAppInfo.getFirstInstallTimeInDateFormat(),
                deviceAppInfo.getLastUpdateTimeInDateFormat(),
                String.valueOf(deviceAppInfo.getTargetSDKVersion())
        };

        List<DeviceAppProfile> appProfileList = new ArrayList<>();
        for (int index = 0; index < titles.length; ++index) {
            DeviceAppProfile appProfile = new DeviceAppProfile(titles[index], descriptions[index]);
            appProfileList.add(appProfile);
        }

        DeviceAppDetailAdapter adapter = new DeviceAppDetailAdapter(this, appProfileList);
        recyclerView.setAdapter(adapter);
    }
}