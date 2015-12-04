package com.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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

import com.adapter.CustomingAdapter;
import com.adapter.ImageAdapter;
import com.alibaba.fastjson.JSON;
import com.common.AboutActivitySy;
import com.common.ImageSizeUtil;
import com.common.ListViewHorizontal;
import com.common.Token;
import com.entity.CustomingEntity;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xzdz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    //private Map<String,String> data=new HashMap<>();
    private String partId;
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
    private ArrayList<String> pic=new ArrayList<>();
    private SharedPreferences listCountSp;
    private SharedPreferences pathSp;


    private int TYPE = 0;
    private int PLACE = 1;
    private int ADDRESS = 2;
    private int CAMERA = 3;
    private ImageAdapter adapter;
    private String order_id;//订单号
    private String repstate;//是否从新申请
    private ImageTask task;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_aftersale);
        AboutActivitySy.getInstance().addActivity(this);
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
        order_id = getIntent().getExtras().getString("order_id");
        repstate = getIntent().getExtras().getString("repstate");
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
                    partId = "15";
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


        if (repstate.equals("3")) {

            getRepStateData();


        } else if (repstate.equals("0")) {

        }
    }

    private class ImageTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... params) {
            if (task.isCancelled() == true) {
                return null;
            }
            try {
                for (int i = 0; i < pic.size(); i++) {
                    Bitmap bit = null;
                    bitmapList.add(bit);
                    URL url = new URL(pic.get(i));
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.connect();
                    if (conn.getResponseCode() == 200) {
                        fileList.add(pic.get(i));
                        InputStream inputStream = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        bitmapList.set(i, bitmap);
                        handler.sendEmptyMessage(2);
                        inputStream.close();
                    } else {
                        bitmapList.remove(i);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            if (msg.what == 2) {
                initAda();
                adapter.notifyDataSetChanged();

            }
        }


    };

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
                        initAda();
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
                        ArrayList<String> list = new ArrayList<>();
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

    private void initAda() {
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
            sendData();
            Intent intent = new Intent(this, SubmitCheck.class);
            intent.putExtra("statenum", "0");
            intent.putExtra("order_id", order_id);
            startActivity(intent);
        }
        if (id == R.id.rl_byn) {
            //T.ss("照相");
            showImageDialog();

        }
    }

    //提交审核数据
    private void sendData() {
        for (int i = 0; i < bitmapList.size(); i++) {
            if (!photoList.equals("")) {
                photoList = photoList + ",";
            }
            photoList = photoList + bitmaptoString(bitmapList.get(i));
        }
        String desc = et_text.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Token.get(this));
        map.put("order_id", order_id);
        map.put("part_id", partId);
        map.put("content", desc);
        map.put("pictures", photoList);
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/repair/repairexamine/sign/aggregation/";
        LReqEntity entity = new LReqEntity(url, map);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);

    }


    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonData(msg.getStr());
            } else if (requestId == 2) {
                getRepJsonDatas(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }

    private void getJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                T.ss(jsonObject.getString("info"));
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

    private void getRepStateData() {
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url) +
                "/app/repair/getpartslist/sign/aggregation/?uuid="
                + Token.get(this) + "&order_id=" +order_id;
        LReqEntity entity = new LReqEntity(url);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 2);
    }


    private void getRepJsonDatas(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject object = jsonObject.getJSONObject("list");
                JSONObject js = object.getJSONObject("repair");
                String id = js.getString("id");
                String orId = js.getString("order_id");
                String content = js.getString("content");
                et_text.setText(content);
                String parId = js.getString("part_id");
                partId = parId;
                JSONArray array = object.getJSONArray("img");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jos = array.getJSONObject(i);
                    String imgview = jos.getString("img");
                   pic.add(imgview);

                }
                task = new ImageTask();
                task.execute();
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


}
