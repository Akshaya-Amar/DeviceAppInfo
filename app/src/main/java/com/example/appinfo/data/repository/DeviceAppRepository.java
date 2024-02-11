package com.example.appinfo.data.repository;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.appinfo.data.model.DeviceAppInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeviceAppRepository {

    private final Context context;

    public DeviceAppRepository(Context context) {
        this.context = context;
    }

    public interface OnTaskCompleteListener {
        void onTaskCompleted(List<DeviceAppInfo> deviceAppsInfo);
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

            PackageManager packageManager = context.getPackageManager();
//            List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(0);
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

            if (listener != null) {
                listener.onTaskCompleted(installedAppInfoList);
            }
        }
    }
}
