package com.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/11/12.
 * 修改真实姓名
 */
public class UpdateNameTrue extends LActivity {
    private EditText et_namettrue;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_updatenametrue);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbupt);
        toolbar.setTitle(getResources().getText(R.string.app_updatenametrue));
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
        et_namettrue = (EditText) findViewById(R.id.et_update_namet);
    }

    public void save(View view) {

    }
}
