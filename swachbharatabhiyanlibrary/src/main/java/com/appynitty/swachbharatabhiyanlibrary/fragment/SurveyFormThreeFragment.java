package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Locale;
/***
 * Created BY Rahul Rokade
 * Date : 3 Jan 2023
 * */
public class SurveyFormThreeFragment extends Fragment {
    private static final String TAG = "SurveyFormThreeFragment";
    private Context context;
    private View view;
    private ImageView imgMinusAdult,imgPlusAdult;
    private ImageView imgMinusChild,imgPlusChild;
    private ImageView imgMinusSCitizen,imgPlusSCitizen;
    private ImageView imgMinusTwoWheel,imgPlusTwoWheel;
    private ImageView imgMinusFourWheel,imgPlusFourWheel;
    private LinearLayout liAvailable;

    private TextView txtNumAdult;
    private TextView txtNumChild;
    private TextView txtNumSCitizen;
    private TextView txtNumTwoWheel;
    private TextView txtNumFourWheel;
    private TextView txtNumTotalVehicle;
    private TextView txtNumTotalMember;

    private String adult, children, SCitizen, totalMemberPlus, totalMemberMinus;
    private Integer i = 1;
    private Integer j = 1;
    private Integer k = 1;
    private Integer r = 1;

    private String twoWheel, fourWheel, totalVehiclePlus, totalVehicleMinus;
    private Integer l = 1;
    private Integer m = 1;
    private Integer n = 1;


    private CheckBox cbBusinessTypeYes, cbBusinessTypeNo;
    private CheckBox[] chkArrayBusinessType;
    private CheckBox cbLandA,cbShopA,cbOtherA;
    private CheckBox[] chkArrayAvailable;
    private CheckBox cbYesOCity,cbNoOCity;
    private CheckBox[] chkArrayOtherCity;

    String yes,no;
    String yesOtherC,noOtherC;
    String land,shop,other;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null){
            view = inflater.inflate(R.layout.fragment_survey_form_three, container, false);
            init();
        }
        return view;
    }

    private void init(){
        context = getActivity();

        //adult
        imgMinusAdult = view.findViewById(R.id.img_minus_adult);
        txtNumAdult = view.findViewById(R.id.txt_num_adult);
        imgPlusAdult = view.findViewById(R.id.img_plus_adult);
        //children
        imgMinusChild = view.findViewById(R.id.img_child_minus);
        txtNumChild = view.findViewById(R.id.txt_num_child);
        imgPlusChild = view.findViewById(R.id.img_child_plus);
        //Senior Citizen
        imgMinusSCitizen = view.findViewById(R.id.img_minus_citizen);
        txtNumSCitizen = view.findViewById(R.id.txt_num_citizen);
        imgPlusSCitizen = view.findViewById(R.id.img_plus_citizen);

        txtNumTotalMember = view.findViewById(R.id.txt_num_total_mem);

        //Two Wheeler
        imgMinusTwoWheel = view.findViewById(R.id.img_minus_two_wheeler);
        txtNumTwoWheel = view.findViewById(R.id.txt_num_two_wheeler);
        imgPlusTwoWheel = view.findViewById(R.id.img_plus_two_wheeler);

        txtNumTotalVehicle = view.findViewById(R.id.txt_num_total_vehicle);

        //Four Wheeler
        imgMinusFourWheel = view.findViewById(R.id.img_minus_four_wheeler);
        txtNumFourWheel = view.findViewById(R.id.txt_num_four_wheeler);
        imgPlusFourWheel = view.findViewById(R.id.img_plus_four_wheeler);


        //total family member
        i = Integer.parseInt(txtNumAdult.getText().toString());
        j = Integer.parseInt(txtNumChild.getText().toString());
        k = Integer.parseInt(txtNumSCitizen.getText().toString());
        r = Integer.parseInt(txtNumSCitizen.getText().toString());

        //total vehicle
        l = Integer.parseInt(txtNumTwoWheel.getText().toString());
        m = Integer.parseInt(txtNumFourWheel.getText().toString());
        n = Integer.parseInt(txtNumTotalVehicle.getText().toString());
//Business Type
        cbBusinessTypeYes = view.findViewById(R.id.cb_business_yes);
        yes = getResStringLanguage(R.string.str_yes,"en");
        cbBusinessTypeNo = view.findViewById(R.id.cb_business_no);
        no = getResStringLanguage(R.string.str_no,"en");

        chkArrayBusinessType = new CheckBox[2];
        chkArrayBusinessType[0] = cbBusinessTypeYes;
        chkArrayBusinessType[0].setOnClickListener(mListenerBusinessType);
        chkArrayBusinessType[1] = cbBusinessTypeNo;
        chkArrayBusinessType[1].setOnClickListener(mListenerBusinessType);
//Available

        liAvailable = view.findViewById(R.id.li_business_available);
        liAvailable.setVisibility(View.GONE);
        cbLandA = view.findViewById(R.id.cb_land_available);
        land = getResStringLanguage(R.string.str_land,"en");
        cbShopA = view.findViewById(R.id.cb_shop_available);
        shop = getResStringLanguage(R.string.str_shop,"en");
        cbOtherA = view.findViewById(R.id.cb_other_available);
        other = getResStringLanguage(R.string.str_other,"en");

        chkArrayAvailable = new CheckBox[3];
        chkArrayAvailable[0] = cbLandA;
        chkArrayAvailable[0].setOnClickListener(mListenerAvailable);
        chkArrayAvailable[1] = cbShopA;
        chkArrayAvailable[1].setOnClickListener(mListenerAvailable);
        chkArrayAvailable[2] = cbOtherA;
        chkArrayAvailable[2].setOnClickListener(mListenerAvailable);
//other city
        cbYesOCity = view.findViewById(R.id.cb_yes_member_other_city);
        yesOtherC = getResStringLanguage(R.string.str_yes,"en");
        cbNoOCity = view.findViewById(R.id.cb_no_member_other_city);
        noOtherC = getResStringLanguage(R.string.str_no,"en");

        chkArrayOtherCity = new CheckBox[2];
        chkArrayOtherCity[0] = cbYesOCity;
        chkArrayOtherCity[0].setOnClickListener(mListenerOtherCity);
        chkArrayOtherCity[1] = cbNoOCity;
        chkArrayOtherCity[1].setOnClickListener(mListenerOtherCity);

        setOnClick();
        setFillData();
    }

    private void setFillData() {
        String totalMember = Prefs.getString(AUtils.PREFS.SUR_TOTAL_MEMBER,"0");
        txtNumTotalMember.setText(totalMember);
        String adult = Prefs.getString(AUtils.PREFS.SUR_TOTAL_ADULT,"0");
        txtNumAdult.setText(adult);
        String children = Prefs.getString(AUtils.PREFS.SUR_TOTAL_CHILDREN,"0");
        txtNumChild.setText(children);
        String citizen = Prefs.getString(AUtils.PREFS.SUR_TOTAL_CITIZEN,"0");
        txtNumSCitizen.setText(citizen);
        String willingStart = Prefs.getString(AUtils.PREFS.SUR_WILLING_START,"0");
        if (willingStart.equals(String.valueOf(true))){
            cbBusinessTypeYes.setChecked(true);
        }else if (willingStart.equals(String.valueOf(false))){
            cbBusinessTypeNo.setChecked(true);
        }
        String resourceA = Prefs.getString(AUtils.PREFS.SUR_RESOURCE_AVAILABLE,"");
        if (resourceA.equals(land)){
            cbLandA.setChecked(true);
        }else if (resourceA.equals(shop)){
            cbShopA.setChecked(true);
        }else if (resourceA.equals(other)){
            cbOtherA.setChecked(true);
        }
        String otherCity = Prefs.getString(AUtils.PREFS.SUR_MEMBER_JOB_OTHER_CITY,"");
        if (otherCity.equals(String.valueOf(true))){
            cbYesOCity.setChecked(true);
        }else if (otherCity.equals(String.valueOf(false))){
            cbNoOCity.setChecked(true);
        }
        String totalVehicle = Prefs.getString(AUtils.PREFS.SUR_NUM_OF_VEHICLE,"0");
        txtNumTotalVehicle.setText(totalVehicle);
        String twoWheeler = Prefs.getString(AUtils.PREFS.SUR_TWO_WHEELER_QTY,"0");
        txtNumTwoWheel.setText(twoWheeler);
        String fourWheeler = Prefs.getString(AUtils.PREFS.SUR_FOUR_WHEELER_QTY,"0");
        txtNumFourWheel.setText(fourWheeler);
    }

    private void setOnClick(){

        //adult
        imgMinusAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Decreasing value...");

                if (i > 0) {
                    i = i - 1;
                    adult = String.valueOf(i);
                    txtNumAdult.setText(adult);
                    Log.i("Social", "adult minus: "+adult);
                    Prefs.putString(AUtils.PREFS.SUR_TOTAL_ADULT,adult);
                    r = i + j + k;
                    totalMemberMinus = String.valueOf(r);
                    txtNumTotalMember.setText( totalMemberMinus.replace("-","").trim());
                    Prefs.putString(AUtils.PREFS.SUR_TOTAL_MEMBER,totalMemberMinus.replace("-","").trim());
                    Log.i("Social", "total Member Minus: "+totalMemberMinus.replace("-","").trim());
                } else {
                    Log.d("src", "Value can't be less than 0");
                }
            }
        });

        imgPlusAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Increasing value...");
                i = i + 1;
                adult = String.valueOf(i);
                txtNumAdult.setText(adult);
                Log.i("Social", "adult add: "+adult);
                Prefs.putString(AUtils.PREFS.SUR_TOTAL_ADULT,adult);
                r = i + j + k;
                totalMemberPlus = String.valueOf(r);
                txtNumTotalMember.setText( totalMemberPlus);
                Prefs.putString(AUtils.PREFS.SUR_TOTAL_MEMBER,totalMemberPlus);
                Log.i("Social", "total Member Plus: "+totalMemberPlus);
            }
        });
   //children
        imgMinusChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Decreasing value...");

                if (j > 0) {
                    j = j - 1;
                    children = String.valueOf(j);
                    txtNumChild.setText(children);
                    Log.i("Social", "children minus: "+children);
                    Prefs.putString(AUtils.PREFS.SUR_TOTAL_CHILDREN,children);

                    r = i + j + k;
                    totalMemberMinus = String.valueOf(r);
                    txtNumTotalMember.setText( totalMemberMinus.replace("-","").trim());
                    Prefs.putString(AUtils.PREFS.SUR_TOTAL_MEMBER,totalMemberMinus.replace("-","").trim());
                    Log.i("Social", "total Member Minus: "+totalMemberMinus.replace("-","").trim());
                } else {
                    Log.d("src", "Value can't be less than 0");
                }
            }
        });

        imgPlusChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Increasing value...");
                j = j + 1;
                children = String.valueOf(j);
                txtNumChild.setText(children);
                Log.i("Social", "children add: "+children);
                Prefs.putString(AUtils.PREFS.SUR_TOTAL_CHILDREN,children);

                r = i + j + k;
                totalMemberPlus = String.valueOf(r);
                txtNumTotalMember.setText( totalMemberPlus);
                Prefs.putString(AUtils.PREFS.SUR_TOTAL_MEMBER,totalMemberPlus);
                Log.i("Social", "total Member Plus: "+totalMemberPlus);
            }
        });
    //Senior Citizen
        imgMinusSCitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Decreasing value...");

                if (k > 0) {
                    k = k - 1;
                    SCitizen = String.valueOf(k);
                    txtNumSCitizen.setText(SCitizen);
                    Log.i("Social", "minus SCitizen: "+SCitizen);
                    Prefs.putString(AUtils.PREFS.SUR_TOTAL_CITIZEN,SCitizen);

                    r = i + j + k;
                    totalMemberMinus = String.valueOf(r);
                    txtNumTotalMember.setText( totalMemberMinus.replace("-","").trim());
                    Prefs.putString(AUtils.PREFS.SUR_TOTAL_MEMBER,totalMemberMinus.replace("-","").trim());
                    Log.i("Social", "total Member Minus: "+totalMemberMinus.replace("-","").trim());
                } else {
                    Log.d("src", "Value can't be less than 0");
                }
            }
        });

        imgPlusSCitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Increasing value...");
                k = k + 1;
                SCitizen = String.valueOf(k);
                txtNumSCitizen.setText(SCitizen);
                Log.i("Social", "SCitizen: "+SCitizen);
                Prefs.putString(AUtils.PREFS.SUR_TOTAL_CITIZEN,SCitizen);

                r = i + j + k;
                totalMemberPlus = String.valueOf(r);
                txtNumTotalMember.setText( totalMemberPlus);
                Prefs.putString(AUtils.PREFS.SUR_TOTAL_MEMBER,totalMemberPlus);
                Log.i("Social", "totalMemberPlus: "+totalMemberPlus);
            }
        });

    //Two Wheeler
        imgMinusTwoWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Decreasing value...");

                if (l > 0) {
                    l = l - 1;
                    twoWheel = String.valueOf(l);
                    txtNumTwoWheel.setText(twoWheel);
                    txtNumTotalVehicle.setText(twoWheel);
                    Log.i("Social", "minus twoWheel: "+twoWheel);
                    Prefs.putString(AUtils.PREFS.SUR_TWO_WHEELER_QTY,twoWheel);
                    n = l + m;
                    totalVehicleMinus = String.valueOf(n);
                    txtNumTotalVehicle.setText( totalVehicleMinus.replace("-","").trim());
                    Prefs.putString(AUtils.PREFS.SUR_NUM_OF_VEHICLE,totalVehicleMinus.replace("-","").trim());
                    Log.i("Social", "totalVehicleMinus: "+totalVehicleMinus.replace("-","").trim());

                } else {
                    Log.d("src", "Value can't be less than 0");
                }
            }
        });

        imgPlusTwoWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Increasing value...");
                l = l + 1;
                twoWheel = String.valueOf(l);
                txtNumTwoWheel.setText(twoWheel);
                Log.i("Social", "twoWheel: "+twoWheel);
                Prefs.putString(AUtils.PREFS.SUR_TWO_WHEELER_QTY,twoWheel);

                n = m + l;
                totalVehiclePlus = String.valueOf(n);
                txtNumTotalVehicle.setText( totalVehiclePlus);
                Prefs.putString(AUtils.PREFS.SUR_NUM_OF_VEHICLE,totalVehiclePlus);
                Log.i("Social", "Add totalVehiclePlus: "+totalVehiclePlus);
            }
        });

        //Four Wheeler
        imgMinusFourWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Decreasing value...");

                if (m > 0) {
                    m = m - 1;
                    fourWheel = String.valueOf(m);
                    txtNumFourWheel.setText(fourWheel);
                    Log.i("Social", "minus fourWheel: "+fourWheel);
                    txtNumTotalVehicle.setText(fourWheel);
                    Prefs.putString(AUtils.PREFS.SUR_FOUR_WHEELER_QTY,fourWheel);

                    n = m + l;
                    totalVehicleMinus = String.valueOf(n);
                    txtNumTotalVehicle.setText( totalVehicleMinus.replace("-","").trim());
                    Prefs.putString(AUtils.PREFS.SUR_NUM_OF_VEHICLE,totalVehicleMinus.replace("-","").trim());
                    Log.i("Social", "totalVehicleMinus: "+totalVehicleMinus.replace("-","").trim());
                } else {
                    Log.d("src", "Value can't be less than 0");
                }
            }
        });

        imgPlusFourWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Increasing value...");
                m = m + 1;
                fourWheel = String.valueOf(m);
                txtNumFourWheel.setText(fourWheel);
                Log.i("Social", "fourWheel: "+fourWheel);
                Prefs.putString(AUtils.PREFS.SUR_FOUR_WHEELER_QTY,fourWheel);

                n = m + l;
                totalVehiclePlus = String.valueOf(n);
                txtNumTotalVehicle.setText( totalVehiclePlus);
                Prefs.putString(AUtils.PREFS.SUR_NUM_OF_VEHICLE,totalVehiclePlus);
                Log.i("Social", "total Vehicle Plus: "+totalVehiclePlus);
            }
        });

    }

    private View.OnClickListener mListenerBusinessType = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            final String checkedValue = v.toString();
            for (int i = 0; i < chkArrayBusinessType.length; i++) {
                final CheckBox current = chkArrayBusinessType[i];
                if (current.getId() == checkedId) {
                    CheckBox checkBoxBusinessType = view.findViewById(current.getId());
                    String cbValueBusinessType = checkBoxBusinessType.getText().toString();
                    if (checkedId == R.id.cb_business_yes){
                        cbValueBusinessType = yes;
                        Log.i("Social", "onClick: "+cbValueBusinessType);
                        liAvailable.setVisibility(View.VISIBLE);
                        // Prefs.putString(AUtils.PREFS.SUR_WILLING_START,cbValueBusinessType);

                    }else if (checkedId == R.id.cb_business_no){
                        cbValueBusinessType = no;
                        Log.i("Social", "onClick: "+cbValueBusinessType);
                        // Prefs.putString(AUtils.PREFS.SUR_WILLING_START,cbValueBusinessType);
                        liAvailable.setVisibility(View.GONE);
                    }
                    Prefs.putString(AUtils.PREFS.SUR_WILLING_START,String.valueOf(false));
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                    Prefs.putString(AUtils.PREFS.SUR_WILLING_START,String.valueOf(true));
                }
            }
        }
    };

    private View.OnClickListener mListenerAvailable = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            final String checkedValue = v.toString();
            for (int i = 0; i < chkArrayAvailable.length; i++) {
                final CheckBox current = chkArrayAvailable[i];
                if (current.getId() == checkedId) {
                    CheckBox checkBoxAvailable = view.findViewById(current.getId());
                    String cbValueAvailable = checkBoxAvailable.getText().toString();
                    if (checkedId == R.id.cb_land_available){
                        cbValueAvailable = land;
                        Log.i("Social", "onClick: "+cbValueAvailable);
                        Prefs.putString(AUtils.PREFS.SUR_RESOURCE_AVAILABLE,cbValueAvailable);
                    }else if (checkedId == R.id.cb_shop_available){
                        cbValueAvailable = shop;
                        Log.i("Social", "onClick: "+cbValueAvailable);
                        Prefs.putString(AUtils.PREFS.SUR_RESOURCE_AVAILABLE,cbValueAvailable);
                    }else if (checkedId == R.id.cb_other_available){
                        cbValueAvailable = other;
                        Log.i("Social", "onClick: "+cbValueAvailable);
                        Prefs.putString(AUtils.PREFS.SUR_RESOURCE_AVAILABLE,cbValueAvailable);
                    }
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }
            }
        }
    };

    private View.OnClickListener mListenerOtherCity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            final String checkedValue = v.toString();
            for (int i = 0; i < chkArrayOtherCity.length; i++) {
                final CheckBox current = chkArrayOtherCity[i];
                if (current.getId() == checkedId) {
                    CheckBox checkBoxOtherCity = view.findViewById(current.getId());
                    String cbValueOtherCity = checkBoxOtherCity.getText().toString();
                    if (checkedId == R.id.cb_yes_member_other_city){
                        cbValueOtherCity = yesOtherC;
                        Log.i("Social", "onClick: "+cbValueOtherCity);
                        // Prefs.putString(AUtils.PREFS.SUR_MEMBER_JOB_OTHER_CITY,cbValueOtherCity);
                    }else if (checkedId == R.id.cb_no_member_other_city){
                        cbValueOtherCity = noOtherC;
                        Log.i("Social", "onClick: "+cbValueOtherCity);
                        // Prefs.putString(AUtils.PREFS.SUR_MEMBER_JOB_OTHER_CITY,cbValueOtherCity);
                    }

                    Prefs.putString(AUtils.PREFS.SUR_MEMBER_JOB_OTHER_CITY,String.valueOf(false));
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                    Prefs.putString(AUtils.PREFS.SUR_MEMBER_JOB_OTHER_CITY,String.valueOf(true));
                }
            }
        }
    };

    public String getResStringLanguage(int id, String lang){
        //Get default locale to back it
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        Locale savedLocale = conf.locale;
        //Retrieve resources from desired locale
        Configuration confAr = getResources().getConfiguration();
        confAr.locale = new Locale(lang);
        DisplayMetrics metrics = new DisplayMetrics();
        Resources resources = new Resources(getResources().getAssets(), metrics, confAr);
        //Get string which you want
        String string = resources.getString(id);
        //Restore default locale
        conf.locale = savedLocale;
        res.updateConfiguration(conf, null);
        //return the string that you want
        return string;
    }

    private boolean isValid(){
        return true;
    }

    public Boolean checkFromThreeText(){
        if (isValid()){
            return true;
        }
        return false;
    }
}