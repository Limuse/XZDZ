package com.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.common.Bar;
import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/28.
 * 更换手机
 */
public class ChangePhone extends LActivity {
    private EditText ed1, ed2, ed3;
    private Button btn_save,btn_oldsee;
    private Boolean key=true;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_changephone);
        initBar();
        initView();
    }

    private void initBar() {
        //Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_phones));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //  toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        ed1 = (EditText) findViewById(R.id.et_ps);
        ed2 = (EditText) findViewById(R.id.et_codes);
        ed3 = (EditText) findViewById(R.id.et_p);
        btn_oldsee=(Button) findViewById(R.id.btn_oldsee);
    }

    //发送验证码
    public void send(View v) {

    }

    //保存
    public void saves(View v) {

    }

    //密码可见
    public void oldssee(View v) {
        if (key == true) {
            ed1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            btn_oldsee.setBackgroundResource(R.mipmap.eyesee);
            key = false;
        } else if (key == false) {
            ed1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            btn_oldsee.setBackgroundResource(R.mipmap.eyes);
           key = true;
        }
    }
}
