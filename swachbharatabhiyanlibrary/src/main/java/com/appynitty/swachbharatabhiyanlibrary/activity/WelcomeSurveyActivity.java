package com.appynitty.swachbharatabhiyanlibrary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

public class WelcomeSurveyActivity extends AppCompatActivity {
    private Context context;
    private ImageView imgBack;
    private Button btnLetsGo;
    private TextView txtHouseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_survey);
        init();
    }

    private void init(){
        context = this;
        imgBack = findViewById(R.id.img_survey_back);
        btnLetsGo = findViewById(R.id.btn_less_go);
        txtHouseId = findViewById(R.id.txt_house_id);
        setOnClick();
    }
    private void setOnClick(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, EmpDashboardActivity.class));
                finish();
            }
        });

        btnLetsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SurveyInformationActivity.class));
            }
        });
    }
}