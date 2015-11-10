package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/30.
 *
 */
public class FabricChangeSubmit extends LActivity implements View.OnClickListener {
    private Button btn_submit;
    private ImageView imgv, img1, img2, img3, img4;


    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_fabricchangesubmit);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        toolbar.setTitle(getResources().getText(R.string.app_fabrichange));
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
        imgv = (ImageView) findViewById(R.id.imgv);
        img1 = (ImageView) findViewById(R.id.imgc1);
        img2 = (ImageView) findViewById(R.id.imgc2);
        img3 = (ImageView) findViewById(R.id.imgc3);
        img4 = (ImageView) findViewById(R.id.imgc4);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(FabricChangeSubmit.this, OrderDetails.class);
                startActivity(intent);
                //T.ss("提交订单");
            }
        });
    }

    //保存
    public void save(View v) {
//        Intent intent = new Intent(this, ChangePhone.class);
//        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.imgc1) {
            Intent intent = new Intent(this, FabricChange.class);
            startActivity(intent);
        }
        if (id == R.id.imgc2) {
            Intent intent = new Intent(this, FabricChange.class);
            startActivity(intent);
        }
        if (id == R.id.imgc3) {
            Intent intent = new Intent(this, FabricChange.class);
            startActivity(intent);
        }
        if (id == R.id.imgc4) {
            Intent intent = new Intent(this, FabricChange.class);
            startActivity(intent);
        }
    }
}
