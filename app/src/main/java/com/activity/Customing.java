package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.adapter.CustomingAdapter;
import com.entity.CustomingEntity;
import com.leo.base.activity.LActivity;
import com.xzdz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/10/27.
 * 正在定制
 */
public class Customing extends LActivity implements AdapterView.OnItemClickListener {
    private ListView listview;
    private CustomingAdapter adapter;
    private List<CustomingEntity> list;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_customing);
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
        initData();
        listview = (ListView) findViewById(R.id.listview);
        adapter = new CustomingAdapter(this, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    private void initData() {
        list = new ArrayList<>();
        CustomingEntity entity = new CustomingEntity();
        entity.setNum("091674367");
        entity.setState("准备量体");
        entity.setTime("2015-10-23");
        entity.setTitle("量体印花西服");
        list.add(entity);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CustomingEntity cu=list.get(position);
        Intent intent=new Intent(this,CustomingState.class);
        startActivity(intent);
    }
}
