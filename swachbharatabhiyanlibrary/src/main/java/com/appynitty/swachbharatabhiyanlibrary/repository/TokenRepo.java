package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.TokenWebService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenRepo {
    private static final String TAG = "TokenRepo";
    private static final TokenRepo instance = new TokenRepo();
    private final MutableLiveData<String> tokenLiveData = new MutableLiveData<>();

    public static TokenRepo getInstance() {
        return instance;
    }

    public void getToken(iTokenResponseListener tokenResponseListener) {
        String enAppID = AUtils.getEncodedAppId();
        Log.e(TAG, "getToken: ");
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        TokenWebService tokenWebService = Connection.createService(TokenWebService.class, gson, AUtils.SERVER_URL);

        Call<String> tokenCall = tokenWebService.getToken(enAppID);

        tokenCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Log.e(TAG, "onResponse: " + response.body());
                tokenLiveData.setValue(response.body());
                tokenResponseListener.onResponse(tokenLiveData);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                tokenResponseListener.onFailure(t);
            }
        });

    }

    public interface iTokenResponseListener {
        void onResponse(MutableLiveData<String> tokenResponse);

        void onFailure(Throwable throwable);
    }
}
