package com.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custom.MyDatePickDialog;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.xzdz.R;

import java.util.Calendar;

/**
 * Created by huisou on 2015/11/2.
 * 预约量体
 */
public class QuantityBody extends LActivity implements View.OnClickListener {
    private RelativeLayout rl_province, rl_timed, rl_dtime;
    private EditText et_name, et_phone, et_xaddr,tv_province,tv_ymd;
    private Button btn_sure;
    private TextView  tv_dtime;
    private String provinceID,cityID,countryID;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_quantitybody);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_quantitybody));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        rl_province = (RelativeLayout) findViewById(R.id.rl_address);
        rl_timed = (RelativeLayout) findViewById(R.id.rl_time);
        rl_dtime = (RelativeLayout) findViewById(R.id.rl_timesd);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_xaddr = (EditText) findViewById(R.id.et_xxaddr);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        tv_province = (EditText) findViewById(R.id.tv_bbb);
        tv_dtime = (TextView) findViewById(R.id.tv_daysd);
        tv_ymd=(EditText)findViewById(R.id.tv_day);
        rl_province.setOnClickListener(this);
        rl_timed.setOnClickListener(this);
        rl_dtime.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_address) {
            T.ss("省市区");
            Intent intent=new Intent(this,ProviceInfoPlace.class);
            startActivityForResult(intent, 1);
        }
        if (id == R.id.rl_time) {
            //T.ss("年月日");
            Calendar c = Calendar.getInstance();
            MyDatePickDialog datePicker = new MyDatePickDialog(QuantityBody.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    tv_ymd.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

            datePicker.show();
        }
        if (id == R.id.rl_timesd) {
            T.ss("预约时间段");
        }
        if (id == R.id.btn_sure) {
            T.ss("确定");
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 1:
                if (data != null) {
                    String name = data.getExtras().getString("name");
                    tv_province.setText(name);
                    provinceID = data.getExtras().getString("pid"); // 省ID
                    cityID = data.getExtras().getString("cityid");   //  市ID
                    if(data.getExtras().getString("countryid")==null){
                        countryID=null;
                    }else {
                        countryID = data.getExtras().getString("countryid"); // 区ID
                    }
                }
                break;
            default:
                break;
        }
    }
}
