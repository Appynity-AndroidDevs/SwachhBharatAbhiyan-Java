package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.DisplayMetrics;
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
import java.util.Locale;

public class SurveyFormFourFragment extends Fragment {

    private static final String TAG = "SurveyFormFourFragment";
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
                    Log.i("Social", "vote minus: "+vote);
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
                Log.i("Social", "vote add: "+vote);
            }
        });
// Social Media
        String strFaceBook = getResStringLanguage(R.string.str_facebook, "en");
        cbFbS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                  //  socialMArray.add(cbFbS.getText().toString());
                    socialMArray.add(strFaceBook);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }else {
                  //  socialMArray.remove(cbFbS.getText().toString());
                    socialMArray.remove(strFaceBook);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }
            }
        });
        String strTwitter = getResStringLanguage(R.string.str_twitter, "en");
        cbTwitSocial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                 //   socialMArray.add(cbTwitSocial.getText().toString());
                    socialMArray.add(strTwitter);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }else {
                 //   socialMArray.remove(cbTwitSocial.getText().toString());
                    socialMArray.remove(strTwitter);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }
            }
        });
        String strInstagram = getResStringLanguage(R.string.str_instagram, "en");
        cbInstagram.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                 //   socialMArray.add(cbInstagram.getText().toString());
                    socialMArray.add(strInstagram);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }else {
                //    socialMArray.remove(cbInstagram.getText().toString());
                    socialMArray.remove(strInstagram);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }
            }
        });
        String strWhatsApp = getResStringLanguage(R.string.str_whatsapp, "en");
        cbWhatsapp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    //socialMArray.add(cbWhatsapp.getText().toString());
                    socialMArray.add(strWhatsApp);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }else {
                 //   socialMArray.remove(cbWhatsapp.getText().toString());
                    socialMArray.remove(strWhatsApp);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }
            }
        });
        String strSnapChat = getResStringLanguage(R.string.str_snapchat, "en");
        cbSnapChat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                 //   socialMArray.add(cbSnapChat.getText().toString());
                    socialMArray.add(strSnapChat);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }else {
                  //  socialMArray.remove(cbSnapChat.getText().toString());
                    socialMArray.remove(strSnapChat);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }
            }
        });
        String strLinkedIn = getResStringLanguage(R.string.str_linkedin, "en");
        cbLinkedIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                  //  socialMArray.add(cbLinkedIn.getText().toString());
                    socialMArray.add(strLinkedIn);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }else {
                   // socialMArray.remove(cbLinkedIn.getText().toString());
                    socialMArray.remove(strLinkedIn);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }
            }
        });
        String strOtherSocial = getResStringLanguage(R.string.str_other, "en");
        cbOtherS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                   // socialMArray.add(cbOtherS.getText().toString());
                    socialMArray.add(strOtherSocial);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }else {
                 //   socialMArray.remove(cbOtherS.getText().toString());
                    socialMArray.remove(strOtherSocial);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "socialMode: "+socialMode);
                }
            }
        });
//shopping
        String strAmazon = getResStringLanguage(R.string.str_amazon, "en");
        cbAmazonS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    //shoppingArray.add(cbAmazonS.getText().toString());
                    shoppingArray.add(strAmazon);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "shoppingMode: "+shoppingModeMode);
                }else {
                    //shoppingArray.remove(cbAmazonS.getText().toString());
                    shoppingArray.remove(strAmazon);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "shoppingMode: "+shoppingModeMode);
                }
            }
        });
        String strFlifkart = getResStringLanguage(R.string.str_flipkart, "en");
        cbFlifkartS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                   // shoppingArray.add(cbFlifkartS.getText().toString());
                    shoppingArray.add(strFlifkart);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "shoppingMode: "+shoppingModeMode);
                }else {
                 //   shoppingArray.remove(cbFlifkartS.getText().toString());
                    shoppingArray.remove(strFlifkart);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "shoppingMode: "+shoppingModeMode);
                }
            }
        });
        String strNayka = getResStringLanguage(R.string.str_nykka, "en");
        cbNaykkaS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
              //      shoppingArray.add(cbNaykkaS.getText().toString());
                    shoppingArray.add(strNayka);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "shoppingMode: "+shoppingModeMode);
                }else {
                   // shoppingArray.remove(cbNaykkaS.getText().toString());
                    shoppingArray.remove(strNayka);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "shoppingMode: "+shoppingModeMode);
                }
            }
        });
        String strTataCliq = getResStringLanguage(R.string.str_tata_cliq, "en");
        cbTataCliqS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
               //     shoppingArray.add(cbTataCliqS.getText().toString());
                    shoppingArray.add(strTataCliq);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "shoppingMode: "+shoppingModeMode);
                }else {
                //    shoppingArray.remove(cbTataCliqS.getText().toString());
                    shoppingArray.remove(strTataCliq);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "shoppingMode: "+shoppingModeMode);
                }
            }
        });
        String strSnapdeal = getResStringLanguage(R.string.str_snapdeal, "en");
        cbSnapS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                //    shoppingArray.add(cbSnapS.getText().toString());
                    shoppingArray.add(strSnapdeal);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "shoppingMode: "+shoppingModeMode);
                }else {
                  //  shoppingArray.remove(cbSnapS.getText().toString());
                    shoppingArray.remove(strSnapdeal);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "shoppingMode: "+shoppingModeMode);
                }
            }
        });
        String strOtherShop = getResStringLanguage(R.string.str_other, "en");
        cbOtherShopping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                //    shoppingArray.add(cbOtherShopping.getText().toString());
                    shoppingArray.add(strOtherShop);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "shoppingMode: "+shoppingModeMode);
                }else {
                  //  shoppingArray.remove(cbOtherShopping.getText().toString());
                    shoppingArray.remove(strOtherShop);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "shoppingMode: "+shoppingModeMode);
                }
            }
        });
//Payment Mode
        String strBhimP = getResStringLanguage(R.string.str_bhim, "en");
        cbBhimP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                //    paymentArray.add(cbBhimP.getText().toString());
                    paymentArray.add(strBhimP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "paymentMode: "+paymentMode);
                }else {
                  //  paymentArray.remove(cbBhimP.getText().toString());
                    paymentArray.remove(strBhimP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "paymentMode: "+paymentMode);
                }
            }
        });

        String strPaytmP = getResStringLanguage(R.string.str_paytm, "en");
        cbPaytmP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                   // paymentArray.add(cbPaytmP.getText().toString());
                    paymentArray.add(strPaytmP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "paymentMode: "+paymentMode);
                }else {
                  //  paymentArray.remove(cbPaytmP.getText().toString());
                    paymentArray.remove(strPaytmP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "paymentMode: "+paymentMode);
                }
            }
        });
        String strPhonePeP = getResStringLanguage(R.string.str_phonepe, "en");
        cbPhonPaP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                  //  paymentArray.add(cbPhonPaP.getText().toString());
                    paymentArray.add(strPhonePeP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "paymentMode: "+paymentMode);
                }else {
                  //  paymentArray.remove(cbPhonPaP.getText().toString());
                    paymentArray.remove(strPhonePeP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "paymentMode: "+paymentMode);
                }
            }
        });
        String strGooglePeP = getResStringLanguage(R.string.str_google_pay, "en");
        cbGooglePeP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                   // paymentArray.add(cbGooglePeP.getText().toString());
                    paymentArray.add(strGooglePeP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "paymentMode: "+paymentMode);
                }else {
                  //  paymentArray.remove(cbGooglePeP.getText().toString());
                    paymentArray.remove(strGooglePeP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "paymentMode: "+paymentMode);
                }
            }
        });
        String strPersonalBP = getResStringLanguage(R.string.str_personal_banking, "en");
        cbPersonalBP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                   // paymentArray.add(cbPersonalBP.getText().toString());
                    paymentArray.add(strPersonalBP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "paymentMode: "+paymentMode);
                }else {
                  //  paymentArray.remove(cbPersonalBP.getText().toString());
                    paymentArray.remove(strPersonalBP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "paymentMode: "+paymentMode);
                }
            }
        });
        String strOtherP = getResStringLanguage(R.string.str_other, "en");
        cbOtherP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                   // paymentArray.add(cbOtherP.getText().toString());
                    paymentArray.add(strOtherP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "paymentMode: "+paymentMode);
                }else {
                   // paymentArray.remove(cbOtherP.getText().toString());
                    paymentArray.remove(strOtherP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    Log.i("Social", "paymentMode: "+paymentMode);
                }
            }
        });

    }

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

}