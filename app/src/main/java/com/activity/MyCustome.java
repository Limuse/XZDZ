package com.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.adapter.MyCustomeAdapterPager;
import com.custom.PagerSlidingTabStrip;
import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/27.
 * 我要定制
 */
public class MyCustome extends LActivity {
    private PagerSlidingTabStrip tabStrip;
    private ViewPager viewPager;
    private MyCustomeAdapterPager pagerAdapter;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_mycustome);
        initBar();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_mycustome));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab_strip);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new MyCustomeAdapterPager(this, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabStrip.setViewPager(viewPager);
    }


}
