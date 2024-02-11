package com.example.appinfo.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfo.R;
import com.example.appinfo.data.model.DeviceAppInfo;
import com.example.appinfo.data.viewmodel.DeviceAppViewModel;
import com.example.appinfo.databinding.ActivityDeviceAppBinding;
import com.example.appinfo.ui.adapter.DeviceAppAdapter;

import java.util.List;

public class DeviceAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDeviceAppBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_device_app);

        DeviceAppViewModel viewModel = new ViewModelProvider(this).get(DeviceAppViewModel.class);
        binding.setDeviceAppViewModel(viewModel);
        binding.setLifecycleOwner(this);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        DeviceAppAdapter adapter = new DeviceAppAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel.getDeviceAppInfoList().observe(this, adapter::setDeviceAppInfoList);

        adapter.setOnItemClickListener(deviceAppInfo -> {
            Intent intent = new Intent(DeviceAppActivity.this, AppInfoDetail.class);
            intent.putExtra("appInfo", deviceAppInfo);
            startActivity(intent);
        });
    }
}