package com.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.adapter.CustomingAdapter;
import com.adapter.MyCollectAdapter;
import com.common.Token;
import com.entity.CustomingEntity;
import com.entity.MyWorkedEntity;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
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
 * 正在定制
 */
public class Customing extends LActivity implements AdapterView.OnItemClickListener {
    private ListView listview;
    private CustomingAdapter adapter;
    private List<CustomingEntity> list = new ArrayList<>();

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_customing);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_customing));
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
        initData();
        listview = (ListView) findViewById(R.id.listview);

        listview.setOnItemClickListener(this);
    }

    private void initData() {
//        CustomingEntity entity = new CustomingEntity();
//        entity.setNum("091674367");
//        entity.setState("准备量体");
//        entity.setTime("2015-10-23");
//        entity.setTitle("量体印花西服");
//        list.add(entity);

        Resources res = getResources();
        final Map<String, String> map = new HashMap<>();
        String url = res.getString(R.string.app_service_url)
                + "/app/order/orderlist/sign/aggregation/";
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
//                if(array.length()<1){
//
//                }else
//                private String imgs;
//                private String state;
//                private String num;
//                private String time;
//                private String id;
//                private String title;
//                "id": "1",
//                        "order_id": "12",
//                        "title": "单排一粒扣",
//                        "order_time": "2038-01-19",
//                        "cut_status": "已完成",
//                        "img": "http://huihaokj.cn//Uploads/Admin/image/20151116/20151116114824_91183.jpg"
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    CustomingEntity entity = new CustomingEntity();
                    entity.setId(object.getString("id"));
                    entity.setNum(object.getString("order_id"));
                    entity.setTitle(object.getString("title"));
                    entity.setTime(object.getString("order_time"));
                    entity.setState(object.getString("cut_status"));
                    entity.setImgs(object.getString("img"));
                    list.add(entity);
                }
                adapter = new CustomingAdapter(this, list);
                listview.setAdapter(adapter);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CustomingEntity cu = list.get(position);
        Intent intent = new Intent(this, CustomingState.class);
        intent.putExtra("state", cu.getState());
        intent.putExtra("id", cu.getId());
        intent.putExtra("order_id", cu.getNum());
        startActivity(intent);
    }
}
