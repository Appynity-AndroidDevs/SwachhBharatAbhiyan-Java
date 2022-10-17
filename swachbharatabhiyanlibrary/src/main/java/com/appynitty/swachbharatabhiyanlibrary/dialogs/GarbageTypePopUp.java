package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyApplication;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GarbageTypePopUp extends Dialog {

    public Dialog d;
    private Executor executor = Executors.newSingleThreadExecutor();

    private final Context mContext;
    private final String mHouseId;

    private RadioButton rb_bifurcate, rb_mixed, rb_no;
    private EditText txtComent;
    private Button btnSubmit;

    private int mGarbageType = -1;
    private String mComment;

    private final GarbageTypePopUp.GarbagePopUpDialogListener mListener;

    private boolean isSubmit = false;

    public GarbageTypePopUp(Context context, String houseId, GarbageTypePopUp.GarbagePopUpDialogListener listener) {
        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;
        mHouseId = houseId;
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.garbage_type_dialog_layout);

        initComponents();
    }

    private void initComponents() {

        generateID();
        registerEvents();
        initData();
    }

    private void generateID() {

        rb_bifurcate = findViewById(R.id.rb_bifurcate_garbage);
        rb_mixed = findViewById(R.id.rb_mixed_garbage);
        rb_no = findViewById(R.id.rb_no_garbage);

        txtComent = findViewById(R.id.txt_garbage_comments);

        btnSubmit = findViewById(R.id.btn_garbage_submit);
    }

    private void initData() {

    }

    private void registerEvents() {

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                popUpDismiss();
            }
        });

        rb_bifurcate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mGarbageType = 1;
                }
            }
        });

        rb_mixed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mGarbageType = 0;
                }
            }
        });

        rb_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mGarbageType = 2;
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClick();
            }
        });
    }

    private void onSubmitClick() {
        mComment = txtComent.getText().toString();
        if (mGarbageType != -1) {
            this.dismiss();
            isSubmit = true;
        } else {
            AUtils.warning(mContext, mContext.getResources().getString(R.string.garbage_error));
        }
    }

    private void popUpDismiss() {

      //  Prefs.putString(AUtils.LAT, null);

        if (isSubmit) {
            if (!AUtils.isNull(Prefs.getString(AUtils.LAT, null)) && !Prefs.getString(AUtils.LAT, null).equals("")) {
                mListener.onGarbagePopUpDismissed(mHouseId, mGarbageType, mComment);
            } else {


                ((MyApplication) AUtils.mainApplicationConstant).startLocationTracking();
             //   AUtils.warning(mContext, mContext.getString(R.string.no_location_found_cant_save));
                ProgressDialog mProgressDialog = new ProgressDialog(mContext);
                mProgressDialog.setMessage(mContext.getResources().getString(R.string.fetching_location));
                mProgressDialog.show();

                executor.execute(new Runnable() {
                    @Override
                    public void run() {

                        boolean isLocationFound = false;
                        while (!isLocationFound) {
                            if (!AUtils.isNull(Prefs.getString(AUtils.LAT, null)) && !Prefs.getString(AUtils.LAT, null).equals("")) {
                                isLocationFound = true;

                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressDialog.dismiss();
                                        mListener.onGarbagePopUpDismissed(mHouseId, mGarbageType, mComment);
                                    }
                                });

                            }
                        }

                    }
                });


            }

        } else {
            mListener.onGarbagePopUpDismissed(mHouseId, -1, mComment);
        }
    }

    public interface GarbagePopUpDialogListener {
        void onGarbagePopUpDismissed(String houseID, int garbageType, @Nullable String comment);
    }

}
