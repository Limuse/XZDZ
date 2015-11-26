package com.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.adapter.MyWorkeDetailAdapter;
import com.common.Token;
import com.custom.IlistView;
import com.custom.ImageCycleView;
import com.custom.ImageCycleViewRight;
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
 * Created by huisou on 2015/11/26.
 */
public class MyWorkDetail extends LActivity {
    private String pid;
    private ImageCycleViewRight icview;
    private TextView tv_detail;
    private IlistView listview;
    private MyWorkeDetailAdapter adapter;
    private List<Map<String, String>> map = new ArrayList<>();
    private ArrayList<String> pageImageList = new ArrayList<String>();
    private ArrayList<String> pageImageId = new ArrayList<String>();

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_myworkdetail);
        initBar();
        initView();
        initData();
    }

    private void initView() {
        icview = (ImageCycleViewRight) findViewById(R.id.ImageCycleViewdetail);
        tv_detail = (TextView) findViewById(R.id.tv_details);
        listview = (IlistView) findViewById(R.id.listvewdetail);

    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_myworkedetail));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        pid = getIntent().getStringExtra("pid");
        //app/product/myworkdetail/sign/aggregation

        Resources res = getResources();
        // final Map<String, String> map = new HashMap<>();
        String url = res.getString(R.string.app_service_url)
                + "app/product/myworkdetail/sign/aggregation/?" + "uuid=" + Token.get(this) + "&product_id=" + pid;
        LReqEntity entity = new LReqEntity(url);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);
    }

    private void getJsonData(String data) {
        map.clear();
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
               JSONObject json = jsonObject.getJSONObject("list");

                    JSONArray as = json.getJSONArray("pictures");
                    for (int i = 0; i < as.length(); i++) {
                        JSONObject jon = as.getJSONObject(i);
                        jon.getString("img");
                        pageImageList.add(jon.getString("img"));
                        pageImageId.add(i + "");
                    }
                    icview.setImageResources(pageImageList, pageImageId, mAdCycleViewListener);

                    JSONObject s = json.getJSONObject("first");
                    String text = s.getString("title");
                    tv_detail.setText(text);
                    JSONArray arr = json.getJSONArray("detail_second");
                    for (int j = 0; j < arr.length(); j++) {
                        JSONObject js = arr.getJSONObject(j);
                        Map<String, String> mas = new HashMap<>();
                        mas.put("img", js.getString("thumbnail"));
                        mas.put("tv1", js.getString("title"));
                        mas.put("tv2", js.getString("content"));
                        map.add(mas);
                    }

                adapter = new MyWorkeDetailAdapter(this, map);
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

    private ImageCycleViewRight.ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewRight.ImageCycleViewListener() {
        public void onImageClick(int position, View imageView) {
        }
    };

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
}
