
package com.activity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.adapter.ImgsAdapter;
import com.common.FileTraversal;
import com.common.ImgCallBack;
import com.common.ServiceReleaseUtil;
import com.xzdz.R;

public class ReleaseServiceImages extends Activity {
    private SharedPreferences sp;
    private Bundle bundle;
    private FileTraversal fileTraversal;
    private GridView imgGridView;
    private ImgsAdapter imgsAdapter;
    private LinearLayout select_layout;
    private ServiceReleaseUtil util;
    private RelativeLayout relativeLayout2;
    private HashMap<Integer, ImageView> hashImage;
    private Button choise_button;
    private ArrayList<String> filelist;
    private int photoCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photogrally);


        initBar();
        init();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarc);
        toolbar.setTitle(getResources().getString(R.string.app_imgfile));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        sp = getSharedPreferences("listCount", 1);
        photoCount = sp.getInt("count", 0);
        imgGridView = (GridView) findViewById(R.id.gridView1);
        bundle = getIntent().getExtras();
        fileTraversal = bundle.getParcelable("data");
        imgsAdapter = new ImgsAdapter(this, fileTraversal.filecontent,
                onItemClickClass);
        imgGridView.setAdapter(imgsAdapter);
        select_layout = (LinearLayout) findViewById(R.id.selected_image_layout);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        choise_button = (Button) findViewById(R.id.button3);
        hashImage = new HashMap<Integer, ImageView>();
        filelist = new ArrayList<String>();
        util = new ServiceReleaseUtil(this);

    }


    class BottomImgIcon implements OnItemClickListener {

        int index;

        public BottomImgIcon(int index) {
            this.index = index;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {

        }
    }

    @SuppressLint("NewApi")
    public ImageView iconImage(String filepath, int index, CheckBox checkBox)
            throws FileNotFoundException {
        LayoutParams params = new LayoutParams(
                relativeLayout2.getMeasuredHeight() - 10,
                relativeLayout2.getMeasuredHeight() - 10);
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(params);
        float alpha = 100;
        imageView.setAlpha(alpha);
        util.imgExcute(imageView, imgCallBack, filepath);
        imageView.setOnClickListener(new ImgOnclick(filepath, checkBox));
        return imageView;
    }

    ImgCallBack imgCallBack = new ImgCallBack() {
        public void resultImgCall(ImageView imageView, Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    };

    class ImgOnclick implements OnClickListener {
        String filepath;
        CheckBox checkBox;

        public ImgOnclick(String filepath, CheckBox checkBox) {
            this.filepath = filepath;
            this.checkBox = checkBox;
        }

        @Override
        public void onClick(View arg0) {
            checkBox.setChecked(false);
            select_layout.removeView(arg0);
            choise_button.setText("已选择" + filelist.size() + "张");
            photoCount--;
            filelist.remove(filepath);
        }
    }

    ImgsAdapter.OnItemClickClass onItemClickClass = new ImgsAdapter.OnItemClickClass() {
        @Override
        public void OnItemClick(View v, int Position, CheckBox checkBox) {
            String filapath = fileTraversal.filecontent.get(Position);
            if (checkBox.isChecked()) {
                checkBox.setChecked(false);
                select_layout.removeView(hashImage.get(Position));
                filelist.remove(filapath);
                choise_button.setText("已选择" + filelist.size() + "张");
                photoCount--;
            } else {
                if (photoCount < 9) {
                    try {
                        checkBox.setChecked(true);
                        ImageView imageView = iconImage(filapath, Position,
                                checkBox);
                        if (imageView != null) {
                            hashImage.put(Position, imageView);
                            filelist.add(filapath);
                            select_layout.addView(imageView);
                            choise_button
                                    .setText("已选择" + filelist.size() + "张");
                            photoCount++;
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ReleaseServiceImages.this, "至多添加9张图片",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public void save(View view) {
        Intent intent = new Intent(this, ImgFileList.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("files", filelist);
        intent.putExtras(bundle);
        ReleaseServiceImages.this.setResult(5, intent);
        finish();
    }


}

