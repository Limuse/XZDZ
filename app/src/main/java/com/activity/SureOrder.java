package com.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.common.AboutActivitySy;
import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/11/2.
 * 确认订单
 */
public class SureOrder extends LActivity implements View.OnClickListener {
    private Button btn_jind, btn_first;
    private String order_id;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_sureorder);
       // AboutActivitySy.getInstance().addActivity(this);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_sureorder));
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
        order_id = getIntent().getExtras().getString("order_id");
        btn_jind = (Button) findViewById(R.id.btn_jind);
        btn_first = (Button) findViewById(R.id.btn_backfst);
        btn_jind.setOnClickListener(this);
        btn_first.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_jind) {
            Intent intent = new Intent(this, CustomingState.class);
            intent.putExtra("state", "准备量体");
            intent.putExtra("order_id", order_id);
            startActivity(intent);
        }
        if (id == R.id.btn_backfst) {
            AboutActivitySy.getInstance().exit();
            finish();
        }
    }
}
