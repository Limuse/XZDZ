package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.xzdz.R;

/**
 * Created by huisou on 2015/11/3.
 * 售后-提交信息
 */
public class AfterSale extends LActivity implements View.OnClickListener {
    private Button btn_submit;
    private EditText et_text;
    private HorizontalScrollView holsview;
    private RelativeLayout rl_btn;
    private Spinner spinner;
    private String[] datas;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_aftersale);
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
        btn_submit = (Button) findViewById(R.id.tj);
        rl_btn = (RelativeLayout) findViewById(R.id.rl_byn);
        et_text = (EditText) findViewById(R.id.et_xiangq);
        holsview = (HorizontalScrollView) findViewById(R.id.chae);
        spinner = (Spinner) findViewById(R.id.spinner);

        btn_submit.setOnClickListener(this);
        rl_btn.setOnClickListener(this);
        datas = new String[]{"袖口", "领口", "上衣", "裤子", "裤脚"};

        ArrayAdapter<String> adapterTwo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, datas);
        spinner.setAdapter(adapterTwo);
        spinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tj) {
            //提交审核
            Intent intent = new Intent(this, SubmitCheck.class);
            startActivity(intent);
        }
        if (id == R.id.rl_byn) {
            T.ss("照相");
        }
    }
}
