package com.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.common.Token;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;
import com.xzdz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huisou on 2015/11/19.
 * 手机
 */

public class InfPhone extends LActivity {
    private EditText et_phone;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_infphone);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbphone);
        toolbar.setTitle(getResources().getText(R.string.app_infphone));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("phone", "2");
                setResult(5, intent);
                finish();
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();
        intent.putExtra("phone", "2");
        setResult(5, intent);
        return super.onKeyDown(keyCode, event);

    }
    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);

    }

    public void save(View v) {
        String phone = et_phone.getText().toString();
        if (phone.equals(null)) {
            T.ss("手机号不能为空！");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("mobile", phone);// 邮箱
            map.put("uuid", Token.get(this));
            Resources res = getResources();
            String url = res.getString(R.string.app_service_url)
                    + "/app/member/editmobile/sign/aggregation/";
            LReqEntity entity = new LReqEntity(url, map);
            ActivityHandler handler = new ActivityHandler(this);
            handler.startLoadingData(entity, 1);
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

    private void getJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                T.ss(jsonObject.getString("info"));
                Intent intent = new Intent();
                String phone = et_phone.getText().toString();
                if (phone.equals(null)) {
                    intent.putExtra("phone", "1");
                } else {
                    intent.putExtra("phone", et_phone.getText().toString());
                }
                setResult(5, intent);
                finish();
            } else {
                T.ss(jsonObject.getString("info"));
                //String longs = jsonObject.getString("info");
//                if(longs.equals("请先登录")){
//                    LSharePreference.getInstance(this).setBoolean("login", false);
//                    Intent intent = new Intent(this, LoginMain.class);
//                    startActivity(intent);
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
