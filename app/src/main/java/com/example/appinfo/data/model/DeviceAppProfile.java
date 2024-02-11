package com.example.appinfo.data.model;

public class DeviceAppProfile {

    private final String title;
    private final String value;

    public DeviceAppProfile(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }
}
