package com.example.appinfo.data.repository;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.appinfo.data.model.DeviceAppInfo;

import java.util.ArrayList;
import java.util.List;
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
            List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(0);

            List<DeviceAppInfo> installedAppInfoList = new ArrayList<>();
            for (ApplicationInfo application : applicationInfoList) {
                if ((application.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    String appName = application.loadLabel(packageManager).toString();
                    String packageName = application.packageName;
                    Drawable appIcon = application.loadIcon(packageManager);
                    DeviceAppInfo deviceAppInfo = new DeviceAppInfo(appName, packageName, appIcon);
                    installedAppInfoList.add(deviceAppInfo);
                }
            }

            if (listener != null) {
                listener.onTaskCompleted(installedAppInfoList);
            }
        }
    }
}
