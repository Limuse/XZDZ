package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/30.
 * 面料选择
 */
public class Fabric extends LActivity {
    private Button btn_next;
    private LinearLayout grouplayout;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_fabric);
        initBar();
        initView();
    }


    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        toolbar.setTitle(getResources().getText(R.string.app_fabric));
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
        btn_next = (Button) findViewById(R.id.btn_next);
        grouplayout = (LinearLayout) findViewById(R.id.middle_layout);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fabric.this, FabricChangeSubmit.class);
                startActivity(intent);
            }
        });

        // 新增子布局对象到父布局中
        for (int i = 0; i < 8; i++) {
            ImageView childImg;// 子视图ImageView
            View childView;// 子视图View

            // 初始化子布局
            childView = LayoutInflater.from(this).inflate(
                    R.layout.imageview_h, null);
            childImg = (ImageView) childView.findViewById(R.id.child_img);

            childImg.setBackgroundResource(R.mipmap.f1);
            grouplayout.addView(childView);// 将childView添加到父布局
        }
    }
}
