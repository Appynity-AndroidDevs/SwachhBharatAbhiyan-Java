package com.appynitty.swachbharatabhiyanlibrary.login.repository;

import com.appynitty.swachbharatabhiyanlibrary.login.RetrofitClient;
import com.appynitty.swachbharatabhiyanlibrary.login.network.LoginInterface;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LoginDetailsPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LoginPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private LoginInterface loginAPI = RetrofitClient.getInstance().getLoginAPI();
    private final Executor executor = Executors.newSingleThreadExecutor();
    private LoginUserCallBacks loginUserCallBacks;

    public LoginRepository(LoginUserCallBacks loginUserCallBacks) {
        this.loginUserCallBacks = loginUserCallBacks;
    }

    public void loginUser(LoginPojo loginPojo) {

        executor.execute(new Runnable() {
            @Override
            public void run() {


                Call<LoginDetailsPojo> call = loginAPI.saveLoginDetails(Prefs.getString(AUtils.APP_ID, "")
                        , AUtils.CONTENT_TYPE, loginPojo.getEmployeeType(),
                        loginPojo);

                call.enqueue(new Callback<LoginDetailsPojo>() {
                    @Override
                    public void onResponse(Call<LoginDetailsPojo> call, Response<LoginDetailsPojo> response) {
                        loginUserCallBacks.onLoginResponseSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<LoginDetailsPojo> call, Throwable t) {
                        loginUserCallBacks.onLoginResponseFailure(t);
                    }
                });
            }
        });
    }

    public interface LoginUserCallBacks {
        void onLoginResponseSuccess(LoginDetailsPojo loginDetailsPojo);

        void onLoginResponseFailure(Throwable t);
    }
}
