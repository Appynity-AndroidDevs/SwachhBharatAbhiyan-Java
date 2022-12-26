package com.appynitty.swachbharatabhiyanlibrary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
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

import java.util.Objects;

public class SurveyInformationActivity extends AppCompatActivity {

    private int dotsCount = 5;
    private Context context;
    private FrameLayout frameLayout;
    private SurveyFormOneFragment srvFromOneFrag;
    private SurveyFormTwoFragment srvFromTwoFrag;
    private SurveyFormThreeFragment srvFromThreeFrag;
    private SurveyFormFourFragment srvFromFourFrag;
    private SurveyFormFiveFragment srvFromFiveFrag;

    private LinearLayout linearLayout;

    private Button btnBack,btnNext, btnDone;
    private ImageView imgBack;

    private ViewPager2 viewPager;
    private SurPagerAdapter pagerAdapter;
    private ImageView[] dots;


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
        linearLayout = findViewById(R.id.viewPagerCountDots);


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
                if (viewPager.getCurrentItem() < viewPager.getAdapter().getItemCount()) {
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
                drawPageSelectionIndicators(position);
                /*super.onPageSelected(position);*/
                if(position==0) {
                    btnBack.setVisibility(View.GONE);
                }else  {
                    btnBack.setVisibility(View.VISIBLE);
                }
                if(position < Objects.requireNonNull(viewPager.getAdapter()).getItemCount() -1 ) {
                    btnNext.setVisibility(View.VISIBLE);
                }else  {
                    btnNext.setVisibility(View.GONE);
                }

                /*if() {
                    btnNext.setVisibility(View.VISIBLE);
                }else  {
                    btnNext.setVisibility(View.GONE);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }

    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setEnterTransition(slide);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void drawPageSelectionIndicators(int mPosition){
        if(linearLayout!=null) {
            linearLayout.removeAllViews();
        }
        linearLayout=(LinearLayout)findViewById(R.id.viewPagerCountDots);
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(context);
            if(i==mPosition)
                dots[i].setImageDrawable(context.getDrawable(R.drawable.selected_line));
            else
                dots[i].setImageDrawable(context.getDrawable(R.drawable.unselected_line));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10,LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);
            linearLayout.addView(dots[i], params);
        }
    }


}