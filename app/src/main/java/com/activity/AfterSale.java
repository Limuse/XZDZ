package com.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.adapter.ImageAdapter;
import com.common.ImageSizeUtil;
import com.common.ListViewHorizontal;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xzdz.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by huisou on 2015/11/3.
 * 售后-提交信息
 */
public class AfterSale extends LActivity implements View.OnClickListener {
    private Button btn_submit;
    private EditText et_text;
    private ListViewHorizontal holsview;
    private RelativeLayout rl_btn;
    private Spinner spinner;
    private String[] datas;

    private static final String IMGURL = Environment
            .getExternalStorageDirectory() + "/Android/data/com.xzdz/";
    private Dialog dialog;
    private View view;
    private Button openPhones;
    private Button openCamera;
    private Button cancel;
    private SharedPreferences.Editor editor;
    private String nowTime;
    private String photoList = "";
    private ArrayList<String> fileList = new ArrayList<>();
    private ArrayList<Bitmap> bitmapList = new ArrayList<>();
    private ArrayList<String> bitmapIdList = new ArrayList<>();
    private SharedPreferences listCountSp;
    private SharedPreferences pathSp;


    private int TYPE = 0;
    private int PLACE = 1;
    private int ADDRESS = 2;
    private int CAMERA = 3;
    private ImageAdapter adapter;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_aftersale);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_afersalse));
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
        btn_submit = (Button) findViewById(R.id.tj);
        rl_btn = (RelativeLayout) findViewById(R.id.rl_byn);
        et_text = (EditText) findViewById(R.id.et_xiangq);
        holsview = (ListViewHorizontal) findViewById(R.id.chae);
        spinner = (Spinner) findViewById(R.id.spinner);

        btn_submit.setOnClickListener(this);
        rl_btn.setOnClickListener(this);
        datas = new String[]{"请选择", "袖口", "领口", "上衣", "裤子", "裤脚"};
        // android.R.layout.simple_spinner_dropdown_item//R.layout.spinner_item
        ArrayAdapter<String> adapterTwo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, datas);
        spinner.setAdapter(adapterTwo);
        spinner.setVisibility(View.VISIBLE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String stype = datas[position];
                    T.ss(stype);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        view = this.getLayoutInflater().inflate(R.layout.pic_show,
                null);
        dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        openPhones = (Button) dialog.findViewById(R.id.btn_pics);
        openCamera = (Button) dialog.findViewById(R.id.btn_play);
        cancel = (Button) dialog.findViewById(R.id.btn_cncel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        openPhones.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listCountSp = getSharedPreferences("listCount", 1);
                editor = listCountSp.edit();
                editor.putInt("count", fileList.size()).commit();
                Intent intent = new Intent(AfterSale.this,
                        ImgFileList.class);
                startActivityForResult(intent, 5);
                dialog.cancel();
            }
        });
        openCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCamera();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case 3:// 相机返回
                if (resultCode == RESULT_OK) {
                    fileList.add(IMGURL + pathSp.getString("path", nowTime));
                    if (ImageSizeUtil.ImageHandle(IMGURL
                            + pathSp.getString("path", nowTime)) != null) {
                        Bitmap bitmap = ImageSizeUtil.ImageHandle(IMGURL
                                + pathSp.getString("path", nowTime));
                        bitmapList.add(bitmap);
                        adapter.notifyDataSetChanged();
                    }
                    if (fileList.size() >= 9) {
                    }
                    if (fileList.size() == 0) {
                    }
                }
                break;
            // case 4:
            // final File tempFile1 = new File(IMGURL + IMAGE_FILE_NAME_TEMP);
            // if (tempFile1.exists()) {
            // new Thread() {
            // public void run() {
            // tempFile1.delete();
            // };
            // }.start();
            // }
            // break;
            case 5:// 图册返回
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        ArrayList<String> list = new ArrayList<String>();
                        list = bundle.getStringArrayList("files");
                        for (int i = 0; i < list.size(); i++) {
                            fileList.add(list.get(i));
                            Bitmap bitmap = ImageSizeUtil.ImageHandle(list.get(i));
                            bitmapList.add(bitmap);
                        }
                        if (fileList.size() >= 9) {
                        }
                        if (fileList.size() == 0) {
                        }
                        initAda();
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
private  void initAda(){
    adapter = new ImageAdapter(this, fileList, bitmapList);
    holsview.setAdapter(adapter);
}
    private void openCamera() {
        File file = new File(IMGURL);
        if (!file.exists()) {
            file.mkdirs();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat t = new SimpleDateFormat("yyyymmddhhmmss");
        nowTime = t.format(new Date()) + ".jpg";
        pathSp = getSharedPreferences("imageurl", 3);
        editor = pathSp.edit();
        editor.putString("path", nowTime).commit();
        Uri imageUri = Uri.fromFile(new File(IMGURL, nowTime));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA);
        dialog.dismiss();
    }

    @SuppressWarnings("deprecation")
    private void showImageDialog() {
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = this.getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public String bitmaptoString(Bitmap bitmap) {
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tj) {
            //提交审核
            Intent intent = new Intent(this, SubmitCheck.class);
            startActivity(intent);
        }
        if (id == R.id.rl_byn) {
            //T.ss("照相");
            showImageDialog();

        }
    }
}
