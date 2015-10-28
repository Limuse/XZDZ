package com.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/27.
 * 个人资料
 */
public class MyInfo extends LActivity implements View.OnClickListener {
    private RelativeLayout rl1, rl2, rl3, rl4, rl5, rl6, rl7;
    private TextView tv_sex, tv_brithday, tv_phone, tv_mail;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_myinfo);
        initBar();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_myinfo));
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
        rl1 = (RelativeLayout) findViewById(R.id.rl_img);
        rl2 = (RelativeLayout) findViewById(R.id.rl_name);
        rl3 = (RelativeLayout) findViewById(R.id.rl_nametrue);
        rl4 = (RelativeLayout) findViewById(R.id.rl_sex);
        rl5 = (RelativeLayout) findViewById(R.id.rl_brithday);
        rl6 = (RelativeLayout) findViewById(R.id.rl_phone);
        rl7 = (RelativeLayout) findViewById(R.id.rl_mail);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_brithday = (TextView) findViewById(R.id.tv_bridthday);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_mail=(TextView) findViewById(R.id.tv_mial);
        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl6.setOnClickListener(this);
        rl7.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    int id=v.getId();
        if(id==R.id.rl_img){
            //头像
        }
        if(id==R.id.rl_name){
            //昵称
        }
        if(id==R.id.rl_nametrue){
            //真实姓名
        }
        if(id==R.id.rl_sex){
            //性别
        }
        if(id==R.id.rl_brithday){
            //生日
        }
        if(id==R.id.rl_phone){
            //手机
        }
        if(id==R.id.rl_mail){
            //邮箱
        }

    }
}
