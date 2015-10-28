package com.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/28.
 * 绑定邮箱
 */
public class BuildEmail extends LActivity {
    private EditText ed_mail, ed_mailpwd;
    private Button btn_save;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_buildemail);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_buildemail));
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
        ed_mail = (EditText) findViewById(R.id.et_mail);
        ed_mailpwd = (EditText) findViewById(R.id.et_mailpwd);
        btn_save = (Button) findViewById(R.id.btn_save);
    }

    //保存
    public void submit(View v) {

    }
}
