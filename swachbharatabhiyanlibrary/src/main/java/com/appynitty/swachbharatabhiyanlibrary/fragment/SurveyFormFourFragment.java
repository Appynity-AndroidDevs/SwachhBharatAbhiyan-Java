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
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
    private String strFaceBook, strTwitter,strInstagram,strWhatsApp,strSnapChat,strLinkedIn,strOtherSocial;

    private CheckBox cbAmazonS,cbFlifkartS,cbNaykkaS,cbTataCliqS,cbSnapS, cbOtherShopping;
    private static  ArrayList<String> shoppingArray = new ArrayList<>();

    private CheckBox cbBhimP,cbPaytmP,cbPhonPaP,cbGooglePeP,cbPersonalBP,cbOtherP;
    private static  ArrayList<String> paymentArray = new ArrayList<>();

    private String socialList,shoppingList,paymentList;
    String regex = "\\[|\\]";
    private List<String> selectedSocialMediaList = new ArrayList<>();
    private List<String> selectedShoppingList = new ArrayList<>();
    private List<String> selectedPaymentList = new ArrayList<>();
    String[] separatedSocialMedia,separatedShopping,separatedPaymentApp;





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
        strTwitter = getResStringLanguage(R.string.str_twitter, "en");
        cbInstagram = view.findViewById(R.id.cb_instagram_social);
        strInstagram = getResStringLanguage(R.string.str_instagram, "en");
        cbFbS = view.findViewById(R.id.cb_fb_social);
        strFaceBook = getResStringLanguage(R.string.str_facebook, "en");
        cbWhatsapp = view.findViewById(R.id.cb_whatsapp_social);
        strWhatsApp = getResStringLanguage(R.string.str_whatsapp, "en");
        cbLinkedIn = view.findViewById(R.id.cb_linkedin_social);
        strLinkedIn = getResStringLanguage(R.string.str_linkedin, "en");
        cbSnapChat = view.findViewById(R.id.cb_snapchat_social);
        strSnapChat = getResStringLanguage(R.string.str_snapchat, "en");
        cbOtherS = view.findViewById(R.id.cb_other_social);
        strOtherSocial = getResStringLanguage(R.string.str_other, "en");
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
        setFillData();
    }

    private void setFillData() {
        String totalVote = Prefs.getString(AUtils.PREFS.SUR_NUM_OF_PEOPLE_VOTE,"0");
        txtNumVote.setText(totalVote);
        String socialMedia = Prefs.getString(AUtils.PREFS.SUR_SOCIAL_MEDIA,"");
        if (socialMedia != null && !socialMedia.equals("")){
            separatedSocialMedia = socialMedia.split(",");
            Log.e(TAG, "socialMedia: "+separatedSocialMedia );
            selectedSocialMediaList.clear();
            for (int k =0 ; k<separatedSocialMedia.length; k++){
                selectedSocialMediaList.add(separatedSocialMedia.toString());
                String twit = separatedSocialMedia[k];
                Log.i("social", "socialDataAPI: "+twit);
                String inst = separatedSocialMedia[k];
                Log.i("social", "socialDataAPI: "+inst);
                String fb = separatedSocialMedia[k];
                Log.i("social", "socialDataAPI: "+fb);
                String whats = separatedSocialMedia[k];
                Log.i("social", "socialDataAPI: "+whats);
                String link = separatedSocialMedia[k];
                Log.i("social", "socialDataAPI: "+link);
                String snap = separatedSocialMedia[k];
                Log.i("social", "socialDataAPI: "+snap);
                String other = separatedSocialMedia[k];
                Log.i("social", "socialDataAPI: "+other);

                if (strFaceBook.equals(fb)){
                    cbFbS.setChecked(true);
                }else if (strInstagram.equals(inst)){
                    cbInstagram.setChecked(true);
                }else if (strLinkedIn.equals(link)){
                    cbLinkedIn.setChecked(true);
                }else if (strTwitter.equals(twit)){
                    cbTwitSocial.setChecked(true);
                }else if (strWhatsApp.equals(whats)){
                    cbWhatsapp.setChecked(true);
                }else if (strSnapChat.equals(snap)){
                    cbSnapChat.setChecked(true);
                }else if (strOtherSocial.equals(other)){
                    cbOtherS.setChecked(true);
                }

            }
        }
        String shopping = Prefs.getString(AUtils.PREFS.SUR_ONLINE_SHOPPING,"");
        String paymentApp = Prefs.getString(AUtils.PREFS.SUR_ONLINE_PAY_APP,"");
    }

    /*private ArrayList<String> getCheckBoxData(){
        ArrayList<String> checkedBox = new ArrayList<>();
        for (int a = 0; a < layout.getChildCount(); a++) {
            checkBox = (CheckBox) layout.getChildAt(a);
            if (checkBox.isChecked()) {
                checkedBox.add(checkBox.getText().toString());
            }
        }
        return checkedBox;
    }*/

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
                    Prefs.putString(AUtils.PREFS.SUR_NUM_OF_PEOPLE_VOTE,vote);
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
                Prefs.putString(AUtils.PREFS.SUR_NUM_OF_PEOPLE_VOTE,vote);
                Log.i("Social", "vote add: "+vote);
            }
        });
// Social Media
        cbFbS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){


                  //  socialMArray.add(cbFbS.getText().toString());
                    socialMArray.add(strFaceBook);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
                }else {
                  //  socialMArray.remove(cbFbS.getText().toString());
                    socialMArray.remove(strFaceBook);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
                }
            }
        });
        cbTwitSocial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                 //   socialMArray.add(cbTwitSocial.getText().toString());
                    socialMArray.add(strTwitter);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
                }else {
                 //   socialMArray.remove(cbTwitSocial.getText().toString());
                    socialMArray.remove(strTwitter);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
                }
            }
        });

        cbInstagram.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                 //   socialMArray.add(cbInstagram.getText().toString());
                    socialMArray.add(strInstagram);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
                }else {
                //    socialMArray.remove(cbInstagram.getText().toString());
                    socialMArray.remove(strInstagram);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
                }
            }
        });

        cbWhatsapp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    //socialMArray.add(cbWhatsapp.getText().toString());
                    socialMArray.add(strWhatsApp);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
                }else {
                 //   socialMArray.remove(cbWhatsapp.getText().toString());
                    socialMArray.remove(strWhatsApp);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
                }
            }
        });

        cbSnapChat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                 //   socialMArray.add(cbSnapChat.getText().toString());
                    socialMArray.add(strSnapChat);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
                }else {
                  //  socialMArray.remove(cbSnapChat.getText().toString());
                    socialMArray.remove(strSnapChat);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
                }
            }
        });

        cbLinkedIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                  //  socialMArray.add(cbLinkedIn.getText().toString());
                    socialMArray.add(strLinkedIn);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
                }else {
                   // socialMArray.remove(cbLinkedIn.getText().toString());
                    socialMArray.remove(strLinkedIn);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
                }
            }
        });

        cbOtherS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                   // socialMArray.add(cbOtherS.getText().toString());
                    socialMArray.add(strOtherSocial);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
                }else {
                 //   socialMArray.remove(cbOtherS.getText().toString());
                    socialMArray.remove(strOtherSocial);
                    String socialMode = socialMArray.toString().trim().replaceAll("\\s","");
                    socialList = socialMode.replaceAll(regex,"");
                    Log.i("Social", "socialMode: "+socialList);
                    Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,socialList);
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
                    shoppingList = shoppingModeMode.replaceAll(regex,"");
                    Log.i("Social", "shoppingMode: "+shoppingList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_SHOPPING,shoppingList);
                }else {
                    //shoppingArray.remove(cbAmazonS.getText().toString());
                    shoppingArray.remove(strAmazon);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    shoppingList = shoppingModeMode.replaceAll(regex,"");
                    Log.i("Social", "shoppingMode: "+shoppingList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_SHOPPING,shoppingList);
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
                    shoppingList = shoppingModeMode.replaceAll(regex,"");
                    Log.i("Social", "shoppingMode: "+shoppingList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_SHOPPING,shoppingList);
                }else {
                 //   shoppingArray.remove(cbFlifkartS.getText().toString());
                    shoppingArray.remove(strFlifkart);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    shoppingList = shoppingModeMode.replaceAll(regex,"");
                    Log.i("Social", "shoppingMode: "+shoppingList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_SHOPPING,shoppingList);
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
                    shoppingList = shoppingModeMode.replaceAll(regex,"");
                    Log.i("Social", "shoppingMode: "+shoppingList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_SHOPPING,shoppingList);
                }else {
                   // shoppingArray.remove(cbNaykkaS.getText().toString());
                    shoppingArray.remove(strNayka);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    shoppingList = shoppingModeMode.replaceAll(regex,"");
                    Log.i("Social", "shoppingMode: "+shoppingList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_SHOPPING,shoppingList);
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
                    shoppingList = shoppingModeMode.replaceAll(regex,"");
                    Log.i("Social", "shoppingMode: "+shoppingList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_SHOPPING,shoppingList);
                }else {
                //    shoppingArray.remove(cbTataCliqS.getText().toString());
                    shoppingArray.remove(strTataCliq);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    shoppingList = shoppingModeMode.replaceAll(regex,"");
                    Log.i("Social", "shoppingMode: "+shoppingList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_SHOPPING,shoppingList);
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
                    shoppingList = shoppingModeMode.replaceAll(regex,"");
                    Log.i("Social", "shoppingMode: "+shoppingList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_SHOPPING,shoppingList);
                }else {
                  //  shoppingArray.remove(cbSnapS.getText().toString());
                    shoppingArray.remove(strSnapdeal);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    shoppingList = shoppingModeMode.replaceAll(regex,"");
                    Log.i("Social", "shoppingMode: "+shoppingList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_SHOPPING,shoppingList);
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
                    shoppingList = shoppingModeMode.replaceAll(regex,"");
                    Log.i("Social", "shoppingMode: "+shoppingList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_SHOPPING,shoppingList);
                }else {
                  //  shoppingArray.remove(cbOtherShopping.getText().toString());
                    shoppingArray.remove(strOtherShop);
                    String shoppingModeMode = shoppingArray.toString().trim().replaceAll("\\s","");
                    shoppingList = shoppingModeMode.replaceAll(regex,"");
                    Log.i("Social", "shoppingMode: "+shoppingList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_SHOPPING,shoppingList);
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
                    paymentList = paymentMode.replaceAll(regex,"");
                    Log.i("Social", "paymentMode: "+paymentList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_PAY_APP,paymentList);
                }else {
                  //  paymentArray.remove(cbBhimP.getText().toString());
                    paymentArray.remove(strBhimP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    paymentList = paymentMode.replaceAll(regex,"");
                    Log.i("Social", "paymentMode: "+paymentList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_PAY_APP,paymentList);
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
                    paymentList = paymentMode.replaceAll(regex,"");
                    Log.i("Social", "paymentMode: "+paymentList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_PAY_APP,paymentList);
                }else {
                  //  paymentArray.remove(cbPaytmP.getText().toString());
                    paymentArray.remove(strPaytmP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    paymentList = paymentMode.replaceAll(regex,"");
                    Log.i("Social", "paymentMode: "+paymentList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_PAY_APP,paymentList);
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
                    paymentList = paymentMode.replaceAll(regex,"");
                    Log.i("Social", "paymentMode: "+paymentList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_PAY_APP,paymentList);
                }else {
                  //  paymentArray.remove(cbPhonPaP.getText().toString());
                    paymentArray.remove(strPhonePeP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    paymentList = paymentMode.replaceAll(regex,"");
                    Log.i("Social", "paymentMode: "+paymentList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_PAY_APP,paymentList);
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
                    paymentList = paymentMode.replaceAll(regex,"");
                    Log.i("Social", "paymentMode: "+paymentList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_PAY_APP,paymentList);
                }else {
                  //  paymentArray.remove(cbGooglePeP.getText().toString());
                    paymentArray.remove(strGooglePeP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    paymentList = paymentMode.replaceAll(regex,"");
                    Log.i("Social", "paymentMode: "+paymentList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_PAY_APP,paymentList);
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
                    paymentList = paymentMode.replaceAll(regex,"");
                    Log.i("Social", "paymentMode: "+paymentList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_PAY_APP,paymentList);
                }else {
                  //  paymentArray.remove(cbPersonalBP.getText().toString());
                    paymentArray.remove(strPersonalBP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    paymentList = paymentMode.replaceAll(regex,"");
                    Log.i("Social", "paymentMode: "+paymentList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_PAY_APP,paymentList);
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
                    paymentList = paymentMode.replaceAll(regex,"");
                    Log.i("Social", "paymentMode: "+paymentList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_PAY_APP,paymentList);
                }else {
                   // paymentArray.remove(cbOtherP.getText().toString());
                    paymentArray.remove(strOtherP);
                    String paymentMode = paymentArray.toString().trim().replaceAll("\\s","");
                    paymentList = paymentMode.replaceAll(regex,"");
                    Log.i("Social", "paymentMode: "+paymentList);
                    Prefs.putString(AUtils.PREFS.SUR_ONLINE_PAY_APP,paymentList);
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
    private boolean isValid(){
        return true;
    }

    public Boolean checkFromFourText(){
        if (isValid()){
            return true;
        }
        return false;
    }
}