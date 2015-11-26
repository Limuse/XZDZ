package com.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MyApplication;
import com.adapter.CustomStateAdapter;
import com.adapter.CustomingAdapter;
import com.common.Token;
import com.custom.CustomDialog;
import com.entity.CustomingEntity;
import com.entity.MetailflowEntity;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xzdz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/11/3.
 */
public class CustomingState extends LActivity implements View.OnClickListener {
    private LinearLayout rl_s1, rl_s2, rl_s3, rl_s4, rl_s5;
    private TextView tv1_name, tv1_phone, tv1_time, tv1_addr, tv1_kfphone;
    private TextView tv2_kfphone;
    private TextView tv3_name, tv3_phone, tv3_addr, tv3_kfphone;
    private TextView tv4_kfphone, tv4_wstate, tv4_gogns, tv4_num, tv4_wphone;
    private ImageView img4;
    private ListView listview4;
    private TextView tv5_kfphone;
    private Button btn5_sback;
    private String staeOr;
    private CustomStateAdapter adapter;
    private List<MetailflowEntity.DataEntity> listMf = new ArrayList<>();

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_customingstate);
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
//        intent.putExtra("state", cu.getState());
//        intent.putExtra("id", cu.getId());
//        intent.putExtra("order_id", cu.getNum());
        String states = getIntent().getStringExtra("state");
        String id = getIntent().getStringExtra("id");
        String order_id = getIntent().getStringExtra("order_id");
        rl_s1 = (LinearLayout) findViewById(R.id.lly_s1);
        rl_s2 = (LinearLayout) findViewById(R.id.lly_s2);
        rl_s3 = (LinearLayout) findViewById(R.id.lly_s3);
        rl_s4 = (LinearLayout) findViewById(R.id.lly_s4);
        rl_s5 = (LinearLayout) findViewById(R.id.lly_s5);
        /**
         * 根据状态判断是否隐藏
         */
        if (states.equals("已完成")) {
            rl_s5.setVisibility(View.VISIBLE);
            staeOr = "5";
        }
        if (states.equals("已发货")) {
            rl_s4.setVisibility(View.VISIBLE);
            staeOr = "4";
        }
        if (states.equals("制衣中")) {
            rl_s3.setVisibility(View.VISIBLE);
            staeOr = "3";
        }
        if (states.equals("量体完成")) {
            rl_s2.setVisibility(View.VISIBLE);
            staeOr = "2";
        }
        if (states.equals("准备量体")) {
            rl_s1.setVisibility(View.VISIBLE);
            staeOr = "1";
        }
        tv1_name = (TextView) findViewById(R.id.sc_name1);
        tv1_phone = (TextView) findViewById(R.id.sc_phone1);
        tv1_time = (TextView) findViewById(R.id.sc_time1);
        tv1_addr = (TextView) findViewById(R.id.sc_addr1);
        tv1_kfphone = (TextView) findViewById(R.id.sc_palyphone1);
        tv1_kfphone.setOnClickListener(this);
        tv2_kfphone = (TextView) findViewById(R.id.sc_palyphone2);
        tv2_kfphone.setOnClickListener(this);
        tv3_name = (TextView) findViewById(R.id.names3);
        tv3_phone = (TextView) findViewById(R.id.phone3);
        tv3_addr = (TextView) findViewById(R.id.addr3);
        tv3_kfphone = (TextView) findViewById(R.id.sc_palyphone3);
        tv3_kfphone.setOnClickListener(this);
        tv4_kfphone = (TextView) findViewById(R.id.sc_palyphone4);
        tv4_kfphone.setOnClickListener(this);
        tv4_wstate = (TextView) findViewById(R.id.wlstate4);
        tv4_gogns = (TextView) findViewById(R.id.kud4);
        tv4_num = (TextView) findViewById(R.id.num4);
        tv4_wphone = (TextView) findViewById(R.id.wphone4);
        img4 = (ImageView) findViewById(R.id.imgs4);
        listview4 = (ListView) findViewById(R.id.wlistvew);
        tv5_kfphone = (TextView) findViewById(R.id.sc_palyphone5);
        tv5_kfphone.setOnClickListener(this);
        btn5_sback = (Button) findViewById(R.id.shouh);
        btn5_sback.setOnClickListener(this);
        initData(order_id);
    }

    private void initData(String oid) {
        Resources res = getResources();
//        final Map<String, String> map = new HashMap<>();
        String url = res.getString(R.string.app_service_url)
                + "/app/order/orderdetail/sign/aggregation/?" + "uuid=" + Token.get(this)  + "&order_id=" + oid;
//        map.put("uuid", Token.get(this));
//        map.put("order_id", oid);
        LReqEntity entity = new LReqEntity(url);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);

    }

    private void getJsonData(String data) {
         listMf.clear();
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                if (staeOr.equals("4")) {
                    JSONObject js = jsonObject.getJSONObject("list");
//                if(array.length()<1){
//
//                }else
                    JSONObject jo = js.getJSONObject("delivery");
                    L.e("ddddddd");
                    MetailflowEntity mens = new MetailflowEntity();
                    mens.setImg(jo.getString("img"));
                    mens.setState(jo.getString("state"));
                    mens.setDelivery_name(jo.getString("delivery_name"));
                    mens.setNum(jo.getString("nu"));
                    tv4_gogns.setText(mens.getDelivery_name());
                    tv4_wstate.setText(mens.getState());
                    tv4_num.setText(mens.getNum());
                    img4(mens.getImg());
                    JSONArray array = jo.getJSONArray("data");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        MetailflowEntity.DataEntity entity = new MetailflowEntity.DataEntity();
                        entity.setContext(object.getString("context"));
                        entity.setTime(object.getString("time"));
                        entity.setFtime(object.getString("ftime"));
                        listMf.add(entity);
                    }
                    adapter = new CustomStateAdapter(this, listMf);
                    listview4.setAdapter(adapter);
                }


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

    private void img4(String imgview) {
        /**
         * 图片需要处理
         */
        ImageLoader imageLoader = null;

        // 图片
        if (imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.fc)
                .showImageForEmptyUri(R.mipmap.fc)
                .showImageOnFail(R.mipmap.fc)
                .cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(200))
                .build();
        imageLoader.displayImage(imgview, img4, options);
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
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sc_palyphone1) {
            T.ss("客服电话");
            String p = tv1_kfphone.getText().toString();
            phoneNum(p);
        }
        if (id == R.id.sc_palyphone2) {
            T.ss("客服电话");
            String p = tv2_kfphone.getText().toString();
            phoneNum(p);
        }
        if (id == R.id.sc_palyphone3) {
            T.ss("客服电话");
            String p = tv3_kfphone.getText().toString();
            phoneNum(p);
        }
        if (id == R.id.sc_palyphone4) {
            T.ss("客服电话");
            String p = tv4_kfphone.getText().toString();
            phoneNum(p);
        }
        if (id == R.id.sc_palyphone5) {
            T.ss("客服电话");
            String p = tv5_kfphone.getText().toString();
            phoneNum(p);
        }
        if (id == R.id.shouh) {
            //售后
            Intent intent = new Intent(this, AfterSale.class);
            startActivity(intent);
        }

    }


    private void phoneNum(final String phones) {
        final CustomDialog alertDialog = new CustomDialog.Builder(this).
                setMessage("确定联系客服吗？").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(
                                getApplicationContext(),
                                "正在呼叫  " + phones,
                                Toast.LENGTH_LONG)
                                .show();
                        Uri uri = Uri.parse("tel:"
                                + phones);
                        Intent intent = new Intent(
                                Intent.ACTION_CALL, uri);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        alertDialog.show();
    }
}
