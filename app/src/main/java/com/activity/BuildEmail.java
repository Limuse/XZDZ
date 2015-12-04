package com.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
 * Created by huisou on 2015/10/28.
 * 绑定邮箱
 */
public class BuildEmail extends LActivity {
    private EditText ed_mail, ed_mailpwd;
    private Button btn_save;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_buildemail);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_buildemail));
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
        ed_mail = (EditText) findViewById(R.id.et_mail);
        ed_mailpwd = (EditText) findViewById(R.id.et_mailpwd);
        btn_save = (Button) findViewById(R.id.btn_save);
    }

    //保存
    public void submit(View v) {
        String edemail = ed_mail.getText().toString();
        String edemailpwd = ed_mailpwd.getText().toString();
        if (edemail.equals(null)||edemailpwd.equals(null)) {
            T.ss("邮箱或密码不能为空！");
        } else {
            Map<String, String> map = new HashMap<>();
            //map.put("email", email);// 邮箱
            //map.put("uuid", Token.get(this));
            Resources res = getResources();
            String url = res.getString(R.string.app_service_url)
                    + "/app/member/editemail/sign/aggregation/?uuid="+ Token.get(this);
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
               finish();
            } else {
                T.ss(jsonObject.getString("info"));
                // String longs=jsonObject.getString("info");
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
