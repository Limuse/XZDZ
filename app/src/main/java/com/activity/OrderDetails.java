package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/30.
 * 订单详情
 */
public class OrderDetails extends LActivity implements View.OnClickListener {
    private RelativeLayout rl_lt, rl_lts, rl_detail, rl_redbox;
    private ImageView img_info;
    private TextView tv_title, tv_mianl, tv_price, tv_yfprice, tv_red, tv_allprice;
    private Button btn_payfor;
    private EditText et_text;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_orderdetails);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_orderdetail));
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
        rl_lt = (RelativeLayout) findViewById(R.id.rl_passs);
        rl_lts = (RelativeLayout) findViewById(R.id.rl_pass2);
        rl_detail = (RelativeLayout) findViewById(R.id.rl_mails);
        rl_redbox = (RelativeLayout) findViewById(R.id.rl_clred);
        img_info = (ImageView) findViewById(R.id.img_img);
        tv_title = (TextView) findViewById(R.id.tv_tvtype);
        tv_mianl = (TextView) findViewById(R.id.tv_tvtypes);
        tv_price = (TextView) findViewById(R.id.tv_tvprice);
        tv_yfprice = (TextView) findViewById(R.id.img_right2);
        tv_red = (TextView) findViewById(R.id.tv_redbox);
        tv_allprice = (TextView) findViewById(R.id.tv_malls);
        btn_payfor = (Button) findViewById(R.id.btn_payfor);
        et_text = (EditText) findViewById(R.id.et_please_num);
        rl_lt.setOnClickListener(this);
        rl_lts.setOnClickListener(this);
        rl_detail.setOnClickListener(this);
        rl_redbox.setOnClickListener(this);
        btn_payfor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_passs) {
            T.ss("添加预约量体信息");
            Intent intent = new Intent(this, QuantityBody.class);
            startActivity(intent);
        }
        if (id == R.id.rl_pass2) {
            T.ss("修改预约量体信息");
        }
        if (id == R.id.rl_mails) {
            T.ss("商品清单");
        }
        if (id == R.id.rl_clred) {
            T.ss("红包");
            Intent intent = new Intent(this, MyReam.class);
            startActivity(intent);
        }
        if (id == R.id.btn_payfor) {
            T.ss("支付定金");
            Intent intent = new Intent(this, SureOrder.class);
            startActivity(intent);
        }
    }
}
