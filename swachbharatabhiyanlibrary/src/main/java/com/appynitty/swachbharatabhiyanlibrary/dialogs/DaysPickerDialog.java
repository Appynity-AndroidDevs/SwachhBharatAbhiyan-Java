package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import com.appynitty.swachbharatabhiyanlibrary.R;

import java.util.Calendar;
/***
 * Created BY Rahul Rokade
 * Date : 3 Jan 2023
 * */
public class DaysPickerDialog extends DialogFragment {
    private static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener listener;
    private String day;

    public DaysPickerDialog(DatePickerDialog.OnDateSetListener listener, String day) {
        this.listener = listener;
        this.day = day;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();
       /* int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.i("Rahul", "onCreateDialog: "+daysInMonth);*/


        View dialog = inflater.inflate(R.layout.days_picker_dialog, null);
        final NumberPicker dayPicker = (NumberPicker) dialog.findViewById(R.id.picker_day);


        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);
        dayPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        dayPicker.setWrapSelectorWheel(true);
        dayPicker.setValue(cal.get(Calendar.DAY_OF_MONTH));


        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(Html.fromHtml(getString(R.string.str_ok)), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDateSet(null, 0, 0, dayPicker.getValue());
                    }
                })
                .setNegativeButton(Html.fromHtml(getString(R.string.str_cancel)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DaysPickerDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
