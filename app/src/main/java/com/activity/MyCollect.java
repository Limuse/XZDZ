package com.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.adapter.MyCollectAdapter;
import com.entity.MyWorkedEntity;
import com.leo.base.activity.LActivity;
import com.xzdz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/10/27.
 * 我的收藏
 */
public class MyCollect extends LActivity {
    private GridView gview;
    private Button write;
    private Boolean flg = true;
    private List<MyWorkedEntity> list = new ArrayList<>();
    private MyCollectAdapter adapter;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_mycollect);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        toolbar.setTitle(getResources().getText(R.string.app_mycollect));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        write = (Button) findViewById(R.id.write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flg == true) {
                    write.setText("完成");
                    flg = false;
                } else if (flg == false) {
                    write.setText("编辑");
                    flg = true;
                }
            }
        });
    }

    private void initView() {
        initData();
        gview = (GridView) findViewById(R.id.gridView);
        adapter=new MyCollectAdapter(this,list);
        gview.setAdapter(adapter);
    }
    private void initData(){
        MyWorkedEntity entity=new MyWorkedEntity();
        entity.setTitle("单排一粒扣");
        entity.setTime("￥3456");
        list.add(entity);
        MyWorkedEntity entity1=new MyWorkedEntity();
        entity1.setTitle("单排一粒扣");
        entity1.setTime("￥3456");
       // list.add(entity1);

    }
}
