package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.appynitty.swachbharatabhiyanlibrary.R;

import java.util.Calendar;
/***
 * Created BY Rahul Rokade
 * Date : 3 Jan 2023
 * */
public class MonthYearPickerDialog extends DialogFragment {
    private static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener listener;
    private String year;

    public MonthYearPickerDialog(DatePickerDialog.OnDateSetListener listener,String year) {
        this.listener = listener;
        this.year = year;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.year_picker_dialog, null);
        final TextView txtDialogTitle = (TextView) dialog.findViewById(R.id.txt_title_dialog);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setVisibility(View.VISIBLE);
        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(year);
        yearPicker.setWrapSelectorWheel(true);
        yearPicker.setValue(year);

        builder.setView(dialog).setPositiveButton(Html.fromHtml(getString(R.string.str_ok)), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                listener.onDateSet(null, yearPicker.getValue(),0, 0);

            }
        }).setNegativeButton(Html.fromHtml(getString(R.string.str_cancel)), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MonthYearPickerDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
