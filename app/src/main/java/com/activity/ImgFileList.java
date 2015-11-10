package com.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.adapter.ImgFileListAdapter;
import com.common.FileTraversal;
import com.common.ServiceReleaseUtil;
import com.leo.base.activity.LActivity;
import com.xzdz.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huisou on 2015/11/9.
 */
public class ImgFileList extends LActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ServiceReleaseUtil util;
    private ImgFileListAdapter listAdapter;
    private List<FileTraversal> locallist;
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_imgfilelist);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_imgfile));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initView(){

        listView = (ListView) findViewById(R.id.listView1);
        util = new ServiceReleaseUtil(this);
        locallist = util.LocalImgFileList();
        List<HashMap<String, String>> listdata = new ArrayList<HashMap<String, String>>();
        @SuppressWarnings("unused")
        Bitmap bitmap[] = null;
        if (locallist != null) {
            bitmap = new Bitmap[locallist.size()];
            for (int i = 0; i < locallist.size(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("filecount", locallist.get(i).filecontent.size() + "张");
                map.put("imgpath",
                        locallist.get(i).filecontent.get(0) == null ? null
                                : (locallist.get(i).filecontent.get(0)));
                map.put("filename", locallist.get(i).filename);
                listdata.add(map);
            }
        }
        listAdapter = new ImgFileListAdapter(this, listdata);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Intent intent = new Intent(this, ReleaseServiceImages.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", locallist.get(arg2));
        intent.putExtras(bundle);
        startActivityForResult(intent, 5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    ArrayList<String> list = new ArrayList<String>();
                    list = bundle.getStringArrayList("files");
                    Bundle sBundle = new Bundle();
                    sBundle.putStringArrayList("files", list);
                    Intent intent = new Intent(ImgFileList.this,
                            AfterSale.class);
                    intent.putExtras(sBundle);
                    ImgFileList.this.setResult(5, intent);
                    finish();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
