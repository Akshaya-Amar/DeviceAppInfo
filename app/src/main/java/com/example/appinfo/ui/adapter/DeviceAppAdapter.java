package com.example.appinfo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfo.data.model.DeviceAppInfo;
import com.example.appinfo.databinding.DeviceAppItemBinding;

import java.util.List;

public class DeviceAppAdapter extends RecyclerView.Adapter<DeviceAppAdapter.ViewHolder> {

    private final Context context;
    private List<DeviceAppInfo> deviceAppInfoList;
    private OnItemClickListener onItemClickListener;

    public DeviceAppAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        DeviceAppItemBinding binding = DeviceAppItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeviceAppInfo deviceAppInfo = deviceAppInfoList.get(position);
        holder.binding.setDeviceAppInfo(deviceAppInfo);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return (deviceAppInfoList != null) ? deviceAppInfoList.size() : 0;
    }

    public void setDeviceAppInfoList(List<DeviceAppInfo> deviceAppInfoList) {
        this.deviceAppInfoList = deviceAppInfoList;
        notifyItemInserted(deviceAppInfoList.size() - 1);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final DeviceAppItemBinding binding;

        public ViewHolder(@NonNull DeviceAppItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(binding.getDeviceAppInfo());
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(DeviceAppInfo deviceAppInfo);
    }
}
