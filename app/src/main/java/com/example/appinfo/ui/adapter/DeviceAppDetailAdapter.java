package com.example.appinfo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfo.data.model.DeviceAppInfo;
import com.example.appinfo.data.model.DeviceAppProfile;
import com.example.appinfo.databinding.DeviceInfoItemListBinding;

import java.util.List;

public class DeviceAppDetailAdapter extends RecyclerView.Adapter<DeviceAppDetailAdapter.ViewHolder> {

    private final Context context;
    private List<DeviceAppProfile> appProfileList;

    public DeviceAppDetailAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        DeviceInfoItemListBinding binding = DeviceInfoItemListBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeviceAppProfile appProfile = appProfileList.get(position);
        holder.binding.setDeviceAppProfile(appProfile);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return appProfileList != null ? appProfileList.size() : 0;
    }

    public void setAppProfileList(List<DeviceAppProfile> appProfileList) {
        this.appProfileList = appProfileList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final DeviceInfoItemListBinding binding;

        public ViewHolder(@NonNull DeviceInfoItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
