package com.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/27.
 * 正在定制
 */
public class Customing extends LActivity {
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_customing);
        initBar();
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

}
