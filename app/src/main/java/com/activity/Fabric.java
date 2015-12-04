package com.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MyApplication;
import com.common.AboutActivitySy;
import com.common.Token;
import com.entity.MyCusdtomEntity;
import com.entity.VcEntity;
import com.handle.ActivityHandler;
import com.handle.FragmentHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
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
 * Created by huisou on 2015/10/30.
 * 面料选择
 */
public class Fabric extends LActivity {
    private Button btn_next;
    private ImageView img1;
    private LinearLayout grouplayout;
    private TextView tv1, tv2;
    private List<Map<String, String>> list = new ArrayList<>();
    private String pId;
    private String Type = "2";
    private String parId;
    private String PudIdNew;
    private ArrayList<MyCusdtomEntity.ListEntity.ChildEntity> all;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_fabric);
        AboutActivitySy.getInstance().addActivity(this);
        initBar();
        initView();

    }


    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        toolbar.setTitle(getResources().getText(R.string.app_fabric));
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
        pId = getIntent().getExtras().getString("productId");
        // T.ss(pId);
        all = (ArrayList<MyCusdtomEntity.ListEntity.ChildEntity>) getIntent().getExtras().getSerializable("allentity");
        MyCusdtomEntity.ListEntity.ChildEntity allcentity = new MyCusdtomEntity.ListEntity.ChildEntity();
        String parentId = LSharePreference.getInstance(this).getString("cid_first",
                null);
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getTitle().equals("面料") && all.get(i).getPid().equals(parentId)) {
                allcentity.setId(all.get(i).getId());
                allcentity.setTitle(all.get(i).getTitle());
                allcentity.setPid(all.get(i).getPid());
                // allcentity.set_child(all.get(i).get_child());
            }
        }
//        L.e(allcentity.getTitle() + allcentity.getId() + allcentity.getPid());
        initData(allcentity.getId());
        LSharePreference.getInstance(this).setString("cid_second", allcentity.getId());
        btn_next = (Button) findViewById(R.id.btn_next);
        img1 = (ImageView) findViewById(R.id.img1);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        grouplayout = (LinearLayout) findViewById(R.id.middle_layout);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savez();
            }
        });

    }

    private void initHData() {
        ImageView childImg;// 子视图ImageView
        View childView;// 子视图View
        // 新增子布局对象到父布局中
        for (int i = 0; i < list.size(); i++) {
            // 初始化子布局
            childView = LayoutInflater.from(this).inflate(
                    R.layout.imageview_h, null);
            childImg = (ImageView) childView.findViewById(R.id.child_img);
            /**
             *  在没有点击的状态
             */

            /**
             * 图片需要处理
             */
            ImageLoader imageLoader = null;

            // 图片
            if (imageLoader == null) {
                imageLoader = MyApplication.getInstance().getImageLoader();
            }
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(false)
                    .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    .build();
            imageLoader.displayImage(list.get(0).get("thumbnail"), img1, options);
            tv1.setText(list.get(0).get("title"));
            parId = list.get(0).get("id");
            /**
             * 图片需要处理
             */


            // 图片
            if (imageLoader == null) {
                imageLoader = MyApplication.getInstance().getImageLoader();
            }
            DisplayImageOptions optionss = new DisplayImageOptions.Builder().cacheInMemory(false)
                    .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    .build();
            imageLoader.displayImage(list.get(i).get("thumbnail"), childImg, optionss);
            grouplayout.addView(childView);// 将childView添加到父布局

            final int po = i;
            childImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Title = list.get(po).get("title");
                    tv1.setText(Title);
                    parId = list.get(po).get("id");

                    /**
                     * 图片需要处理
                     */
                    ImageLoader imageLoader = null;

                    // 图片
                    if (imageLoader == null) {
                        imageLoader = MyApplication.getInstance().getImageLoader();
                    }
                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(false)
                            .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
                            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                            .build();
                    imageLoader.displayImage(list.get(po).get("thumbnail"), img1, options);
                }
            });
        }
    }

    private void initData(String string) {
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/product/suit/sign/aggregation/";
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Token.get(this));
        map.put("id", string);
        LReqEntity entity = new LReqEntity(url, map);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);//VcEntity
        // L.e(entity.toString());
    }


    public void getData(String str) {
        list.clear();
        try {
            JSONObject jsonObject = new JSONObject(str);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                JSONArray array = jsonObject.getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    Map<String, String> map = new HashMap<>();
                    JSONObject ob = array.getJSONObject(i);
                    map.put("id", ob.getString("id"));
                    map.put("title", ob.getString("title"));
                    map.put("thumbnail", ob.getString("thumbnail"));
                    list.add(map);
                }
                initHData();
            } else {
                T.ss(jsonObject.getString("msg"));
            }
        } catch (Exception e) {

        }


    }

    public void savez() {
        //  T.ss("保存");
        String firstId = LSharePreference.getInstance(this).getString("cid_first", null);
        String secondId = LSharePreference.getInstance(this).getString("cid_second", null);
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/product/preservation/sign/aggregation/?uuid="
                + Token.get(this) +
                "&type=" + Type +
                "&cid_first=" + firstId +
                "&cid_second=" + secondId +
                "&part_id=" + parId +
                "&product_id=" + pId;
        LReqEntity entity = new LReqEntity(url);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 2);
        // L.e(entity.toString());
        // T.ss("保存");
    }


    private void getJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject object = jsonObject.getJSONObject("list");
                PudIdNew = object.getString("product_id");
                Intent intent = new Intent(Fabric.this, FabricChangeSubmit.class);
                Bundle bundle = new Bundle();
                bundle.putString("productId", PudIdNew);
                bundle.putSerializable("allentity", all);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                T.ss("跳转失败");
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

    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getData(msg.getStr());
            } else if (requestId == 2) {
                getJsonData(msg.getStr());
            }
        }
    }
}
