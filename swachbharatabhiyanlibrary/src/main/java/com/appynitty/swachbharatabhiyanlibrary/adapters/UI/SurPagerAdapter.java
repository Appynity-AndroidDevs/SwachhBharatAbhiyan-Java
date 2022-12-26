package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormFiveFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormFourFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormOneFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormThreeFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormTwoFragment;

import java.util.ArrayList;
import java.util.List;

public class SurPagerAdapter extends FragmentStateAdapter {

    private Context mContext;
    private ArrayList<Fragment> arrayList = new ArrayList<>();

    public SurPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                Log.i("FragRahul", "createFragment: "+position);
                return new SurveyFormOneFragment();
            case 1:
                Log.i("FragRahul", "createFragment: "+position);
                return new SurveyFormTwoFragment();
            case 2:
                Log.i("FragRahul", "createFragment: "+position);
                return new SurveyFormThreeFragment();
            case 3:
                Log.i("FragRahul", "createFragment: "+position);
                return new SurveyFormFourFragment();
            case 4:
                Log.i("FragRahul", "createFragment: "+position);
                return new SurveyFormFiveFragment();
            default:

                return new SurveyFormOneFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

}
