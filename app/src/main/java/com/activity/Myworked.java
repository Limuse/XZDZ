package com.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.adapter.MyWorkedGridvAdapter;
import com.common.Token;
import com.entity.MyCusdtomEntity;
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
 * 我的作品
 */
public class Myworked extends LActivity {
    private GridView gridView;
    private MyWorkedGridvAdapter adapter;
    private Button write;
    private List<MyWorkedEntity> mlist = new ArrayList<>();
    private String title[] = null;
    private String time[] = null;
    private String id[] = null;
    private Boolean flg = true;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_myworked);
        initBar();
        initView();

    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        toolbar.setTitle(getResources().getText(R.string.app_myworked));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        write = (Button) findViewById(R.id.write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flg == true) {
                    write.setText("完成");
                    flg = false;
                    adapter.Update(true);
                    adapter.notifyDataSetChanged();
                } else if (flg == false) {
                    write.setText("编辑");
                    flg = true;
                    adapter.Update(false);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initView() {
        initData();
        gridView = (GridView) findViewById(R.id.gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    private void initData() {
        //   app/product/mywork/sign/aggregation/
        Resources res = getResources();
        // final Map<String, String> map = new HashMap<>();
        String url = res.getString(R.string.app_service_url)
                + "app/product/mywork/sign/aggregation/?" + "uuid=" +Token.get(this);
        LReqEntity entity = new LReqEntity(url);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);
    }

    private void getJsonData(String data) {
        mlist.clear();
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONArray array = jsonObject.getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    MyWorkedEntity myentity = new MyWorkedEntity();
                    myentity.setId(object.getString("product_id"));
                    myentity.setTitle(object.getString("title"));
                    myentity.setImg(object.getString("img"));
                    myentity.setTime(object.getString("etime"));
                    myentity.setType(object.getString("product_step"));
                    mlist.add(myentity);
                }
                adapter = new MyWorkedGridvAdapter(this, mlist);
                gridView.setAdapter(adapter);
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
}
