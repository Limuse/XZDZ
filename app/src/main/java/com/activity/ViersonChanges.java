package com.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.common.AboutActivitySy;
import com.common.Bar;
import com.common.Token;
import com.entity.MyCusdtomEntity;
import com.fragment.Vc_Dress;
import com.fragment.Vc_Releaxe;
import com.fragment.Vc_Suit;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.xzdz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by huisou on 2015/11/13.
 */
public class ViersonChanges extends LActivity {
    private String Type = "1";
    private List<MyCusdtomEntity.ListEntity> list = new ArrayList<>();
    private List<MyCusdtomEntity.ListEntity.ChildEntity> childlist = new ArrayList<>();
    private List<MyCusdtomEntity.ListEntity.ChildEntity.childV> chilvv = new ArrayList<>();
    @InjectView(R.id.relax)
    Button relax;
    @InjectView(R.id.suit)
    Button suit;
    @InjectView(R.id.dress)
    Button dress;


    @InjectView(R.id.rlrelax)
    RelativeLayout rlrelax;
    @InjectView(R.id.rldress)
    RelativeLayout rldress;
    @InjectView(R.id.rlsuit)
    RelativeLayout rlsuit;

    @InjectView(R.id.vc_view)
    LinearLayout ly_vcview;

    public Vc_Releaxe vc_releaxe;
    public Vc_Dress vc_dress;
    public Vc_Suit vc_suit;

    public static final String RELAX = "relax";
    public static final String DRESS = "dress";
    public static final String SUIT = "suit";


    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private String hideTag;

    public static ViersonChanges vChanges;
    private String product_id = null;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_viersonchanges);
        AboutActivitySy.getInstance().addActivity(this);
        vChanges = this;
        ButterKnife.inject(this);
        initBar();
        initData();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarc);
        toolbar.setTitle(getResources().getText(R.string.app_viersonchange));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        MyCusdtomEntity.ListEntity lentity = (MyCusdtomEntity.ListEntity) getIntent().getSerializableExtra("entity");
        childlist = lentity.get_child();
        lentity.getId();
        lentity.getPid();
        lentity.getTitle();
        //   L.e(lentity.getTitle());
        for (int i = 0; i < childlist.size(); i++) {
            if (childlist.get(i).getPid().equals(lentity.getId()) && childlist.get(i).getTitle().equals("版型")) {
                chilvv = childlist.get(i).get_child();
                LSharePreference.getInstance(this).setString("vsNumID", childlist.get(i).getId());
                //   L.e(childlist.get(i).getTitle()+childlist.get(i).getId()+"ffffffffffff");
            }

        }

        initView();
    }

    public List<MyCusdtomEntity.ListEntity.ChildEntity.childV> SendChildV() {
        return chilvv;

    }

    public void setBg() {
        relax.setBackgroundColor(getResources().getColor(R.color.app_toolbar));
        relax.setTextColor(getResources().getColor(R.color.app_white));

        dress.setBackgroundColor(getResources().getColor(R.color.app_toolbar));
        dress.setTextColor(getResources().getColor(R.color.app_white));

        suit.setBackgroundColor(getResources().getColor(R.color.app_toolbar));
        suit.setTextColor(getResources().getColor(R.color.app_white));

    }


    private void initView() {
        vc_releaxe = new Vc_Releaxe();
        relax.setBackgroundResource(R.mipmap.tbb);
        relax.setTextColor(getResources().getColor(R.color.app_toolbar));
        switchFragemnt(vc_releaxe, RELAX);
    }


    public void switchFragemnt(Fragment fragment, String tag) {

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        Fragment tagFragment = mFragmentManager.findFragmentByTag(tag);

        if (tagFragment == null) {
            mFragmentTransaction.add(R.id.vchange, fragment, tag);
        } else {
            mFragmentTransaction.show(tagFragment);
        }

        tagFragment = mFragmentManager.findFragmentByTag(hideTag);

        if (tagFragment != null) {
            mFragmentTransaction.hide(tagFragment);
        }

        hideTag = tag;
        mFragmentTransaction.commit();


    }

    @OnClick(R.id.rldress)
    void dress() {
        if (hideTag.equals(DRESS))
            return;
        if (vc_dress == null) {
            vc_dress = new Vc_Dress();
        }
        setBg();
        dress.setBackgroundResource(R.mipmap.tbb);
        dress.setTextColor(getResources().getColor(R.color.app_toolbar));
        switchFragemnt(vc_dress, DRESS);
    }

    @OnClick(R.id.rlsuit)
    void rlsuit() {
        if (hideTag.equals(SUIT))
            return;
        if (vc_suit == null) {
            vc_suit = new Vc_Suit();
        }
        setBg();
        suit.setBackgroundResource(R.mipmap.tbb);
        suit.setTextColor(getResources().getColor(R.color.app_toolbar));
        switchFragemnt(vc_suit, SUIT);
    }

    @OnClick(R.id.rlrelax)
    void rlrelax() {
        if (hideTag.equals(RELAX))
            return;
        if (vc_releaxe == null) {
            vc_releaxe = new Vc_Releaxe();
        }

        setBg();
        relax.setBackgroundResource(R.mipmap.tbb);
        relax.setTextColor(getResources().getColor(R.color.app_toolbar));
        switchFragemnt(vc_releaxe, RELAX);
    }


//    public void save(View v) {
//        /**
//         * type
//         product_id
//         cid_first
//         cid_second
//         part_id
//         array_num
//         */
//        String parId = null;
//        String TID = LSharePreference.getInstance(this).getString("tID", null);
//        if (TID.equals("1")) {
//            parId = Vc_Releaxe.getIntenceR.tsData();
//        } else if (TID.equals("2")) {
//            parId = Vc_Suit.getIntenteS.tsData();
//        } else if (TID.equals("3")) {
//            parId = Vc_Dress.getIntenceD.tsData();
//        }
//        String firstId = LSharePreference.getInstance(this).getString("cid_first", null);
//        String secondId = LSharePreference.getInstance(this).getString("cid_second", null);
//        Resources res = getResources();
//        String url = res.getString(R.string.app_service_url)
//                + "/app/product/preservation/sign/aggregation/?uuid="
//                + Token.get(this) +
//                "&type=" + Type +
//                "&cid_first=" + firstId +
//                "&cid_second=" + secondId +
//                "&part_id=" + parId;
//        LReqEntity entity = new LReqEntity(url);
//        // Fragment用FragmentHandler/Activity用ActivityHandler
//        ActivityHandler handler = new ActivityHandler(this);
//        handler.startLoadingData(entity, 1);
//        // T.ss("保存");
//    }

//    // 返回获取的网络数据
//    public void onResultHandler(LMessage msg, int requestId) {
//        super.onResultHandler(msg, requestId);
//        if (msg != null) {
//            if (requestId == 1) {
//                getJsonData(msg.getStr());
//            } else {
//                T.ss("获取数据失败");
//            }
//        }
//    }

//    private void getJsonData(String data) {
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            int code = jsonObject.getInt("status");
//            if (code == 1) {
//                JSONObject object = jsonObject.getJSONObject("list");
//                product_id = object.getString("product_id");
//                T.ss("已保存");
//            } else {
//                //  T.ss(jsonObject.getString("info").toString());
//                //String longs = jsonObject.getString("info");
////                if (longs.equals("请先登录")) {
////                    LSharePreference.getInstance(this).setBoolean("login", false);
////                    Intent intent = new Intent(this, LoginMain.class);
////                    startActivity(intent);
////                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//    public String returnData() {
//        String ProductId = null;
//        if (product_id != null) {
//            ProductId = product_id;
//        }
//        return ProductId;
//    }

    public ArrayList<MyCusdtomEntity.ListEntity.ChildEntity> retrnEntity() {
        return (ArrayList) childlist;
    }


}
