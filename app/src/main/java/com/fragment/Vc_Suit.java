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
import android.widget.TextView;

import com.activity.Fabric;
import com.activity.ViersonChanges;
import com.common.Token;
import com.entity.MyCusdtomEntity;
import com.entity.VcEntity;
import com.handle.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.LSharePreference;
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
 * 正装
 */
public class Vc_Suit extends LFragment implements ViewPager.OnPageChangeListener {
    private Button btn_next;
    private ViewPager viewPager;
    private List<Fragment> fragmentLists = new ArrayList<>();
    private List<MyCusdtomEntity.ListEntity.ChildEntity.childV> cVchild;
    private VcEntity vcEntity;
    private List<VcEntity> list = new ArrayList<>();
    public static Vc_Suit getIntenteS;
    private String partId;
    private String product_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vcsuit, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getIntenteS = this;
        LSharePreference.getInstance(getActivity()).setString("tID", "2");
        initView();
        initData();
    }

    private void initView() {
        fragmentLists.clear();
        btn_next = (Button) getActivity().findViewById(R.id.nextc2);
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewimgs);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String productId = ViersonChanges.vChanges.returnData();
                save();
//                if (productId != null) {
//                    ArrayList<MyCusdtomEntity.ListEntity.ChildEntity> allentity = ViersonChanges.vChanges.retrnEntity();
//                    Intent intent = new Intent(getActivity(), Fabric.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("productId", productId);
//                    bundle.putSerializable("allentity", allentity);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                } else {
//                    T.ss("请选择并保存");
//                }

            }
        });
    }

    public String tsData() {
        return partId;
    }


    public void save() {
        /**
         * type
         product_id
         cid_first
         cid_second
         part_id
         array_num
         */
//        String parId = null;
//        String TID = LSharePreference.getInstance(getActivity()).getString("tID", null);
//        if (TID.equals("1")) {
//            parId = Vc_Releaxe.getIntenceR.tsData();
//        } else if (TID.equals("2")) {
//            parId = Vc_Suit.getIntenteS.tsData();
//        } else if (TID.equals("3")) {
//            parId = Vc_Dress.getIntenceD.tsData();
//        }
        String firstId = LSharePreference.getInstance(getActivity()).getString("cid_first", null);
        String secondId = LSharePreference.getInstance(getActivity()).getString("cid_second", null);
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/product/preservation/sign/aggregation/?uuid="
                + Token.get(getActivity()) +
                "&type=" + "1" +
                "&cid_first=" + firstId +
                "&cid_second=" + secondId +
                "&part_id=" + partId;
        LReqEntity entity = new LReqEntity(url);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        FragmentHandler handler = new FragmentHandler(this);
        handler.startLoadingData(entity, 2);
        // T.ss("保存");
    }


    private void getJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject object = jsonObject.getJSONObject("list");
                product_id = object.getString("product_id");
                ArrayList<MyCusdtomEntity.ListEntity.ChildEntity> allentity = ViersonChanges.vChanges.retrnEntity();
                Intent intent = new Intent(getActivity(), Fabric.class);
                Bundle bundle = new Bundle();
                bundle.putString("productId", product_id);
                bundle.putSerializable("allentity", allentity);
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

    private void initData() {
        cVchild = ViersonChanges.vChanges.SendChildV();
        String pid = LSharePreference.getInstance(getActivity()).getString("vsNumID", null);
        MyCusdtomEntity.ListEntity.ChildEntity.childV cvh = new MyCusdtomEntity.ListEntity.ChildEntity.childV();
        for (int i = 0; i < cVchild.size(); i++) {
            if (cVchild.get(i).getTitle().equals("正装") && cVchild.get(i).getPid().equals(pid)) {
                cvh.setId(cVchild.get(i).getId());
                cvh.setPid(cVchild.get(i).getPid());
                cvh.setTitle(cVchild.get(i).getTitle());
            }
        }
        LSharePreference.getInstance(getActivity()).setString("cid_second", cvh.getPid());
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/product/suit/sign/aggregation/";
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Token.get(getActivity()));
        map.put("id", cvh.getId());
        LReqEntity entity = new LReqEntity(url, map);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        FragmentHandler handler = new FragmentHandler(this);
        handler.startLoadingData(entity, 1);//VcEntity
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

    public void getData(String str) {
        list.clear();
        try {
            JSONObject jsonObject = new JSONObject(str);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                JSONArray array = jsonObject.getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject ob = array.getJSONObject(i);
                    vcEntity = new VcEntity();
                    String id = ob.getString("id");
                    String title = ob.getString("title");
                    String thumbnail = ob.getString("thumbnail");
                    vcEntity.setId(id);
                    vcEntity.setTitle(title);
                    vcEntity.setThumbnail(thumbnail);
                    list.add(vcEntity);
                }

                initPage();
            } else {
                T.ss(jsonObject.getString("msg"));
            }
        } catch (Exception e) {

        }


    }

    private void initPage() {

        for (int i = 0; i < list.size(); i++) {
            Vc_pagerS fragmentSpecificPage = new Vc_pagerS();
            fragmentSpecificPage.getData(list.get(i));
            fragmentLists.add(fragmentSpecificPage);
        }
        partId = list.get(0).getId();
        WindowManager wm = Vc_Suit.this.getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(width / 10);
        viewPager.setAdapter(new MyAdapters(Vc_Suit.this.getActivity().getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //viewPager.getChildAt(position).animate().scaleX(1f).scaleY(1f).start();
        //arg0是表示你当前选中的页面，这事件是在你页面跳转完毕的时候调用的。
        // list.get(position).getId();
    }

    @Override
    public void onPageSelected(int position) {
        // viewPager.getChildAt(position).animate().scaleX(1f).scaleY(1f).start();
        //表示在前一个页面滑动到后一个页面的时候，在前一个页面滑动前调用的方法。
        partId = list.get(position).getId();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //arg0 ==1的时候表示正在滑动，arg0==2的时候表示滑动完毕了，arg0==0的时候表示什么都没做，就是停在那。

    }

    private class MyAdapters extends FragmentStatePagerAdapter {
        public MyAdapters(FragmentManager fm) {
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

}
