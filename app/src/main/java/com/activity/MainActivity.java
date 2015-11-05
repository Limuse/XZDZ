package com.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.MyApplication;
import com.common.UntilList;
import com.custom.ImageCycleView;
import com.custom.slidingMenu.SlidingMenu;
import com.fragment.MenuFragment;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.xzdz.R;

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

    private ListView listView;

    protected void onLCreate(Bundle bundle) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initLayout();
        initPop();
    }

    private void initView() {
        scrollView = (ScrollView) findViewById(R.id.scrollView_main);
        tv_city=(TextView)findViewById(R.id.tv_city);
        tv_city.setTextColor(Color.parseColor("#2A8BCC"));
        listView=(ListView)findViewById(R.id.list_main);
    }

    private void initPop() {
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_main_city_style, null);
        pop = new PopupWindow(v, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        popListview = (ListView) v.findViewById(R.id.listview_city);
        RelativeLayout relativeLayout=(RelativeLayout)v.findViewById(R.id.rel_popdis);
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
        pageImageList.add(0, "ssss");
        pageImageList.add(1, "ssss");
        pageImageId.add(0, "ssss");
        pageImageId.add(1, "ssss");
        pageImageList.add(2, "ssss");
        pageImageId.add(2, "ssss");
        mAdView = (ImageCycleView) findViewById(R.id.ImageCycleView);
        mAdView.setImageResources(pageImageList, pageImageId, mAdCycleViewListener);

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

    public void city(View v) {
        RelativeLayout relativeLayout = (RelativeLayout) MainActivity.this.findViewById(R.id.rel_city);
        pop.showAsDropDown(relativeLayout);
    }

    public void goCustom(View v) {
    }

    public void Customing(View v) {
    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        public void onImageClick(int position, View imageView) {
        }
    };

}
