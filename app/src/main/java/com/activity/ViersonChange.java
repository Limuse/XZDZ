package com.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.adapter.MyCustomeAdapterPager;
import com.adapter.ViersonChangeAdapterPager;
import com.custom.CustomViewPager;
import com.custom.PagerSlidingTabStrip;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/30.
 * 版型选择
 */
public class ViersonChange extends LActivity {
    private PagerSlidingTabStrip tabStrip;
    private CustomViewPager viewPager;
    private ViersonChangeAdapterPager pagerAdapter;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_viersonchange);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarc);
        toolbar.setTitle(getResources().getText(R.string.app_viersonchange));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab_strip);
        viewPager = (CustomViewPager) findViewById(R.id.view_pagers);
        pagerAdapter = new ViersonChangeAdapterPager(this, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //禁止手动滑动
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        tabStrip.setViewPager(viewPager);

    }

    public void save(View v) {
        T.ss("保存");
    }

    private void initView() {

    }
}
