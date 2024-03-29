package com.example.appinfo.data.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinfo.data.model.DeviceAppInfo;
import com.example.appinfo.data.repository.DeviceAppRepository;

import java.util.List;

public class DeviceAppViewModel extends AndroidViewModel {

    private final Application application;
    private final MutableLiveData<List<DeviceAppInfo>> deviceAppInfoList;
    private final MutableLiveData<Boolean> isLoading;
    private static final String TAG = "DeviceAppViewModel";

    public DeviceAppViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        deviceAppInfoList = new MutableLiveData<>();
        isLoading = new MutableLiveData<>(false);
        getInstalledApps();
    }

    public LiveData<List<DeviceAppInfo>> getDeviceAppInfoList() {
        return deviceAppInfoList;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private void getInstalledApps() {

        isLoading.setValue(true);

        DeviceAppRepository repository = DeviceAppRepository.getInstance(application);
        repository.getInstalledApps(new DeviceAppRepository.OnTaskCompleteListener() {
            @Override
            public void onTaskCompleted(List<DeviceAppInfo> deviceAppsInfo) {
                deviceAppInfoList.postValue(deviceAppsInfo);
                isLoading.postValue(false);
            }

            @Override
            public void onTaskFailed(String errorMessage) {
                Log.i(TAG, "onTaskFailed: " + errorMessage);
                isLoading.postValue(false);
            }
        });
    }
}