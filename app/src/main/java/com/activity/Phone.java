package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/28.
 * 绑定手机
 */
public class Phone extends LActivity {
    private TextView tv1, tv2;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_phone);
        initBar();
        initView();
    }

    private void initBar() {
        final String ids = getIntent().getExtras().getString("id");
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        toolbar.setTitle(getResources().getText(R.string.app_phones));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ids.equals("1")) {
                    finish();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("phone", tv2.getText().toString());
                    setResult(5, intent);
                    finish();
                }
            }
        });

    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv_bbb);
        tv2 = (TextView) findViewById(R.id.tv_phoness);
    }

    //更换手机
    public void change(View v) {
        Intent intent = new Intent(Phone.this, ChangePhone.class);
        startActivity(intent);
    }
}
