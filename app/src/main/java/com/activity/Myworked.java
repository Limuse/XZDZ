package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.adapter.MyWorkedGridvAdapter;
import com.entity.MyWorkedEntity;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.xzdz.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/10/27.
 * 我的作品
 */
public class Myworked extends LActivity {
    private GridView gridView;
    private MyWorkedGridvAdapter adapter;
    private Button write;
    private List<Map<String, String>> mlist = new ArrayList<>();
    private String title[] = null;
    private String time[] = null;
    private String id[] = null;
    private  Boolean flg=true;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_myworked);
        initBar();
        initView();

    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbars);
        toolbar.setTitle(getResources().getText(R.string.app_myworked));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
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
        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new MyWorkedGridvAdapter(this, mlist);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }





    private void initData() {
        title = new String[]{"单排一粒西装", "单排一粒西装", "单排一粒西装", "单排一粒西装"};
        time = new String[]{"2015-04-24  09:23:12", "2015-04-24  09:23:12", "2015-04-24  09:23:12", "2015-04-24  09:23:12"};
        id = new String[]{"1", "2", "3", "4"};
        for (int i = 0; i < title.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", title[i]);
            map.put("time", time[i]);
            map.put("id", id[i]);
            mlist.add(map);
        }
    }

}
