package com.custom;

import android.app.DatePickerDialog;
import android.content.Context;

/**
 * Created by huisou on 2015/11/19.
 */
public class MyDatePickDialog extends DatePickerDialog {
    public MyDatePickDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
    }

    public MyDatePickDialog(Context context, int theme, OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, theme, listener, year, monthOfYear, dayOfMonth);
    }


    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
    }
}
