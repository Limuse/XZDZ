package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.custom.IlistView;
import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/27.
 * 我的红包
 */
public class MyReam extends LActivity implements View.OnClickListener {
    private RelativeLayout rl_next;
    private EditText et_text;
    private Button btn_change;
    private IlistView listview;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_myredm);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_myredm));
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
        rl_next = (RelativeLayout) findViewById(R.id.rl_next);
        et_text = (EditText) findViewById(R.id.ed_redb);
        btn_change = (Button) findViewById(R.id.btn_change);
        listview = (IlistView) findViewById(R.id.listview_rb);
        rl_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_next) {
            Intent intent = new Intent(this, OutMyReam.class);
            startActivity(intent);
        }
    }
}
