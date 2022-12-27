package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.appynitty.swachbharatabhiyanlibrary.R;

public class SurveyFormThreeFragment extends Fragment {
    private Context context;
    private View view;
    private ImageView imgMinusAdult,imgPlusAdult;
    private ImageView imgMinusChild,imgPlusChild;
    private ImageView imgMinusSCitizen,imgPlusSCitizen;
    private ImageView imgMinusTwoWheel,imgPlusTwoWheel;
    private ImageView imgMinusFourWheel,imgPlusFourWheel;

    private TextView txtNumAdult;
    private TextView txtNumChild;
    private TextView txtNumSCitizen;
    private TextView txtNumTwoWheel;
    private TextView txtNumFourWheel;
    private TextView txtNumTotalVehicle;

    private String adult, children, SCitizen, twoWheel, fourWheel, totalVehiclePlus, totalVehicleMinus;
    private Integer i = 1;
    private Integer j = 1;
    private Integer k = 1;
    private Integer l = 1;
    private Integer m = 1;
    private Integer n = 1;


    private CheckBox cbBusinessTypeYes, cbBusinessTypeNo;

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

        //Two Wheeler
        imgMinusTwoWheel = view.findViewById(R.id.img_minus_two_wheeler);
        txtNumTwoWheel = view.findViewById(R.id.txt_num_two_wheeler);
        imgPlusTwoWheel = view.findViewById(R.id.img_plus_two_wheeler);

        txtNumTotalVehicle = view.findViewById(R.id.txt_num_total_vehicle);

        //Four Wheeler
        imgMinusFourWheel = view.findViewById(R.id.img_minus_four_wheeler);
        txtNumFourWheel = view.findViewById(R.id.txt_num_four_wheeler);
        imgPlusFourWheel = view.findViewById(R.id.img_plus_four_wheeler);



        i = Integer.parseInt(txtNumAdult.getText().toString());
        j = Integer.parseInt(txtNumChild.getText().toString());
        k = Integer.parseInt(txtNumSCitizen.getText().toString());
        l = Integer.parseInt(txtNumTwoWheel.getText().toString());
        m = Integer.parseInt(txtNumFourWheel.getText().toString());
        n = Integer.parseInt(txtNumTotalVehicle.getText().toString());




        setOnClick();
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
                txtNumTotalVehicle.setText(twoWheel);
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
                    txtNumTotalVehicle.setText(fourWheel);
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
                txtNumTotalVehicle.setText(fourWheel);
            }
        });



    }

}