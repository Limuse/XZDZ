package com.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MyApplication;
import com.common.AboutActivitySy;
import com.common.Token;
import com.entity.MyCusdtomEntity;
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
import com.xzdz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/10/30.
 */
public class FabricChangeSubmit extends LActivity {
    private Button btn_submit;
    private ImageView imgv;
    private List<Map<String, String>> list1 = new ArrayList<>();
    private List<Map<String, String>> list2 = new ArrayList<>();
    private List<Map<String, String>> arraylist = new ArrayList<>();
    private LinearLayout grouplayout;
    private String parId, parId2;
    private String Type;
    private Dialog dialog;
    private String productId;

    // Type productId
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_fabricchangesubmit);
        AboutActivitySy.getInstance().addActivity(this);
        Type = "3";
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        toolbar.setTitle(getResources().getText(R.string.app_fabrichange));
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
        grouplayout = (LinearLayout) findViewById(R.id.middle_layout);
        imgv = (ImageView) findViewById(R.id.imgv);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitData();

                //T.ss("提交订单");
            }
        });
        productId = getIntent().getExtras().getString("productId");
        final ArrayList<MyCusdtomEntity.ListEntity.ChildEntity> all = (ArrayList<MyCusdtomEntity.ListEntity.ChildEntity>) getIntent().getExtras().getSerializable("allentity");
        String parentId = LSharePreference.getInstance(this).getString("cid_first",
                null);
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getTitle().equals("细节") && all.get(i).getPid().equals(parentId)) {
                MyCusdtomEntity.ListEntity.ChildEntity allcentity = new MyCusdtomEntity.ListEntity.ChildEntity();
                allcentity.setId(all.get(i).getId());
                allcentity.setTitle(all.get(i).getTitle());
                allcentity.setPid(all.get(i).getPid());
                allcentity.set_child(all.get(i).get_child());

                LSharePreference.getInstance(this).setString("cid_second", allcentity.getId());
                for (int j = 0; j < allcentity.get_child().size(); j++) {
                    if (allcentity.getId().equals(allcentity.get_child().get(j).getPid())) {
                        Map<String, String> map = new HashMap<>();
                        map.put("id", allcentity.get_child().get(j).getId());
                        map.put("title", allcentity.get_child().get(j).getTitle());
                        map.put("thumbnail", allcentity.get_child().get(j).getFace_pic());
                        map.put("pid", allcentity.get_child().get(j).getPid());
                        list1.add(map);
                    }

                }
            }
        }
        initHData();


    }


    public void DialogView() {
        /**
         *  获取屏幕的宽度让dialog适应
         */
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        View view = getLayoutInflater().inflate(R.layout.chenge_fabricsubmit, null);
        dialog = new Dialog(this,
                R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(screenWidth,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        initHData2(view);
    }

    private void initHData() {
        ImageView childImg;// 子视图ImageView
        View childView;// 子视图View
        TextView tv1;
        // 新增子布局对象到父布局中
        for (int i = 0; i < list1.size(); i++) {
            // 初始化子布局
            childView = LayoutInflater.from(this).inflate(
                    R.layout.imageview_fabr1, null);
            childImg = (ImageView) childView.findViewById(R.id.img_far);
            tv1 = (TextView) childView.findViewById(R.id.tv_text1);
            tv1.setText(list1.get(i).get("title"));
            parId = list1.get(0).get("id");

            /**
             * 图片需要处理
             */
            ImageLoader imageLoader = null;


            // 图片
            if (imageLoader == null) {
                imageLoader = MyApplication.getInstance().getImageLoader();
            }
            DisplayImageOptions optionss = new DisplayImageOptions.Builder().cacheInMemory(false)
                    .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    .build();
            imageLoader.displayImage(list1.get(i).get("thumbnail"), childImg, optionss);
            grouplayout.addView(childView);// 将childView添加到父布局

            final int po = i;
            childImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Title = list1.get(po).get("title");
                    // tv1.setText(Title);
                    parId = list1.get(po).get("id");
                    getDialogData();
                }
            });
        }
    }

    private void getDialogData() {

        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/product/suit/sign/aggregation/";
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Token.get(this));
        map.put("id", parId);
        LReqEntity entity = new LReqEntity(url, map);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);//VcEntity

    }


    public void getData(String str) {
        list2.clear();
        try {
            JSONObject jsonObject = new JSONObject(str);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                /**
                 * id: 11
                 title: 噶发噶是否打算
                 thumbnail:
                 */
                JSONArray array = jsonObject.getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    Map<String, String> map1 = new HashMap<>();
                    JSONObject ob = array.getJSONObject(i);
                    map1.put("id", ob.getString("id"));
                    map1.put("title", ob.getString("title"));
                    map1.put("thumbnail", ob.getString("thumbnail"));
                    list2.add(map1);
                }
                if (list2.size() > 0) {
                    DialogView();
                }
                dialog.show();
            } else {
                T.ss(jsonObject.getString("msg"));
            }
        } catch (Exception e) {

        }


    }


    private void initHData2(View view) {
        final String firstId = LSharePreference.getInstance(this).getString("cid_first", null);
        final String secondId = LSharePreference.getInstance(this).getString("cid_second", null);
        Button btn = (Button) view.findViewById(R.id.nextt);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        LinearLayout group2 = (LinearLayout) view.findViewById(R.id.middle_changes);
        ImageView childImg;// 子视图ImageView
        View childView;// 子视图View
        TextView tv1;
        // 新增子布局对象到父布局中

        for (int i = 0; i < list2.size(); i++) {
            // 初始化子布局
            childView = LayoutInflater.from(this).inflate(
                    R.layout.imageview_fabr2, null);
            childImg = (ImageView) childView.findViewById(R.id.img_img2);
            tv1 = (TextView) childView.findViewById(R.id.tv_text2);
            tv1.setText(list2.get(i).get("title"));
            parId2 = list2.get(0).get("id");
            /**
             * 图片需要处理
             */
            ImageLoader imageLoader = null;


            // 图片
            if (imageLoader == null) {
                imageLoader = MyApplication.getInstance().getImageLoader();
            }
            DisplayImageOptions optionss = new DisplayImageOptions.Builder().cacheInMemory(false)
                    .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    .build();
            imageLoader.displayImage(list2.get(i).get("thumbnail"), childImg, optionss);
            group2.addView(childView);// 将childView添加到父布局

            final int po = i;
            childImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Title = list2.get(po).get("title");
                    //tv1.setText(Title);
                    parId2 = list2.get(po).get("id");
                    Map<String, String> map = new HashMap<>();
                    map.put("cid_first", firstId);
                    map.put("cid_second", secondId);
                    map.put("part_id", parId2);
                    arraylist.add(map);
                }
            });
        }
    }


    private void submitData() {
        StringBuffer sb = new StringBuffer("{\"array_num\":[");
        String firstId = LSharePreference.getInstance(this).getString("cid_first", null);
        String secondId = LSharePreference.getInstance(this).getString("cid_second", null);
        if (arraylist.size() < list1.size()) {
            for (int i = 0; i < (list1.size() - arraylist.size()); i++) {
                Map<String, String> map = new HashMap<>();
                map.put("cid_first", firstId);
                map.put("cid_second", secondId);
                map.put("part_id", "");
                arraylist.add(map);
            }
            for (int i = 0; i < arraylist.size(); i++) {

                Map<String, String> m = arraylist.get(i);
                sb.append("{");
                for (Map.Entry<String, String> e : m.entrySet()) {
                    String key = e.getKey();
                    String value = e.getValue();

                    sb.append("\"" + key + "\"" + ":" + "\"" + value + "\"" + ",");


                }
                //删除最后一个多的逗号，
                sb.deleteCharAt(sb.length() - 1);
                sb.append("},");
            }
            //删除最后一个多的逗号，
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]}");
        } else {

            for (int i = 0; i < arraylist.size(); i++) {

                Map<String, String> m = arraylist.get(i);
                sb.append("{");
                for (Map.Entry<String, String> e : m.entrySet()) {
                    String key = e.getKey();
                    String value = e.getValue();

                    sb.append("\"" + key + "\"" + ":" + "\"" + value + "\"" + ",");


                }
                //删除最后一个多的逗号，
                sb.deleteCharAt(sb.length() - 1);
                sb.append("},");
            }
            //删除最后一个多的逗号，
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]}");
        }

        Map<String, String> map = new HashMap<>();

        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/product/preservation/sign/aggregation/?uuid="
                + Token.get(this) +
                "&type=" + "3" +
                "&cid_first=" + firstId +
                "&cid_second=" + secondId +
                "&product_id=" + productId;
        map.put("array_num", sb.toString());
        LReqEntity entity = new LReqEntity(url,map);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 2);
        // L.e(url);
    }


    private void getJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject object = jsonObject.getJSONObject("list");
                String order_id = object.getString("order_id");
                Intent intent = new Intent(FabricChangeSubmit.this, OrderDetails.class);
                intent.putExtra("order_id", order_id);
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
            } else {
                T.ss("网络请求错误");
            }
        }
    }

}
