package com.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.adapter.MyCollectAdapter;
import com.adapter.MyCustomeAdapterPager;
import com.common.AboutActivitySy;
import com.common.Token;
import com.custom.PagerSlidingTabStrip;
import com.entity.MyCusdtomEntity;
import com.entity.MyWorkedEntity;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.xzdz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/10/27.
 * 我要定制
 */
public class MyCustome extends LActivity {
    private PagerSlidingTabStrip tabStrip;
    private ViewPager viewPager;
    private MyCustomeAdapterPager pagerAdapter;
    private List<MyCusdtomEntity.ListEntity> list = new ArrayList<>();
    private List<MyCusdtomEntity.ListEntity.ChildEntity> childlist = new ArrayList<>();
    private List<MyCusdtomEntity.ListEntity.ChildEntity.childV> chivlist = new ArrayList<>();
    public static MyCustome myCustome;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_mycustome);
        AboutActivitySy.getInstance().addActivity(this);
        myCustome = this;
        initBar();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    }

    private void initView() {
        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tab_strip);
//        tabStrip.setTabBackground();
        tabStrip.setTabChecdBackgroud(R.mipmap.tbb);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        pagerAdapter = new MyCustomeAdapterPager(this, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabStrip.setViewPager(viewPager);
    }

    private void initData() {
        Resources res = getResources();
        final Map<String, String> map = new HashMap<>();
        String url = res.getString(R.string.app_service_url)
                + "/app/product/categoryinfo/sign/aggregation/";
        map.put("uuid", Token.get(this));
        LReqEntity entity = new LReqEntity(url, map);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);

    }

    private void getJsonData(String data) {
        list.clear();
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONArray array = jsonObject.getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    MyCusdtomEntity.ListEntity listEntity = new MyCusdtomEntity.ListEntity();
                    listEntity.setId(object.getString("id"));
                    listEntity.setPid(object.getString("pid"));
                    listEntity.setTitle(object.getString("title"));
                    listEntity.setFace_pic(object.getString("face_pic"));
                    JSONArray array_child = object.getJSONArray("_child");

                    for (int t = 0; t < array_child.length(); t++) {
                        JSONObject ob = array_child.getJSONObject(t);
                        MyCusdtomEntity.ListEntity.ChildEntity childEntity = new MyCusdtomEntity.ListEntity.ChildEntity();
                        childEntity.setId(ob.getString("id"));
                        childEntity.setPid(ob.getString("pid"));
                        childEntity.setTitle(ob.getString("title"));
                        childEntity.setFace_pic(ob.getString("face_pic"));
                        if (!ob.opt("_child").equals("")) {
                            JSONArray arr = ob.getJSONArray("_child");

                            for (int o = 0; o < arr.length(); o++) {
                                JSONObject jsd = arr.getJSONObject(o);
                                MyCusdtomEntity.ListEntity.ChildEntity.childV childV = new MyCusdtomEntity.ListEntity.ChildEntity.childV();
                                childV.setId(jsd.getString("id"));
                                childV.setPid(jsd.getString("pid"));
                                childV.setTitle(jsd.getString("title"));
                                childV.setFace_pic(jsd.getString("face_pic"));
                                childV.set_child(jsd.getString("_child"));
                                chivlist.add(childV);
                            }

                        } else {
                            String as = ob.getString("_child");
                        }

                        childEntity.set_child(chivlist);
                        childlist.add(childEntity);
                    }
                    listEntity.set_child(childlist);
                    list.add(listEntity);

                }
                initView();
            } else {
                //  T.ss(jsonObject.getString("info").toString());
                //String longs = jsonObject.getString("info");
//                if (longs.equals("请先登录")) {
//                    LSharePreference.getInstance(this).setBoolean("login", false);
//                    Intent intent = new Intent(this, LoginMain.class);
//                    startActivity(intent);
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonData(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }


    public List<MyCusdtomEntity.ListEntity> SendList() {
        return list;


    }

}
