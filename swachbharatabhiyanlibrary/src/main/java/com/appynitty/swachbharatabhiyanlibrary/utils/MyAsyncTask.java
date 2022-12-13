package com.appynitty.swachbharatabhiyanlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.riaylibrary.custom_component.MyProgressDialog;

public class MyAsyncTask extends AsyncTask {

    public SyncServer syncServer;
    private final Context context;
    private boolean isNetworkAvail = false;
    private final boolean isShowPrgressDialog;
    private MyProgressDialog myProgressDialog;
    private final AsynTaskListener asynTaskListener;


    public MyAsyncTask(Context context, boolean isShowPrgressDialog, AsynTaskListener asynTaskListener) {

        this.asynTaskListener = asynTaskListener;
        this.context = context;
        this.syncServer = new SyncServer(context);
        this.isShowPrgressDialog = isShowPrgressDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        myProgressDialog = new MyProgressDialog(context, R.drawable.progress_bar, false);
        if (!AUtils.isNull(myProgressDialog) && isShowPrgressDialog) {
            createOrHideDialog(false);
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        if (AUtils.isInternetAvailable()) {
            try {

                isNetworkAvail = true;
                asynTaskListener.doInBackgroundOpration(syncServer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (!AUtils.isNull(myProgressDialog) && myProgressDialog.isShowing()) {
                    createOrHideDialog(true);
                }
            }
        });
        Log.i("appynitty12", "onPostExecute: ");
        if (!AUtils.isNull(myProgressDialog) && myProgressDialog.isShowing()) {
            createOrHideDialog(true);
        }


        if (isNetworkAvail) {

            asynTaskListener.onFinished();
        } else {

            if (!AUtils.isNull(isShowPrgressDialog) && isShowPrgressDialog) {
                AUtils.warning(context, context.getString(R.string.no_internet_error));
                asynTaskListener.onInternetLost();
            }
        }
    }

    private void createOrHideDialog(boolean isHide) {
        if (!((Activity) context).isFinishing()) {
            try {
                if (isHide) {
                    if (myProgressDialog.isShowing()) myProgressDialog.dismiss();
                } else {
                    if (!myProgressDialog.isShowing()) myProgressDialog.show();
                }
            } catch (WindowManager.BadTokenException e) {
                Log.e("WindowManagerBad ", e.toString());
            }
        }
    }


    public interface AsynTaskListener {

        void doInBackgroundOpration(SyncServer syncServer);
        void onFinished();
        void onInternetLost();

    }
}