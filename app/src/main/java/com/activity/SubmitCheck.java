package com.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.xzdz.R;

import org.w3c.dom.Text;

/**
 * Created by huisou on 2015/11/4.
 * 提交审核-提交信息
 */
public class SubmitCheck extends LActivity implements View.OnClickListener {
    private LinearLayout rl_sc1, rl_sc2, rl_sc3, rl_sc4, rl_sc5;
    private Button btn_backfirst;
    private TextView tv_kfphones;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_submitcheck);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_afersalse));
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
        rl_sc1 = (LinearLayout) findViewById(R.id.lly_sc1);
        rl_sc2 = (LinearLayout) findViewById(R.id.lly_sc2);
        rl_sc3 = (LinearLayout) findViewById(R.id.lly_sc3);
        rl_sc4 = (LinearLayout) findViewById(R.id.lly_sc4);
        rl_sc5 = (LinearLayout) findViewById(R.id.lly_sc5);
        rl_sc1.setVisibility(View.GONE);
        rl_sc2.setVisibility(View.GONE);
        rl_sc3.setVisibility(View.GONE);
        rl_sc4.setVisibility(View.GONE);
        rl_sc5.setVisibility(View.GONE);
        tv_kfphones = (TextView) findViewById(R.id.scu_palyphone1);
        btn_backfirst = (Button) findViewById(R.id.backfirst);
        tv_kfphones.setOnClickListener(this);
        btn_backfirst.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.scu_palyphone1) {
            T.ss("电话");
        }
        if (id == R.id.backfirst) {
            T.ss("返回首页");
        }
    }
}
