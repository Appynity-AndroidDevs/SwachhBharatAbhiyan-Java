package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleNumberPojo;

import java.util.ArrayList;

/******
 * created by Rahul Rokade
 * vehicle number item adapter
 * */

public class VehicleNumberAdapter extends RecyclerView.Adapter<VehicleNumberAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<VehicleNumberPojo> list;
    private VehicleNumberAdapterInterface listener;

    public VehicleNumberAdapter(Context context, ArrayList<VehicleNumberPojo> list, VehicleNumberAdapterInterface listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public interface VehicleNumberAdapterInterface{
        void onItemClicked(VehicleNumberPojo model);
    }

    @NonNull
    @Override
    public VehicleNumberAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.vehicle_number_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleNumberAdapter.MyViewHolder holder, int position) {
            VehicleNumberPojo model = list.get(position);
            holder.txtVnum.setText(model.getVehicleNo());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        listener.onItemClicked(model);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtVnum;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtVnum = itemView.findViewById(R.id.txt_vNo_item);
        }
    }
}
