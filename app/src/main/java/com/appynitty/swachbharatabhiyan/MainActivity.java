package com.appynitty.swachbharatabhiyan;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.appynitty.swachbharatabhiyanlibrary.activity.WelcomeActivity;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.LocaleHelper;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, AUtils.LanguageConstants.MARATHI));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Prefs.putString(AUtils.APP_ID, "3098");       //for Appynitty ULB APP_ID - 3098
        Prefs.putInt(AUtils.VERSION_CODE, BuildConfig.VERSION_CODE);

        startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
