package com.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.xzdz.R;

/**
 * Created by huisou on 2015/11/2.
 * 预约量体
 */
public class QuantityBody extends LActivity implements View.OnClickListener {
    private RelativeLayout rl_province, rl_timed, rl_dtime;
    private EditText et_name, et_phone, et_xaddr;
    private Button btn_sure;
    private TextView tv_province, tv_dtime;

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
        tv_province = (TextView) findViewById(R.id.tv_bbb);
        tv_dtime = (TextView) findViewById(R.id.tv_daysd);
        rl_province.setOnClickListener(this);
        rl_timed.setOnClickListener(this);
        rl_dtime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.rl_address){
            T.ss("省市区");
        }
        if(id==R.id.rl_time){
            T.ss("年月日");
        }
        if(id==R.id.rl_timesd){
            T.ss("预约时间段");
        }
    }
}
