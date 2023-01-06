package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.VersionDetailsAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.viewmodels.TokenViewModel;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.LocaleHelper;

import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenActivity";
    VersionDetailsAdapterClass mAdapter;
    private TokenViewModel tokenViewModel;

    @Override
    protected void attachBaseContext(Context base) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(LocaleHelper.onAttach(base));
        } else {
            super.attachBaseContext(base);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        AUtils.currentContextConstant = this;

        mAdapter = new VersionDetailsAdapterClass();
        tokenViewModel = new ViewModelProvider(this).get(TokenViewModel.class);

        setDefaultLanguage();

        /*if(AUtils.isInternetAvailable())
            mAdapter.checkVersionDetails();
        else
            loadNextPage();*/
        loadNextPage();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAdapter.setVersionDetailsListener(new VersionDetailsAdapterClass.VersionDetailsListener() {
            @Override
            public void onSuccessCallBack() {
                AUtils.showConfirmationDialog(SplashScreenActivity.this, AUtils.VERSION_CODE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        AUtils.rateApp(SplashScreenActivity.this);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        finish();
                    }
                });
            }

            @Override
            public void onFailureCallBack() {
                loadNextPage();
            }
        });

        if (Prefs.contains(AUtils.BEARER_TOKEN)) {

            if (!DateUtils.isToday(Prefs.getLong(AUtils.BEARER_TOKEN_TIME, 0))) {
                Log.e(TAG, " token was expired, getting new one!");
                tokenViewModel.init();
            }
        } else {
            tokenViewModel.init();
            Log.e(TAG, "onCreate: getting new token");
        }

        tokenViewModel.tokenLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String token_value) {
                Prefs.putString(AUtils.BEARER_TOKEN, token_value);
                Prefs.putLong(AUtils.BEARER_TOKEN_TIME, System.currentTimeMillis());
            }
        });

    }

    private void loadNextPage() {
        if (Prefs.getBoolean(AUtils.PREFS.IS_USER_LOGIN, false)) {
            loadDashboard();
        } else {
            loadLogin();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (AUtils.isInternetAvailable()) {
            AUtils.hideSnackBar();
        } else {
            AUtils.showSnackBar(findViewById(R.id.parent));
        }
    }

    private void setDefaultLanguage() {
        AUtils.changeLanguage(SplashScreenActivity.this, Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID));
    }

    private void loadDashboard() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String userType = Prefs.getString(AUtils.PREFS.USER_TYPE_ID, AUtils.USER_TYPE.USER_TYPE_GHANTA_GADI);
                startActivity(new Intent(SplashScreenActivity.this, AUtils.getDashboardClass(userType)));
                SplashScreenActivity.this.finish();
            }
        }, AUtils.SPLASH_SCREEN_TIME);
    }

    private void loadLogin() {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                SplashScreenActivity.this.finish();
            }
        }, AUtils.SPLASH_SCREEN_TIME);


    }


}