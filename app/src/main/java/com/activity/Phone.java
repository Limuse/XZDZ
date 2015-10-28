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
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_phones));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.inflateMenu(R.menu.menu_rightphone);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_messages) {
                    Intent intent = new Intent(Phone.this, ChangePhone.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv_bbb);
        tv2 = (TextView) findViewById(R.id.tv_phoness);
    }
}
