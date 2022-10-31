package com.appynitty.swachbharatabhiyan;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.appynitty.swachbharatabhiyanlibrary.activity.WelcomeActivity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LanguagePojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.LocaleHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    protected void attachBaseContext(Context base) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(LocaleHelper.onAttach(base, AUtils.LanguageConstants.MARATHI));
        } else {
            super.attachBaseContext(base);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //    Prefs.putString(AUtils.APP_ID, "3104");
        Prefs.putString(AUtils.APP_ID, "3126");

//        Prefs.putString(AUtils.APP_ID, "3124"); // nagbhid
        //   Prefs.putString(AUtils.APP_ID, "3128"); // chimur
        //    Prefs.putString(AUtils.APP_ID, "3127");  // akot
      //  Prefs.putString(AUtils.APP_ID, "3098");       //for Appynitty ULB APP_ID - 3098
        // Prefs.putString(AUtils.APP_ID, "3122");       //for Appynitty ULB APP_ID - 3098
        // Prefs.putString(AUtils.APP_ID, "3111");       //for Shrirampur ULB APP_ID - 3111
        //Prefs.putString(AUtils.APP_ID, "3127");       //for Akot ULB APP_ID - 3127
//        Prefs.putString(AUtils.APP_ID, "3123");       //for Chandwad ULB APP_ID - 3123
        // Prefs.putString(AUtils.APP_ID, "3068");       //for nagpur ulb app id - 3068
//        Prefs.putString(AUtils.APP_ID, "3099");       //for Indapur Nagar Parishad ULB APP_ID - 3106
        //for nagpur APP_ID="3068", Demo App-"1"
//           Prefs.putString(AUtils.APP_ID, "3041");
//        Prefs.putString(AUtils.APP_ID, "1003");
//        Prefs.putInt(AUtils.VERSION_CODE, BuildConfig.VERSION_CODE);
        Prefs.putInt(AUtils.VERSION_CODE, BuildConfig.VERSION_CODE);
//        initLanguageList();

        startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
    }

    private void initLanguageList() {

        ArrayList<LanguagePojo> languagePojos = new ArrayList<>();

        LanguagePojo eng = new LanguagePojo();
        eng.setLanguage(AUtils.LanguageNameConstants.ENGLISH);
        eng.setLanguageId(AUtils.LanguageIDConstants.ENGLISH);
        languagePojos.add(eng);

        LanguagePojo mar = new LanguagePojo();
        mar.setLanguageId(AUtils.LanguageIDConstants.MARATHI);
        mar.setLanguage(AUtils.LanguageNameConstants.MARATHI);
        languagePojos.add(mar);

        LanguagePojo hi = new LanguagePojo();
        hi.setLanguageId(AUtils.LanguageIDConstants.HINDI);
        hi.setLanguage(AUtils.LanguageNameConstants.HINDI);
        languagePojos.add(hi);

        AUtils.setLanguagePojoList(languagePojos);

    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
