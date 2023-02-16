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
import com.appynitty.swachbharatabhiyanlibrary.pojos.GetApiResponseModel;
import com.appynitty.swachbharatabhiyanlibrary.pojos.GetSurveyResponsePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;

import java.util.ArrayList;
import java.util.List;
/***
 * Created BY Rahul Rokade
 * Date : 3 Jan 2023
 * */
public class SurPagerAdapter extends FragmentStateAdapter {

    private Context mContext;
    private ArrayList<Fragment> arrayList = new ArrayList<>();
    private GetApiResponseModel getApiResponseModel;
    public SurPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, GetApiResponseModel getApiResponseModel) {
        super(fragmentManager, lifecycle);
        this.getApiResponseModel = getApiResponseModel;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        boolean isValid = false;
        switch (position){
            case 0:
                Log.i("FragRahul", "createFragment: "+position);
                return new SurveyFormOneFragment(getApiResponseModel);
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
