package com.appynitty.swachbharatabhiyanlibrary.login;

import com.appynitty.swachbharatabhiyanlibrary.login.network.LoginInterface;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private final LoginInterface loginAPI;

    private RetrofitClient() {

        Retrofit retrofit = new Retrofit.Builder()
                //   .baseUrl(AUtils.SERVER_URL)
                .baseUrl(AUtils.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginAPI = retrofit.create(LoginInterface.class);

    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public LoginInterface getLoginAPI() {
        return loginAPI;
    }

}
