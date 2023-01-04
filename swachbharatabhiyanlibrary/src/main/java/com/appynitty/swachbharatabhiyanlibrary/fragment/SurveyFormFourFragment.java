package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class SurveyFormFourFragment extends Fragment {

    private Context context;
    private View view;
    private ImageView imgMinusVote,imgPlusVote;
    private TextView txtNumVote;
    private String vote;
    private Integer o = 1;

    private CheckBox cbFbS,cbTwitSocial,cbInstagram,cbWhatsapp,cbLinkedIn,cbSnapChat,cbOtherS;
    public static ArrayList<String> socialMArray = new ArrayList<String>();

    private CheckBox cbAmazonS,cbFlifkartS,cbNaykkaS,cbTataCliqS,cbSnapS, cbOtherShopping;
    private static  ArrayList<String> shoppingArray = new ArrayList<>();

    private CheckBox cbBhimP,cbPaytmP,cbPhonPaP,cbGooglePeP,cbPersonalBP,cbOtherP;
    private static  ArrayList<String> paymentArray = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null){
            view = inflater.inflate(R.layout.fragment_survey_form_four, container, false);
            init();
        }
        return view;
    }

    private void init(){
        context = getActivity();

        //Vote
        imgMinusVote = view.findViewById(R.id.img_minus_vote);
        txtNumVote = view.findViewById(R.id.txt_num_vote);
        imgPlusVote = view.findViewById(R.id.img_plus_vote);

        o = Integer.parseInt(txtNumVote.getText().toString());
//Social Media
        cbTwitSocial = view.findViewById(R.id.cb_twitter_social);
        cbInstagram = view.findViewById(R.id.cb_instagram_social);
        cbFbS = view.findViewById(R.id.cb_fb_social);
        cbWhatsapp = view.findViewById(R.id.cb_whatsapp_social);
        cbLinkedIn = view.findViewById(R.id.cb_linkedin_social);
        cbSnapChat = view.findViewById(R.id.cb_snapchat_social);
        cbOtherS = view.findViewById(R.id.cb_other_social);
//Shopping
        cbAmazonS = view.findViewById(R.id.cb_amazon_shopping);
        cbFlifkartS = view.findViewById(R.id.cb_flipkart);
        cbNaykkaS = view.findViewById(R.id.cb_nykaa_shopping);
        cbTataCliqS = view.findViewById(R.id.cb_tata_cliq_shopping);
        cbSnapS = view.findViewById(R.id.cb_snapdeal_shopping);
        cbOtherShopping = view.findViewById(R.id.cb_other_shopping);
//Payment mode
        cbBhimP = view.findViewById(R.id.cb_bhim_online_payment);
        cbPaytmP = view.findViewById(R.id.cb_paytm_online_payment);
        cbPhonPaP = view.findViewById(R.id.cb_phone_pe_online_payment);
        cbGooglePeP = view.findViewById(R.id.cb_gpay_online_payment);
        cbPersonalBP = view.findViewById(R.id.cb_banking_online_payment);
        cbOtherP = view.findViewById(R.id.cb_other_online_payment);


        setOnClick();
    }

    private void setOnClick() {
        //Vote
        imgMinusVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Decreasing value...");

                if (o > 0) {
                    o = o - 1;
                    vote = String.valueOf(o);
                    txtNumVote.setText(vote);
                } else {
                    Log.d("src", "Value can't be less than 0");
                }
            }
        });

        imgPlusVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Increasing value...");
                o = o + 1;
                vote = String.valueOf(o);
                txtNumVote.setText(vote);
            }
        });
// Social Media
        cbFbS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    socialMArray.add(cbFbS.getText().toString());
                }else {
                    socialMArray.remove(cbFbS.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+socialMArray);
            }
        });
        cbTwitSocial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    socialMArray.add(cbTwitSocial.getText().toString());
                }else {
                    socialMArray.remove(cbTwitSocial.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+socialMArray);
            }
        });
        cbInstagram.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    socialMArray.add(cbInstagram.getText().toString());
                }else {
                    socialMArray.remove(cbInstagram.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+socialMArray);
            }
        });
        cbWhatsapp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    socialMArray.add(cbWhatsapp.getText().toString());
                }else {
                    socialMArray.remove(cbWhatsapp.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+socialMArray);
            }
        });
        cbSnapChat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    socialMArray.add(cbSnapChat.getText().toString());
                }else {
                    socialMArray.remove(cbSnapChat.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+socialMArray);
            }
        });
        cbLinkedIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    socialMArray.add(cbLinkedIn.getText().toString());
                }else {
                    socialMArray.remove(cbLinkedIn.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+socialMArray);
            }
        });
        cbOtherS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    socialMArray.add(cbOtherS.getText().toString());
                }else {
                    socialMArray.remove(cbOtherS.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+socialMArray);
            }
        });
//shopping
        cbAmazonS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    shoppingArray.add(cbAmazonS.getText().toString());
                }else {
                    shoppingArray.remove(cbAmazonS.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+shoppingArray);
            }
        });
        cbFlifkartS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    shoppingArray.add(cbFlifkartS.getText().toString());
                }else {
                    shoppingArray.remove(cbFlifkartS.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+shoppingArray);
            }
        });
        cbNaykkaS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    shoppingArray.add(cbNaykkaS.getText().toString());
                }else {
                    shoppingArray.remove(cbNaykkaS.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+shoppingArray);
            }
        });
        cbTataCliqS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    shoppingArray.add(cbTataCliqS.getText().toString());
                }else {
                    shoppingArray.remove(cbTataCliqS.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+shoppingArray);
            }
        });
        cbSnapS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    shoppingArray.add(cbSnapS.getText().toString());
                }else {
                    shoppingArray.remove(cbSnapS.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+shoppingArray);
            }
        });
        cbOtherShopping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    shoppingArray.add(cbOtherShopping.getText().toString());
                }else {
                    shoppingArray.remove(cbOtherShopping.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+shoppingArray);
            }
        });
//Payment Mode
        cbBhimP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    paymentArray.add(cbBhimP.getText().toString());
                }else {
                    paymentArray.remove(cbBhimP.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+paymentArray);
            }
        });

        cbPaytmP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    paymentArray.add(cbPaytmP.getText().toString());
                }else {
                    paymentArray.remove(cbPaytmP.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+paymentArray);
            }
        });
        cbPhonPaP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    paymentArray.add(cbPhonPaP.getText().toString());
                }else {
                    paymentArray.remove(cbPhonPaP.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+paymentArray);
            }
        });
        cbGooglePeP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    paymentArray.add(cbGooglePeP.getText().toString());
                }else {
                    paymentArray.remove(cbGooglePeP.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+paymentArray);
            }
        });
        cbPersonalBP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    paymentArray.add(cbPersonalBP.getText().toString());
                }else {
                    paymentArray.remove(cbPersonalBP.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+paymentArray);
            }
        });
        cbOtherP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    paymentArray.add(cbOtherP.getText().toString());
                }else {
                    paymentArray.remove(cbOtherP.getText().toString());
                }
                Log.i("Social", "onCheckedChanged: "+paymentArray);
            }
        });

    }


   /*if (checkbox.isChecked()) {
        assuranceArray.add(new Assurance("email",checkBox.getText().toString()));
    } else {
        for (Assurance assurance : assuranceArray) {
            if (assurance.getEmail().equals(checkBox.getText().toString())  {
                assuranceArray.remove(assurance);
                //You can exit the loop as you find a reference
                break;
            }
        }
    }*/

    /*public String getResStringLanguage(int id, String lang){
        //Get default locale to back it
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        Locale savedLocale = conf.locale;
        //Retrieve resources from desired locale
        Configuration confAr = getResources().getConfiguration();
        confAr.locale = new Locale(lang);
        DisplayMetrics metrics = new DisplayMetrics();
        Resources resources = new Resources(getAssets(), metrics, confAr);
        //Get string which you want
        String string = resources.getString(id);
        //Restore default locale
        conf.locale = savedLocale;
        res.updateConfiguration(conf, null);
        //return the string that you want
        return string;
    }

    String str = getResStringLanguage(R.string.any_string, "en");*/
}