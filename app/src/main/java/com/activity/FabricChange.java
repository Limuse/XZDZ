package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/30.
 * 面料选择
 */
public class FabricChange extends LActivity {
    private Button btn_save;
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_fabrichange);
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
        btn_save = (Button) findViewById(R.id.nextt);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               Intent intent = new Intent(this, ChangePhone.class);
//                startActivity(intent);
            }
        });
    }

    //保存
    public void save(View v) {
//        Intent intent = new Intent(this, ChangePhone.class);
//        startActivity(intent);
    }
}
