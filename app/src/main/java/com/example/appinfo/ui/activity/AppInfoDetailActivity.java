package com.example.appinfo.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfo.R;
import com.example.appinfo.data.model.DeviceAppInfo;
import com.example.appinfo.data.viewmodel.AppInfoDetailViewModel;
import com.example.appinfo.databinding.ActivityAppInfoDetailBinding;
import com.example.appinfo.ui.adapter.DeviceAppDetailAdapter;
import com.google.android.material.snackbar.Snackbar;

public class AppInfoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAppInfoDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_app_info_detail);

        AppInfoDetailViewModel viewModel = new ViewModelProvider(this).get(AppInfoDetailViewModel.class);
        binding.setAppInfoDetailViewModel(viewModel);

        DeviceAppInfo deviceAppInfo = (DeviceAppInfo) getIntent().getParcelableExtra("appInfo");
        binding.setDeviceAppInfo(deviceAppInfo);
        binding.setLifecycleOwner(this);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        DeviceAppDetailAdapter adapter = new DeviceAppDetailAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel.getDeviceAppProfileList(deviceAppInfo);

        viewModel.getDeviceAppProfile().observe(this, adapter::setAppProfileList);

        String packageName = deviceAppInfo.getPackageName();

        viewModel.getButtonType().observe(this, buttonType -> {

            switch (buttonType) {
                case PLAY_STORE:
                    openAppInPlayStore(packageName);
                    break;

                case SETTINGS:
                    openAppSettings(packageName);
                    break;

                case LAUNCH_APP:
                    launchApp(packageName, binding);
            }
        });
    }

    private void openAppInPlayStore(String packageName) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_PICK, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    private void openAppSettings(String packageName) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", packageName, null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void launchApp(String packageName, ActivityAppInfoDetailBinding binding) {
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
            startActivity(intent);
        } catch (Exception exception) {
            Snackbar.make(binding.getRoot(), "App can't be launched", Snackbar.LENGTH_LONG).show();
        }
    }
}