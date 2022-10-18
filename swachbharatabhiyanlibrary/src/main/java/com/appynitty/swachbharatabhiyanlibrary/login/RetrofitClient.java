package com.appynitty.swachbharatabhiyanlibrary.login;

import com.appynitty.swachbharatabhiyanlibrary.login.network.LoginInterface;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private final LoginInterface loginAPI;

    private RetrofitClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder()
                //   .baseUrl(AUtils.SERVER_URL)
                .baseUrl(AUtils.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient).build();

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
