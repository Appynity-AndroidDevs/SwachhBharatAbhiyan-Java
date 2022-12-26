package com.appynitty.swachbharatabhiyanlibrary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.SurPagerAdapter;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormFiveFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormFourFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormOneFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormThreeFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormTwoFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SurveyInformationActivity extends AppCompatActivity {

    private final static int NUM_PAGES = 5;
    private Context context;
    private FrameLayout frameLayout;
    private SurveyFormOneFragment srvFromOneFrag;
    private SurveyFormTwoFragment srvFromTwoFrag;
    private SurveyFormThreeFragment srvFromThreeFrag;
    private SurveyFormFourFragment srvFromFourFrag;
    private SurveyFormFiveFragment srvFromFiveFrag;

    private Button btnBack,btnNext, btnDone;
    private ImageView imgBack;

    private ViewPager2 viewPager;
    private SurPagerAdapter pagerAdapter;
    private View vOne, vTwo, vThree, vFour,vFive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_information);
        init();
    }
    private void init(){
        context = this;
        frameLayout = findViewById(R.id.container_frame_layout);
        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new SurPagerAdapter(getSupportFragmentManager(),getLifecycle());
        imgBack = findViewById(R.id.img_survey_back);
        btnNext = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);
        btnDone = findViewById(R.id.btn_done);

        vOne = findViewById(R.id.view_one);
        vTwo = findViewById(R.id.view_two);
        vThree = findViewById(R.id.view_three);
        vFour = findViewById(R.id.view_four);
        vFive = findViewById(R.id.view_five);

        btnDone.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);

        srvFromOneFrag = new SurveyFormOneFragment();
        srvFromTwoFrag = new SurveyFormTwoFragment();
        srvFromThreeFrag = new SurveyFormThreeFragment();
        srvFromFourFrag = new SurveyFormFourFragment();
        srvFromFiveFrag = new SurveyFormFiveFragment();
        viewPager.setAdapter(pagerAdapter);

        setOnClick();
    }



    private void setOnClick(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() < Objects.requireNonNull(viewPager.getAdapter()).getItemCount()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() != 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SurveyCompletActivity.class));
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("Rahul", "PageChange: "+position);

                if(position==0) {
                    btnBack.setVisibility(View.GONE);
                }else  {
                    btnBack.setVisibility(View.VISIBLE);
                }
                if(position < Objects.requireNonNull(viewPager.getAdapter()).getItemCount() -1 ) {
                    btnNext.setVisibility(View.VISIBLE);
                    btnDone.setVisibility(View.GONE);

                }else  {
                    btnNext.setVisibility(View.GONE);
                    btnDone.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }
}