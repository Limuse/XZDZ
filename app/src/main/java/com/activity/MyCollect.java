package com.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/27.
 * 我的收藏
 */
public class MyCollect extends LActivity {
    private GridView gview;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_mycollect);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_mycollect));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
//        toolbar.inflateMenu(R.string.write);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            public boolean onMenuItemClick(MenuItem item) {
//               T.ss("bianjjjjjjjj");
//                return false;
//            }
//        });
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        gview = (GridView) findViewById(R.id.gridView);
    }
}
