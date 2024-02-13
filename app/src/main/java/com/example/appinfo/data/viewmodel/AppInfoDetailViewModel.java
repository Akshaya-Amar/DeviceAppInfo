package com.example.appinfo.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appinfo.data.model.DeviceAppInfo;
import com.example.appinfo.data.model.DeviceAppProfile;
import com.example.appinfo.data.repository.DeviceAppRepository;
import com.example.appinfo.util.ButtonType;

import java.util.List;

public class AppInfoDetailViewModel extends AndroidViewModel {

    private final Application application;
    private final MutableLiveData<List<DeviceAppProfile>> deviceAppProfile;
    private final MutableLiveData<ButtonType> buttonType;
    private final MutableLiveData<Boolean> isDeviceAppProfileListRequested;

    public AppInfoDetailViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        deviceAppProfile = new MutableLiveData<>();
        buttonType = new MutableLiveData<>();
        isDeviceAppProfileListRequested = new MutableLiveData<>(false);
    }

    public LiveData<List<DeviceAppProfile>> getDeviceAppProfile() {
        return deviceAppProfile;
    }

    public LiveData<ButtonType> getButtonType() {
        return buttonType;
    }

    public LiveData<Boolean> getIsDeviceAppProfileListRequested() {
        return isDeviceAppProfileListRequested;
    }

    public void getDeviceAppProfileList(DeviceAppInfo deviceAppInfo) {
        if (Boolean.FALSE.equals(getIsDeviceAppProfileListRequested().getValue())) {
            List<DeviceAppProfile> deviceAppProfiles = DeviceAppRepository.getInstance(application).getDeviceAppInfo(deviceAppInfo);
            deviceAppProfile.setValue(deviceAppProfiles);
            isDeviceAppProfileListRequested.setValue(true);
        }
    }

    public void onPlayStoreButtonClick() {
        buttonType.setValue(ButtonType.PLAY_STORE);
    }

    public void onSettingButtonClick() {
        buttonType.setValue(ButtonType.SETTINGS);
    }

    public void onAppLaunchButtonClick() {
        buttonType.setValue(ButtonType.LAUNCH_APP);
    }
}