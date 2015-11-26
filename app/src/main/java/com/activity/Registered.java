package com.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.common.Bar;
import com.common.Token;
import com.common.UntilList;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.xzdz.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/8/11.
 */
public class Registered extends LActivity {
    private int isEye = 0;
    private EditText et_user, et_pwd, et_code;
    private Button btn_look, btn_send, btn_send1, btn_send2;
    private boolean flag = true;
    private int time = 60;
    private String code;
    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    private Map<String, String> shareMap = new HashMap<String, String>();
    protected void onLCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_registered);
        initBar();
        initView();
    }

    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_code = (EditText) findViewById(R.id.et_code);
        btn_look = (Button) findViewById(R.id.btn_look);
        btn_send1 = (Button) findViewById(R.id.btn_send1);
        btn_send2 = (Button) findViewById(R.id.btn_send2);
    }

    private void initBar() {
        Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_registered));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void eye(View v) {
        if (isEye == 1) {
            et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            btn_look.setBackgroundResource(R.mipmap.login_look);
            isEye = 0;
        } else if (isEye == 0) {
            et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            btn_look.setBackgroundResource(R.mipmap.login_look2);
            isEye = 1;
        }
    }

    public void send(View v) {
        if (UntilList.isPhone(et_user.getText().toString().trim())) {
            time = 60;
            flag = true;
            getTime();
            String url = getResources().getString(R.string.app_service_url) + "/app/register/smsverify/sign/aggregation/";
            ActivityHandler handler = new ActivityHandler(this);
            Map<String, String> map = new HashMap<String, String>();
            map.put("mobile", et_user.getText().toString().trim());
            LReqEntity entity = new LReqEntity(url, map);
            handler.startLoadingData(entity, 1);
        } else {
            T.ss("请输入正确的手机号码");
        }
    }

    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getCode(msg.getStr());
            } else if (requestId == 2) {
                reg(msg.getStr());
            }
            else if (requestId ==3) {
                getOrdersData(msg.getStr());
            }
        }
    }
    private void getOrdersData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.optInt("status") == 1) {
                JSONObject list = jsonObject.getJSONObject("list");

                JSONObject share_info = list.getJSONObject("share_info");
                shareMap.put("id", share_info.opt("id").toString());
                shareMap.put("title", share_info.opt("title").toString());
                shareMap.put("link", share_info.opt("link").toString());
                shareMap.put("content", share_info.opt("content").toString());
                shareMap.put("add_time", share_info.opt("add_time").toString());


                JSONArray coupon_list = list.getJSONArray("coupon_list");
                List<Map<String, String>> clist = new ArrayList<Map<String, String>>();
                for (int i = 0; i < coupon_list.length(); i++) {
                    JSONObject item = coupon_list.optJSONObject(i);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id", item.opt("id").toString());
                    map.put("money", item.opt("money").toString());
                    clist.add(map);
                }
//                ImageDialog dialog = new ImageDialog.Builder(Registered.this).setImage(R.mipmap.dialog_logo).setMessage(clist.get(1).get("money")).setInfo(clist.get(0).get("money"), clist.get(1).get("money")).setPositiveButton("暂不分享", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        finish();
//                    }
//                }).setNegativeButton("现在就去", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        share();
//                        dialog.dismiss();
//                    }
//                }).create();
//                dialog.show();
            }
        } catch (Exception e) {
            T.ss("分享信息解析失败");
        }
    }

    public void reg(String str) {
        try {
            JSONObject info = new JSONObject(str);
            int status = info.optInt("status");
            if (status == 1) {
                T.ss("注册成功");
                finish();
//                    String url = getResources().getString(R.string.app_service_url) + "/huihao/orders/denomination/1/sign/aggregation/";
//                ActivityHandler handler = new ActivityHandler(Registered.this);
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("uuid", Token.get(Registered.this));
//                LReqEntity entity = new LReqEntity(url, map);
//                handler.startLoadingData(entity, 3);
            } else {
                T.ss(info.opt("info").toString());
            }
        } catch (Exception e) {

        }
    }

    public void getCode(String str) {
        try {
            JSONObject info = new JSONObject(str);
            int status = info.optInt("status");
            if (status == 1) {
                T.ss("验证码发送成功，请注意查收");
            } else {
                T.ss("验证码发送失败，请重试");
                handler.sendEmptyMessage(2);
            }
        } catch (Exception e) {

        }
    }

    public void ok(View v) {
        if (!UntilList.isPhone(et_user.getText().toString().trim())) {
            T.ss("请输入正确的手机号码");
            return;
        }

        if (!(et_code.getText().toString().length() > 0)) {
            T.ss("请输入验证码");
            return;
        }

        if (!(et_pwd.getText().toString().length() > 0)) {
            T.ss("请输入密码");
            return;
        }

        ActivityHandler handler = new ActivityHandler(this);
        String url = getResources().getString(R.string.app_service_url) + "app/register/register/sign/aggregation/";
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", et_user.getText().toString().trim());
        map.put("captcha", et_code.getText().toString().trim());
        map.put("password", et_pwd.getText().toString().trim());
        LReqEntity entity = new LReqEntity(url, map);
        handler.startLoadingData(entity, 2);
    }

    public void getTime() {
        new Thread(new Runnable() {
            public void run() {
                while (flag) {
                    handler.sendEmptyMessage(1);
                    try {
                        Thread.sleep(1000);
                        if (time > 1) {
                            time--;
                        } else {
                            handler.sendEmptyMessage(2);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                btn_send1.setText(time + " s");
                btn_send1.setClickable(false);
                btn_send2.setClickable(false);
            }
            if (msg.what == 2) {
                btn_send1.setText("发送验证码");
                btn_send1.setClickable(true);
                btn_send2.setClickable(true);
                flag = false;
            }
        }
    };


//    public void share() {
////        mController.getConfig().setSsoHandler(new SinaSsoHandler());
//
//        String appID = "wxe5749e0e8d40f5aa";
//        String appSecret = "47eb904d7b88e62ad66287cbc6924daf";
//        UMWXHandler umwxHandler = new UMWXHandler(Registered.this, appID,
//                appSecret);
//        umwxHandler.addToSocialSDK();
//
//        UMWXHandler wxCircleHandler = new UMWXHandler(Registered.this, appID,
//                appSecret);
//        wxCircleHandler.setToCircle(true);
//        wxCircleHandler.addToSocialSDK();
//        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(Registered.this, "100424468",
//                "c7394704798a158208a74ab60104f0ba");
//        qqSsoHandler.addToSocialSDK();
//
//        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(Registered.this, "100424468",
//                "c7394704798a158208a74ab60104f0ba");
//        qZoneSsoHandler.addToSocialSDK();
//
//        WeiXinShareContent weixinContent = new WeiXinShareContent();
//
//        weixinContent.setShareContent(shareMap.get("content"));
//        weixinContent.setTitle(shareMap.get("title"));
//        weixinContent.setTargetUrl(shareMap.get("link"));
//        weixinContent.setShareImage(new UMImage(Registered.this, R.mipmap.logo));
//
//        CircleShareContent circleMedia = new CircleShareContent();
//        circleMedia.setShareContent(shareMap.get("content"));
//
//        circleMedia.setTitle(shareMap.get("title"));
//        circleMedia.setTargetUrl(shareMap.get("link"));
//        circleMedia.setShareImage(new UMImage(Registered.this, R.mipmap.logo));
//        mController.setShareMedia(circleMedia);
//        QQShareContent qqShareContent = new QQShareContent();
//
//        qqShareContent.setShareContent(shareMap.get("content"));
//        qqShareContent.setTitle(shareMap.get("title"));
//        qqShareContent.setTargetUrl(shareMap.get("link"));
//        qqShareContent.setShareImage(new UMImage(Registered.this, R.mipmap.logo));
//        mController.setShareMedia(qqShareContent);
//        QZoneShareContent qzone = new QZoneShareContent();
//        qzone.setShareContent(shareMap.get("content"));
//        qzone.setTargetUrl(shareMap.get("link"));
//        qzone.setTitle(shareMap.get("title"));
//        qzone.setShareImage(new UMImage(Registered.this, R.mipmap.logo));
//        mController.setShareMedia(qzone);
//        mController.setShareMedia(weixinContent);
//
//        mController.getConfig().removePlatform(SHARE_MEDIA.SINA);
//        mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT);
//
//        mController.openShare(Registered.this, false);
//    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
