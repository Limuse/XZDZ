package com.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.adapter.MyRedMAdapter;
import com.common.Token;
import com.custom.IlistView;
import com.entity.MyRedbEntity;
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
 * Created by huisou on 2015/11/5.
 */
public class OutMyReam extends LActivity {
    private IlistView listview_outrb;
    private List<MyRedbEntity> list = new ArrayList<>();
    private MyRedMAdapter adapter;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_outmyream);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_outmyredm));
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
        listview_outrb = (IlistView) findViewById(R.id.listview_rb);
    }

    private void initData() {
        Resources res = getResources();
        final Map<String, String> map = new HashMap<>();
        String url = res.getString(R.string.app_service_url)
                + "/app/order/coupon/sign/aggregation/";
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
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    MyRedbEntity itementity = new MyRedbEntity();
                    itementity.setId(object.getString("id"));
                    itementity.setCoupon_id(object.getString("coupon_id"));
                    String tme = object.getString("end_time").substring(0, 10);
                    itementity.setEnd_time("有效期至" + tme);
                    String money = object.getString("money").substring(0, object.getString("money").indexOf("."));
                    itementity.setMoney(money);
                    list.add(itementity);
                }
                adapter = new MyRedMAdapter(OutMyReam.this, list);
                listview_outrb.setAdapter(adapter);
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
