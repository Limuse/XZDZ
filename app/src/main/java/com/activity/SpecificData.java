package com.activity;

import android.graphics.Bitmap;
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

import com.MyApplication;
import com.alibaba.fastjson.JSON;
import com.common.Bar;
import com.common.Token;
import com.custom.CirclePageIndicator;
import com.entity.SpecificEntity;
import com.fragment.Specific_page;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xzdz.R;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private SpecificEntity specificEntity;

    public static DisplayImageOptions options;
    public static ImageLoader imageLoader;

    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_specificdata);
        if (imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }
        options = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        initBar();
        initView();
        initData();

    }

    private void initData() {
        ActivityHandler handler = new ActivityHandler(this);
        String url = getResources().getString(R.string.app_service_url) + "/app/member/suituser_info/sign/aggregation/";
        Map<String, String> map = new HashMap<String, String>();
        map.put("uuid", Token.get(mContext));
        LReqEntity entity = new LReqEntity(url, map);
        handler.startLoadingData(entity, 1);
    }

    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getData(msg.getStr());
            }
        }
    }

    public void getData(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                specificEntity = JSON.parseObject(str, SpecificEntity.class);
                initPage();
            } else {
                T.ss(jsonObject.getString("msg"));
            }
        } catch (Exception e) {

        }


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
        for (int i = 0; i < specificEntity.getList().size(); i++) {
            Specific_page fragmentSpecificPage = new Specific_page();
            fragmentSpecificPage.getData(specificEntity.getList().get(i));
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
