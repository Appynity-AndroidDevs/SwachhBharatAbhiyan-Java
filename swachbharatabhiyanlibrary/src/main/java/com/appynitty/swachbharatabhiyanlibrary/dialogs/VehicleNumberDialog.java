package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.VehicleNumberAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.VehicleNumberAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleNumberPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

public class VehicleNumberDialog extends Dialog {
    private final static String TAG  = "VehicleNumberDialog";
    private Context context;
    private ArrayList<VehicleNumberPojo> list;
    private RecyclerView recyclerView;
    private TextView txtNoData;
    private ProgressBar loader;
    private LinearLayoutManager layoutManager;
    private VehicleNumberAdapter adapter;
    private VehicleNumberDialogInterface listener;
    private VehicleNumberAdapterClass vehicleNumberAdapter;
    String vehicleNum = "";


    public VehicleNumberDialog(@NonNull Context context, VehicleNumberDialogInterface listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    public interface VehicleNumberDialogInterface{
        void onClicked(VehicleNumberPojo model);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_vehicle_number);
        init();
    }

    private void init() {
        context = this.getContext();
        recyclerView = findViewById(R.id.recycler);
        txtNoData = findViewById(R.id.txt_no_data);
        loader = findViewById(R.id.progress_circular);
        list = new ArrayList<>();
        layoutManager = new LinearLayoutManager(context);
        setOnRecycler();
        onClick();

    }

    private void onClick() {

    }


    private void setOnRecycler() {
       adapter = new VehicleNumberAdapter(context, list, new VehicleNumberAdapter.VehicleNumberAdapterInterface() {
           @Override
           public void onItemClicked(VehicleNumberPojo model) {
               Log.e(TAG, "Vehicle number data: "+model.getVehicleNo());
              vehicleNum = model.getVehicleNo();
           }
       });
       recyclerView.setLayoutManager(layoutManager);
       recyclerView.setAdapter(adapter);
    }
}
