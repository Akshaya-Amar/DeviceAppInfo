package com.example.appinfo.data.repository;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.appinfo.data.model.DeviceAppInfo;
import com.example.appinfo.data.model.DeviceAppProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeviceAppRepository {

    private static DeviceAppRepository deviceAppRepository;
    private final Application application;

    private DeviceAppRepository(Application application) {
        this.application = application;
    }

    public static DeviceAppRepository getInstance(Application application) {
        if (deviceAppRepository == null) {
            deviceAppRepository = new DeviceAppRepository(application);
        }

        return deviceAppRepository;
    }

    public interface OnTaskCompleteListener {
        void onTaskCompleted(List<DeviceAppInfo> deviceAppsInfo);

        void onTaskFailed(String errorMessage);
    }

    public void getInstalledApps(OnTaskCompleteListener listener) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new GetInstalledAppsRunnable(listener));
    }

    private class GetInstalledAppsRunnable implements Runnable {

        private final OnTaskCompleteListener listener;

        public GetInstalledAppsRunnable(OnTaskCompleteListener listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            Context context = application.getApplicationContext();
            PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);

            List<DeviceAppInfo> installedAppInfoList = new ArrayList<>();
            for (PackageInfo packageInfo : packageInfoList) {

                ApplicationInfo applicationInfo = packageInfo.applicationInfo;

                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {

                    String appName = applicationInfo.loadLabel(packageManager).toString();
                    String packageName = applicationInfo.packageName;
                    int versionCode = packageInfo.versionCode;
                    String versionName = packageInfo.versionName;
                    int targetSDKVersion = applicationInfo.targetSdkVersion;
                    String path = applicationInfo.dataDir;
                    long firstInstallTime = packageInfo.firstInstallTime;
                    long lastUpdateTime = packageInfo.lastUpdateTime;
                    Drawable appIcon = applicationInfo.loadIcon(packageManager);

                    DeviceAppInfo deviceAppInfo = new DeviceAppInfo(appName, packageName, versionCode, versionName, targetSDKVersion, path, firstInstallTime, lastUpdateTime, appIcon);
                    installedAppInfoList.add(deviceAppInfo);
                }
            }

            if (!installedAppInfoList.isEmpty()) {
                listener.onTaskCompleted(installedAppInfoList);
            } else {
                listener.onTaskFailed("Unable to retrieve apps from device");
            }
        }
    }

    private static final String[] titles = {
            "Package Name",
            "Version Name",
            "Version Code",
            "Path",
            "First Install Time",
            "Last Update Time",
            "Target SDK Version"
    };

    public List<DeviceAppProfile> getDeviceAppInfo(DeviceAppInfo deviceAppInfo) {

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

        return appProfileList;
    }
}
