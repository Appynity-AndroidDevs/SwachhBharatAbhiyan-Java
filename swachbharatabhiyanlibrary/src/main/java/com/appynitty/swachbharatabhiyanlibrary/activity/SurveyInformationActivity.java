package com.appynitty.swachbharatabhiyanlibrary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormFiveFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormFourFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormOneFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormThreeFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormTwoFragment;

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

    private Button btnBack,btnNext;
    private ImageView imgBack;

    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private ImageView[] dots;
    private View viewLineOne, viewLineTwo, viewLineThree,viewLineFour, viewLineFive;

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
        imgBack = findViewById(R.id.img_survey_back);
        btnNext = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);
        linearLayout = findViewById(R.id.viewPagerCountDots);

        srvFromOneFrag = new SurveyFormOneFragment();
        srvFromTwoFrag = new SurveyFormTwoFragment();
        srvFromThreeFrag = new SurveyFormThreeFragment();
        srvFromFourFrag = new SurveyFormFourFragment();
        srvFromFiveFrag = new SurveyFormFiveFragment();

        loadFragment(srvFromOneFrag);
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

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_frame_layout, fragment);
            ft.commit();
            return true;
        }
        return false;
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

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);
            linearLayout.addView(dots[i], params);
        }
    }
}