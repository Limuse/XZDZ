package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/27.
 * 设置
 */
public class Set extends LActivity implements View.OnClickListener {
    private RelativeLayout rl1, rl2, rl3, rl4;
    private Button btn_out;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_set);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_set));
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
        rl1 = (RelativeLayout) findViewById(R.id.rl_pass);
        rl2 = (RelativeLayout) findViewById(R.id.rl_mails);
        rl3 = (RelativeLayout) findViewById(R.id.rl_phones);
        rl4 = (RelativeLayout) findViewById(R.id.rl_clear);
        btn_out = (Button) findViewById(R.id.btn_outline);

        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        btn_out.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_pass) {
            //密码修改
            Intent intent = new Intent(this, UpdatePwd.class);
            startActivity(intent);
        }
        if (id == R.id.rl_mails) {
            //邮箱绑定
            Intent intent = new Intent(this, BuildEmail.class);
            startActivity(intent);
        }
        if (id == R.id.rl_phones) {
            //手机绑定
            Intent intent = new Intent(this, Phone.class);
            intent.putExtra("id","1");
            startActivity(intent);
        }
        if (id == R.id.rl_clear) {
            //清理缓存
        }
        if (id == R.id.btn_outline) {
            //退出登录
        }
    }
}
