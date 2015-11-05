package com.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.common.Bar;
import com.custom.CirclePageIndicator;
import com.fragment.Specific_page;
import com.leo.base.activity.LActivity;
import com.xzdz.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/10/27.
 * 量体数据
 */
public class SpecificData extends LActivity {

    private CirclePageIndicator indicator;
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<Map<String, String>> specInfo = new ArrayList<Map<String, String>>();

    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_specificdata);
        initBar();
        initView();
        initPage();
    }

    private void initBar() {
//         Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_specificdata));
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
        viewPager = (ViewPager) findViewById(R.id.viewpage);
    }

    private void initPage() {
        for (int i = 0; i < 5; i++) {
            Specific_page fragmentSpecificPage = new Specific_page();
//            fragmentSpecificPage.getData(specInfo.get(i));
            fragmentList.add(fragmentSpecificPage);
        }
        WindowManager wm = getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(width / 10);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }

    private class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        public int getCount() {
            return fragmentList.size();
        }
    }
}
