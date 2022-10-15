package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import static com.appynitty.swachbharatabhiyanlibrary.utils.AUtils.gpsStatusCheck;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.activity.EmpQRcodeScannerActivity;
import com.appynitty.swachbharatabhiyanlibrary.activity.QRcodeScannerActivity;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.VerifyDataAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.login.InternetWorking;
import com.appynitty.swachbharatabhiyanlibrary.pojos.MenuListPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Ayan Dey on 22/1/20.
 */
public class DashboardMenuAdapter extends RecyclerView.Adapter<DashboardMenuAdapter.WasteMenuViewHolder> {

    private static final String TAG = "DashboardMenuAdapter";
    private final Context context;
    final Executor executor = Executors.newSingleThreadExecutor();
    private List<MenuListPojo> menuList;
    private final VerifyDataAdapterClass verifyDataAdapterClass;
    private ProgressBar progressBar;

    public DashboardMenuAdapter(Context context, ProgressBar progressBar) {
        this.context = context;
        verifyDataAdapterClass = new VerifyDataAdapterClass(context);
        this.progressBar = progressBar;
    }

    public void setMenuList(List<MenuListPojo> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public WasteMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_dashboard_menu_card, parent, false);
        return new WasteMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WasteMenuViewHolder holder, int position) {
        final MenuListPojo menuPojo = menuList.get(position);
        holder.menuNameTextView.setText(menuPojo.getMenuName());
        holder.menuImageView.setImageResource(menuPojo.getImage());
        holder.menuCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Prefs.getString(AUtils.PREFS.USER_TYPE_ID, AUtils.USER_TYPE.USER_TYPE_EMP_SCANNIFY)) {
                    case AUtils.USER_TYPE.USER_TYPE_EMP_SCANNIFY:
                        break;
                    case AUtils.USER_TYPE.USER_TYPE_WASTE_MANAGER:
                        verifyDataAdapterClass.verifyWasteManagementSync();
                        break;
                    case AUtils.USER_TYPE.USER_TYPE_GHANTA_GADI:
                    default:
                        if (!AUtils.isSyncOfflineDataRequestEnable) {
                            verifyDataAdapterClass.verifyOfflineSync();
                        }
                        break;
                }
                if (menuPojo.getCheckAttendance()) {
                    if (AUtils.isIsOnduty()) {
                        if (menuPojo.getNextIntentClass().equals(EmpQRcodeScannerActivity.class)) {
                            if (AUtils.isInternetAvailable() && AUtils.isConnectedFast(context)) {
                                progressBar.setVisibility(View.VISIBLE);
                                executor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (InternetWorking.isOnline()) {

                                            Handler handler = new Handler(Looper.getMainLooper());
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressBar.setVisibility(View.GONE);
                                                    AUtils.getAppGeoArea(new AUtils.geoAreaRequestListener() {
                                                        @Override
                                                        public void onResponse() {
                                                            if (Prefs.getBoolean(AUtils.PREFS.IS_AREA_ACTIVE, false)) {
                                                                if (AUtils.isValidArea()) {
                                                                    context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
                                                                } else {
                                                                    AUtils.warning(context, context.getResources().getString(R.string.out_of_area_msg));
                                                                }
                                                            } else {
                                                                context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
                                                            }

                                                        }

                                                        @Override
                                                        public void onFailure(Throwable throwable) {
                                                            Log.e(TAG, "onFailure: " + throwable.getMessage());
                                                            progressBar.setVisibility(View.GONE);
                                                            Handler handler = new Handler(Looper.getMainLooper());
                                                            handler.post(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    if (Prefs.getBoolean(AUtils.PREFS.IS_AREA_ACTIVE, false)) {
                                                                        if (AUtils.isValidArea()) {
                                                                            context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
                                                                        } else {
                                                                            AUtils.warning(context, context.getResources().getString(R.string.out_of_area_msg));
                                                                        }
                                                                    } else {
                                                                        context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });

                                        } else {
                                            Handler handler = new Handler(Looper.getMainLooper());
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressBar.setVisibility(View.GONE);
                                                    if (Prefs.getBoolean(AUtils.PREFS.IS_AREA_ACTIVE, false)) {
                                                        if (AUtils.isValidArea()) {
                                                            context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
                                                        } else {
                                                            AUtils.warning(context, context.getResources().getString(R.string.out_of_area_msg));
                                                        }
                                                    } else {
                                                        context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
                                                    }
                                                }
                                            });

                                        }
                                    }
                                });


                            } else {
                                if (Prefs.getBoolean(AUtils.PREFS.IS_AREA_ACTIVE, false)) {
                                    if (AUtils.isValidArea()) {
                                        context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
                                    } else {
                                        AUtils.warning(context, context.getResources().getString(R.string.out_of_area_msg));
                                    }
                                } else {
                                    context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
                                }

                            }
                        } else if (menuPojo.getNextIntentClass().equals(QRcodeScannerActivity.class)) {
//                            context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
//                            Prefs.putString(AUtils.LAT , "");
                            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
                            } else {
                                gpsStatusCheck(context);
                            }
                        } else {
                            context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
                        }
                    } else {
                        AUtils.warning(context, context.getResources().getString(R.string.be_no_duty));
                    }
                } else
                    context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (menuList != null) ? menuList.size() : 0;
    }

    class WasteMenuViewHolder extends RecyclerView.ViewHolder {

        TextView menuNameTextView;
        ImageView menuImageView;
        LinearLayout menuCardLayout;

        WasteMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            menuNameTextView = itemView.findViewById(R.id.menu_title);
            menuImageView = itemView.findViewById(R.id.menu_icon);
            menuCardLayout = itemView.findViewById(R.id.menu_card_layout);
        }
    }
}
