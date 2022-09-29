package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ListPopupWindow;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.AutocompleteContainSearch;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.DialogAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.VehicleNoListAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.VehicleNoListAdapterRepo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleNumberPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleTypePojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/****
 * Updated dialog popup by rahul rokade
 * vehicle number selection
 * */
public class PopUpDialog extends Dialog{

    private static final String TAG = "PopUpDialog";
    private final Context mContext;
    private final String mType;

    private LinearLayout liSelectVehicleNum;
    private TextInputLayout tiVehicle;
    private AutoCompleteTextView autoTxtVehicleNum;

    private ListView mItemList;

    private LinearLayout hiddenView;
    private EditText txtVehicleNo;
    private Button btnSubmit;
    private TextView lblTitle;
    private TextView txtVehicleNote;
    private DialogAdapter mAdapter;
    private final HashMap<Integer, Object> mList;
    private VehicleNoListAdapterRepo vehicleNoListAdapterRepo;
    private int vNumberListSize;
    private ProgressBar loader;

    private Object mReturnData;
    private ArrayList<VehicleNumberPojo> vehicleNumList;
    private List<VehicleNumberPojo> vehiNumList;
    private String mVehicleNo;
    private HashMap<String, String> areaHash;
    ArrayAdapter<String> adapterN;
    TextWatcher textWatcher;

    private String mVehicleId = "";
    //private Integer vNumListSize ;
    private static Integer vNumListSize = null;
    int num1;

    private final PopUpDialogListener mListener;

    private VehicleNumberDialog vehicleNumberDialog;

    public PopUpDialog(Context context, String type, HashMap<Integer, Object> list, PopUpDialogListener listener) {
        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;
        mType = type;
        mList = list;
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_layout);

        initComponents();
    }

    private void initComponents() {

        generateID();
        registerEvents();
        initData();
    }

    private void generateID() {

        lblTitle = findViewById(R.id.lbl_title);
        mItemList = findViewById(R.id.dialog_listview);
        txtVehicleNote = findViewById(R.id.txt_vehicle_note);

        hiddenView = findViewById(R.id.dialog_input_view);

        if(mType.equals(AUtils.DIALOG_TYPE_VEHICLE))
        {
            vehicleNumList = new ArrayList<>();

            txtVehicleNo = findViewById(R.id.txt_vehicle_no);
            tiVehicle = findViewById(R.id.ti_vehicle);
            loader = findViewById(R.id.progress_bar);
            liSelectVehicleNum = findViewById(R.id.li_selection_vehicle_num);
            autoTxtVehicleNum = findViewById(R.id.auto_select_vNumber);
            btnSubmit = findViewById(R.id.btn_submit);

        }

        if (mType.equals(AUtils.DIALOG_TYPE_LANGUAGE)){
            lblTitle.setText(mContext.getResources().getString(R.string.change_language));

            mAdapter = new DialogAdapter(mContext, mList, mType);

            mItemList.setAdapter(mAdapter);
        }
    }

    private void initData() {
        vehicleNoListAdapterRepo = VehicleNoListAdapterRepo.getInstance();

        if(mType.equals(AUtils.DIALOG_TYPE_VEHICLE))
        {
            lblTitle.setText(mContext.getResources().getString(R.string.dialog_title_txt_vehicle));

        }
        else
        {
            lblTitle.setText(mContext.getResources().getString(R.string.change_language));
        }

        mAdapter = new DialogAdapter(mContext, mList, mType);

        mItemList.setAdapter(mAdapter);

    }

    private void registerEvents() {

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                popUpDismiss();
            }
        });

        if (mType.equals(AUtils.DIALOG_TYPE_VEHICLE)){
            loader.setVisibility(View.VISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loader.setVisibility(View.VISIBLE);
                    VehicleTypePojo vehicleTypePojo = (VehicleTypePojo) mReturnData;
                    if (vehicleTypePojo != null){
                        mVehicleId = vehicleTypePojo.getVtId();
                    }else {
                       // Toast.makeText(mContext, "Vehicle type id not getting", Toast.LENGTH_SHORT).show();
                    }
                   // mVehicleId = vehicleTypePojo.getVtId();
                    vehicleNoListAdapterRepo.getVehicleNosList(Prefs.getString(AUtils.APP_ID, null), mVehicleId, new VehicleNoListAdapterRepo.IVehicleNoListListener() {
                        @Override
                        public void onResponse(List<VehicleNumberPojo> vehicleNumbersList) {
                            vehiNumList = vehicleNumbersList;
                            if (vehiNumList != null){
                                vNumListSize = vehicleNumbersList.size();
                                Log.e(TAG, "Vehicle number list: " + vNumListSize);
                                loader.setVisibility(View.VISIBLE);
                                listSize(vNumListSize);

                            }else {
                                loader.setVisibility(View.GONE);
                                tiVehicle.setVisibility(View.VISIBLE);
                                txtVehicleNote.setVisibility(View.GONE);
                                autoTxtVehicleNum.setVisibility(View.GONE);
                                Log.e(TAG, "Please Enter Manually: " + txtVehicleNo.getText().toString());
                            }

                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Log.e(TAG, "onFailure: " + throwable.getMessage());
                        }
                    });
                }
            }, 1000*9);


             textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    adapterN.getFilter().filter(charSequence.toString());

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };


        }


        mItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelected(position);
            }
        });
        if(!AUtils.isNull(btnSubmit))
        {
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSubmitClick();
                }
            });
        }
    }

    private void listSize(int vNumListSize){
        Log.e(TAG, "Vehicle number list: " + vNumListSize);
        if (vNumListSize > 0){
            loader.setVisibility(View.GONE);
            tiVehicle.setVisibility(View.GONE);
            txtVehicleNote.setVisibility(View.GONE);
            autoTxtVehicleNum.setVisibility(View.VISIBLE);

           /**
             * just show only list then select
             * added by Rahul Rokade
             */
            /*autoTxtVehicleNum.setFocusable(false);
            autoTxtVehicleNum.setFocusableInTouchMode(false);
            autoTxtVehicleNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (VehicleNumberPojo vehicleNumberPojo : vehiNumList) {
                        Log.e(TAG, "onResponse: " + vehicleNumberPojo.getVehicleNo());
                    }

                    showVehicleNoList(autoTxtVehicleNum, vehiNumList);
                }
            });*/

            /**
             * write with search list
             * added by Rahul Rokade
             */
            autoTxtVehicleNum.requestFocus();
            autoTxtVehicleNum.clearListSelection();
            inflateVehicleAutoComplete(vehiNumList);


        }else {
            loader.setVisibility(View.GONE);
            tiVehicle.setVisibility(View.VISIBLE);
            txtVehicleNote.setVisibility(View.GONE);
            autoTxtVehicleNum.setVisibility(View.GONE);
            Log.e(TAG, "Please Enter Manually: " + txtVehicleNo.getText().toString());
        }
    }
    private void itemSelected(int postion)
    {
         mReturnData = mList.get(postion);
         if(mType.equals(AUtils.DIALOG_TYPE_VEHICLE))
         {
             hiddenView.setVisibility(View.VISIBLE);
             mItemList.setVisibility(View.GONE);
         }
         else {
             this.dismiss();
         }
    }

    private void onSubmitClick()
    {
        mVehicleNo = txtVehicleNo.getText().toString();
        if(!mVehicleNo.isEmpty()){
            this.dismiss();
        }else {
            txtVehicleNo.setError(mContext.getString(R.string.noVehicleNo));
        }
    }

    private void vehicleNumberDialogOpen(){
        vehicleNumberDialog = new VehicleNumberDialog(mContext, new VehicleNumberDialog.VehicleNumberDialogInterface() {
            @Override
            public void onClicked(VehicleNumberPojo model) {
                autoTxtVehicleNum.setText(model.getVehicleNo());
            }
        });
        vehicleNumberDialog.show();
        vehicleNumberDialog.setCanceledOnTouchOutside(true);
    }



    private void popUpDismiss()
    {
        if(mType.equals(AUtils.DIALOG_TYPE_VEHICLE))
        {
            /*if(!AUtils.isNull(mListener))
            {
                mListener.onPopUpDismissed(mType, mReturnData,mVehicleNo);
            }*/

         /****** Rahul**********/
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!AUtils.isNull(mListener))
                    {
                        mListener.onPopUpDismissed(mType, mReturnData,mVehicleNo);
                    }
                }
            }, 1000*2);
        }
        else {
            if(!AUtils.isNull(mListener))
            {
                Log.e("dialog popup", "Vehicle type selected: " +mType);
                mListener.onPopUpDismissed(mType, mReturnData,"");
            }
        }
    }


    private void inflateVehicleAutoComplete(List<VehicleNumberPojo> pojoList) {

        areaHash = new HashMap<>();
        ArrayList<String> keyList = new ArrayList<>();
        for (VehicleNumberPojo pojo : pojoList) {
            areaHash.put(pojo.getVehicleNo().toLowerCase(Locale.ROOT), pojo.getVehicleId());
            keyList.add(pojo.getVehicleNo().trim());
        }

        AutocompleteContainSearch adapter = new AutocompleteContainSearch(mContext, android.R.layout.simple_dropdown_item_1line, keyList);

        adapterN = adapter;
        autoTxtVehicleNum.setThreshold(0);
        autoTxtVehicleNum.setAdapter(adapter);

        txtVehicleNo = autoTxtVehicleNum;
        if (!autoTxtVehicleNum.isFocused()) {
            autoTxtVehicleNum.requestFocus();
        }

    }



    private Boolean isAutoCompleteValid(AutoCompleteTextView autoCompleteTextView, HashMap<String, String> hashMap) {
        try {
            return hashMap.containsKey(autoCompleteTextView.getText().toString().toLowerCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private void showVehicleNoList(View textView, List<VehicleNumberPojo> mVehicleNoList) {

        final ListPopupWindow listPopupWindow =
                createVehicleNoPopup(textView, ViewGroup.LayoutParams.WRAP_CONTENT, mVehicleNoList);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listPopupWindow.dismiss();
                autoTxtVehicleNum.setText(mVehicleNoList.get(position).getVehicleNo());
                txtVehicleNo = autoTxtVehicleNum;
            }
        });
        listPopupWindow.show();
    }

    private ListPopupWindow createVehicleNoPopup(View textView, int width,
                                                 List<VehicleNumberPojo> items) {
        final ListPopupWindow popup = new ListPopupWindow(mContext);
        VehicleNoListAdapter adapter = new VehicleNoListAdapter(items);
        adapter.notifyDataSetChanged();
        popup.setAnchorView(textView);
        popup.setWidth(width);
        popup.setAdapter(adapter);
        return popup;
    }


    public interface PopUpDialogListener {
        void onPopUpDismissed(String type, Object listItemSelected, @Nullable String vehicleNo);

    }

}



