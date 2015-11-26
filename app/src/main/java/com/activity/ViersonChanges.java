package com.activity;

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

import com.common.Bar;
import com.entity.MyCusdtomEntity;
import com.fragment.Vc_Dress;
import com.fragment.Vc_Releaxe;
import com.fragment.Vc_Suit;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.xzdz.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by huisou on 2015/11/13.
 */
public class ViersonChanges extends LActivity {
    public static ViersonChanges context;
    private String Type = "1";
    private List<MyCusdtomEntity.ListEntity> list = new ArrayList<>();
    private List<MyCusdtomEntity.ListEntity.ChildEntity> childlist = new ArrayList<>();
    private List<MyCusdtomEntity.ListEntity.ChildEntity.childV> chilvv=new ArrayList<>();
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


    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_viersonchanges);
        context = this;
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
            lentity.getId();
        lentity.getPid();
        lentity.getTitle();
        for(int i=0;i<lentity.get_child().size();i++){
            chilvv=lentity.get_child().get(i).get_child();
        }

        initView();
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


    public void save(View v) {
        T.ss("保存");
    }
}
