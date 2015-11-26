package com.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.MyApplication;
import com.adapter.MainListAdapter;
import com.common.Log;
import com.common.UntilList;
import com.custom.ImageCycleView;
import com.custom.NoScrollListview;
import com.custom.slidingMenu.SlidingMenu;
import com.fragment.MenuFragment;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.adapter.LBaseAdapter;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xzdz.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends LActivity {
    private SlidingMenu mSlidingMenu;
    private SlidingMenu.CanvasTransformer mTransformer;
    private ImageCycleView mAdView;
    private ArrayList<String> pageImageList = new ArrayList<String>();
    private ArrayList<String> pageImageId = new ArrayList<String>();

    private PopupWindow pop;
    private ListView popListview;
    private SimpleAdapter popCityAda;
    private ScrollView scrollView;
    private TextView tv_city;
    private List<Map<String, String>> poplist = new ArrayList<Map<String, String>>();


    private List<Map<String, String>> newList = new ArrayList<Map<String, String>>();
    private NoScrollListview newListView;

    private MainListAdapter listAda;

    public static DisplayImageOptions options;
    public static ImageLoader imageLoader;

    protected void onLCreate(Bundle bundle) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        MyApplication.isLogin(mContext);
        if (imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }
        options = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        initView();
        initData();
        initLayout();
//        initPop();
    }

    private void initAda(ListView listView,List list) {
        listAda=new MainListAdapter(list,mContext);
        listView.setAdapter(listAda);
    }

    private void initData() {
        String url = getResources().getString(R.string.app_service_url) + "app/index/index/sign/aggregation/";
        Map<String, String> map = new HashMap<>();
        ActivityHandler handler = new ActivityHandler(this);
        LReqEntity entity = new LReqEntity(url, map);
        handler.startLoadingData(entity, 1);
    }

    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getData(msg.getStr());
            }
        }
    }

    private void getData(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            int status = jsonObject.optInt("status");
            if (status == 1) {
                JSONObject list = jsonObject.getJSONObject("list");
                JSONArray head_portraits = list.getJSONArray("head_portraits");
                for (int i = 0; i < head_portraits.length(); i++) {
                    JSONObject item = head_portraits.optJSONObject(i);
                    pageImageList.add(item.optString("surface_pic"));
                    pageImageId.add(item.optString("id"));
                }
                mAdView.setImageResources(pageImageList, pageImageId, mAdCycleViewListener);

                JSONArray recommond_list = list.getJSONArray("recommond_list");
                for (int i = 0; i < recommond_list.length(); i++) {
                    JSONObject item = recommond_list.optJSONObject(i);
                    Map<String, String> map = new HashMap<>();
                    map.put("image", item.optString("surface_pic"));
                    map.put("title1", item.optString("title"));
                    map.put("title2", item.optString("short_title"));
                    map.put("title3", item.optString("addtime"));
                    map.put("id", item.optString("id"));
                    newList.add(map);
                }
                initAda(newListView,newList);

            } else {
                T.ss("数据获取失败");
            }
        } catch (Exception e) {

        }
    }

    private void initView() {
        scrollView = (ScrollView) findViewById(R.id.scrollView_main);
        tv_city = (TextView) findViewById(R.id.tv_city);
        newListView = (NoScrollListview) findViewById(R.id.list_main);
        mAdView = (ImageCycleView) findViewById(R.id.ImageCycleView);
    }
    private void initPop() {
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_main_city_style, null);
        pop = new PopupWindow(v, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        popListview = (ListView) v.findViewById(R.id.listview_city);
        RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rel_popdis);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
            }
        });


        Map<String, String> map = new HashMap<String, String>();
        map.put("city", "北京");
        poplist.add(map);
        map = new HashMap<String, String>();
        map.put("city", "上海");
        poplist.add(map);
        map = new HashMap<String, String>();
        map.put("city", "广州");
        poplist.add(map);
        map = new HashMap<String, String>();
        map.put("city", "武汉");
        poplist.add(map);

        popCityAda = new SimpleAdapter(this, poplist, R.layout.activity_main_city_style_item, new String[]{"city"}, new int[]{R.id.tv_city_name});
        popListview.setAdapter(popCityAda);
        popListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.ss(poplist.get(position).get("city"));
            }
        });
    }

    public void open(View view) {
        mSlidingMenu.toggle();
    }

    public void initLayout() {
        mTransformer = new SlidingMenu.CanvasTransformer() {
            public void transformCanvas(Canvas canvas, float percentOpen) {
                float scale = (float) (percentOpen * 0.25 + 0.75);
                canvas.scale(scale, scale, canvas.getWidth() / 2, canvas.getHeight() / 2);
            }
        };
        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setBehindCanvasTransformer(mTransformer);
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mSlidingMenu.setFadeDegree(1f);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        mSlidingMenu.setMenu(R.layout.frame_menu);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_menu, new MenuFragment(mSlidingMenu)).commit();

    }

//    public void city(View v) {
//        RelativeLayout relativeLayout = (RelativeLayout) MainActivity.this.findViewById(R.id.rel_city);
//        pop.showAsDropDown(relativeLayout);
//    }

    public void goCustom(View v) {
    }

    public void Customing(View v) {
    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        public void onImageClick(int position, View imageView) {
        }
    };

}
