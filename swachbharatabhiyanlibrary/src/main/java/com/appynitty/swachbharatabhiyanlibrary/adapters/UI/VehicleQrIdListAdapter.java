package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaHousePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleNumberPojo;

import java.util.List;

/*****
 * created by Rahul rokade
 * */
public class VehicleQrIdListAdapter extends BaseAdapter {
    List<CollectionAreaHousePojo> vehicleNumberList;

    public VehicleQrIdListAdapter(List<CollectionAreaHousePojo> vehicleNumberList) {
        this.vehicleNumberList = vehicleNumberList;
    }


    @Override
    public int getCount() {
        return vehicleNumberList.size();
    }

    @Override
    public CollectionAreaHousePojo getItem(int position) {
        return vehicleNumberList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_number_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtVnum.setText(getItem(position).getHouseid());
        return convertView;
    }

    static class ViewHolder {
        TextView txtVnum;

        ViewHolder(View view) {
            txtVnum = view.findViewById(R.id.txt_vNo_item);
        }
    }
}
