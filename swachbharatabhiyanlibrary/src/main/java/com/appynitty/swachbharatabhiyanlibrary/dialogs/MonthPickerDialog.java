package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import com.appynitty.swachbharatabhiyanlibrary.R;

import java.util.Calendar;

public class MonthPickerDialog extends DialogFragment {
    private static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener listener;
    private String month;

    public MonthPickerDialog(DatePickerDialog.OnDateSetListener listener, String month) {
        this.listener = listener;
        this.month = month;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.month_picker_dialog, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);


        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        monthPicker.setWrapSelectorWheel(true);
        monthPicker.setValue(cal.get(Calendar.MONTH)+1);


        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(Html.fromHtml(getString(R.string.str_ok)), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDateSet(null, 0, monthPicker.getValue(), 0);
                        month = String.valueOf(monthPicker.getValue());
                    }
                })
                .setNegativeButton(Html.fromHtml(getString(R.string.str_cancel)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MonthPickerDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
