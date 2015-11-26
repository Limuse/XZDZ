package com.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.activity.Fabric;
import com.activity.FabricChange;
import com.adapter.CustomingAdapter;
import com.common.Token;
import com.entity.CustomingEntity;
import com.entity.VBanxinEntity;
import com.handle.ActivityHandler;
import com.handle.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
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
 * Created by huisou on 2015/10/30.
 * 礼服 西装 8
 */
public class Vc_Dress extends LFragment {
    private Button btn_next;
    private ViewPager viewPager;
    private List<Fragment> fragmentLists = new ArrayList<>();
    private TextView tv_text;
    private Map<Integer, Drawable> map = new HashMap<>();
    private List<VBanxinEntity> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vcdress, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initPage();
        initData();
    }

    private void initView() {
        fragmentLists.clear();
        btn_next = (Button) getActivity().findViewById(R.id.nextc3);
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewimgd);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Fabric.class);
                startActivity(intent);
                // T.ss("下一步");
            }
        });
    }

    private void initData() {
        //app/product/suit/sign/aggregation/
        Resources res = getResources();
        final Map<String, String> map = new HashMap<>();
        String url = res.getString(R.string.app_service_url)
                + "app/product/suit/sign/aggregation";
        map.put("id", "8");
        LReqEntity entity = new LReqEntity(url, map);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        FragmentHandler handler = new FragmentHandler(this);
        handler.startLoadingData(entity, 1);
    }

    private void initPage() {
//        map.put(1,getResources().getDrawable(R.mipmap.fc));
//        map.put(2,getResources().getDrawable(R.mipmap.fc));
//        map.put(3,getResources().getDrawable(R.mipmap.fc));
//        map.put(4,getResources().getDrawable(R.mipmap.fc));
        for (int i = 0; i < list.size(); i++) {
            VBanxinEntity entity = list.get(i);
            Vc_pager fragmentSpecificPage = new Vc_pager();
//            fragmentSpecificPage.getData();
            fragmentLists.add(fragmentSpecificPage);
        }
        WindowManager wm = Vc_Dress.this.getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(width / -10);
        viewPager.setAdapter(new MyAdapterd(Vc_Dress.this.getActivity().getSupportFragmentManager()));
        tv_text = (TextView) getActivity().findViewById(R.id.tv_textd);

    }

    private class MyAdapterd extends FragmentStatePagerAdapter {
        public MyAdapterd(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return fragmentLists.get(position);
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);

//            container = (ViewGroup) LayoutInflater.from(getActivity()).inflate(
//                    R.layout.fragment_specific_page_view, null);
//            ImageView img = (ImageView) container.findViewById(R.id.fc);
//            Drawable maps= map.get(position);
//
//            for(int i=0;i<map.size();i++){
//                img.setBackground(maps);
//            }


        }

        public int getCount() {
            return fragmentLists.size();
        }


    }

    private void getJsonData(String data) {
        list.clear();
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONArray array = jsonObject.getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    VBanxinEntity entity = new VBanxinEntity();
                    entity.setId(object.getString("id"));
                    entity.setThumbnail(object.getString("thumbnail"));
                    entity.setTitle(object.getString("title"));
                    list.add(entity);
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
