package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adapter.CustomStateAdapter;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.xzdz.R;

/**
 * Created by huisou on 2015/11/3.
 */
public class CustomingState extends LActivity implements View.OnClickListener {
    private LinearLayout rl_s1, rl_s2, rl_s3, rl_s4, rl_s5;
    private TextView tv1_name, tv1_phone, tv1_time, tv1_addr, tv1_kfphone;
    private TextView tv2_kfphone;
    private TextView tv3_name, tv3_phone, tv3_addr, tv3_kfphone;
    private TextView tv4_kfphone, tv4_wstate, tv4_gogns, tv4_num, tv4_wphone;
    private ImageView img4;
    private ListView listview4;
    private TextView tv5_kfphone;
    private Button btn5_sback;

    private CustomStateAdapter adapter;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_customingstate);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_customing));
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
        rl_s1 = (LinearLayout) findViewById(R.id.lly_s1);
        rl_s2 = (LinearLayout) findViewById(R.id.lly_s2);
        rl_s3 = (LinearLayout) findViewById(R.id.lly_s3);
        rl_s4 = (LinearLayout) findViewById(R.id.lly_s4);
        rl_s5 = (LinearLayout) findViewById(R.id.lly_s5);
        /**
         * 根据状态判断是否隐藏
         */
        rl_s1.setVisibility(View.GONE);
        rl_s2.setVisibility(View.GONE);
        rl_s3.setVisibility(View.GONE);
        rl_s4.setVisibility(View.GONE);
        rl_s5.setVisibility(View.VISIBLE);

        tv1_name = (TextView) findViewById(R.id.sc_name1);
        tv1_phone = (TextView) findViewById(R.id.sc_phone1);
        tv1_time = (TextView) findViewById(R.id.sc_time1);
        tv1_addr = (TextView) findViewById(R.id.sc_addr1);
        tv1_kfphone = (TextView) findViewById(R.id.sc_palyphone1);
        tv1_kfphone.setOnClickListener(this);
        tv2_kfphone = (TextView) findViewById(R.id.sc_palyphone2);
        tv2_kfphone.setOnClickListener(this);
        tv3_name = (TextView) findViewById(R.id.names3);
        tv3_phone = (TextView) findViewById(R.id.phone3);
        tv3_addr = (TextView) findViewById(R.id.addr3);
        tv3_kfphone = (TextView) findViewById(R.id.sc_palyphone3);
        tv3_kfphone.setOnClickListener(this);
        tv4_kfphone = (TextView) findViewById(R.id.sc_palyphone4);
        tv4_kfphone.setOnClickListener(this);
        tv4_wstate = (TextView) findViewById(R.id.wlstate4);
        tv4_gogns = (TextView) findViewById(R.id.kud4);
        tv4_num = (TextView) findViewById(R.id.num4);
        tv4_wphone = (TextView) findViewById(R.id.wphone4);
        img4 = (ImageView) findViewById(R.id.imgs4);
        listview4 = (ListView) findViewById(R.id.wlistvew);
        tv5_kfphone = (TextView) findViewById(R.id.sc_palyphone5);
        tv5_kfphone.setOnClickListener(this);
        btn5_sback = (Button) findViewById(R.id.shouh);
        btn5_sback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sc_palyphone1) {
            T.ss("客服电话");
        }
        if (id == R.id.sc_palyphone2) {
            T.ss("客服电话");
        }
        if (id == R.id.sc_palyphone3) {
            T.ss("客服电话");
        }
        if (id == R.id.sc_palyphone4) {
            T.ss("客服电话");
        }
        if (id == R.id.sc_palyphone5) {
            T.ss("客服电话");
        }
        if (id == R.id.shouh) {
            //售后
            Intent intent = new Intent(this, AfterSale.class);
            startActivity(intent);
        }

    }
}
