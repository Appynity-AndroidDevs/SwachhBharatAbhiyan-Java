package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.util.Log;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.pojos.AppGeoArea;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.AppGeoAreaWebService;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppGeoAreaAdapter {
    private static final String TAG = "AppGeoAreaAdapter";
    private static final AppGeoAreaAdapter instance = new AppGeoAreaAdapter();


    public static AppGeoAreaAdapter getInstance() {
        return instance;
    }

    public void getAppGeoArea(AppGeoListener appGeoListener) {
        AppGeoAreaWebService service = Connection.createService(AppGeoAreaWebService.class, AUtils.SERVER_URL);
        Call<AppGeoArea> appGeoAreaCall = service.getAppGeoArea(Prefs.getString(AUtils.APP_ID, null));
        appGeoAreaCall.enqueue(new Callback<AppGeoArea>() {
            @Override
            public void onResponse(Call<AppGeoArea> call, Response<AppGeoArea> response) {
//                Log.e(TAG, "onResponse: GeoVertices: " + response.body().getAreaGeoVertices());
                if (response.body() != null)
                    appGeoListener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<AppGeoArea> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                appGeoListener.onFailure(t);
            }
        });
    }

    public interface AppGeoListener {
        void onResponse(AppGeoArea areaGeoVertices);

        void onFailure(Throwable throwable);
    }
}
