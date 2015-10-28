package com.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/28.
 * 修改密码
 */
public class UpdatePwd extends LActivity implements View.OnClickListener {
    private EditText ed1, ed2;
    private Button btn_save;
    private Button btn_see, btn_see1;
    private Boolean newkey = true, oldkey = true;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_updatepwd);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_updatepwd));
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
        ed1 = (EditText) findViewById(R.id.et_pwds_oldnum);
        ed2 = (EditText) findViewById(R.id.et_update_pwds);
        btn_see = (Button) findViewById(R.id.btn_oldsee);
        btn_see1 = (Button) findViewById(R.id.btn_sees);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

    }

    //新密码可见
    public void newsee(View v) {
        if (newkey == true) {
            ed2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            btn_see1.setBackgroundResource(R.mipmap.eyesee);
            newkey = false;
        } else if (newkey == false) {
            ed2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            btn_see1.setBackgroundResource(R.mipmap.eyes);
            newkey = true;
        }
    }

    //老密码可见
    public void oldssee(View v) {
        if (oldkey == true) {
            ed1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            btn_see.setBackgroundResource(R.mipmap.eyesee);
            oldkey = false;
        } else if (oldkey == false) {
            ed1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            btn_see.setBackgroundResource(R.mipmap.eyes);
            oldkey = true;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_save) {
            ed1.getText().toString();
            ed2.getText().toString();
        }
    }
}
