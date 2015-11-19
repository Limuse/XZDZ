package com.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.common.HavaSdCard;
import com.common.Token;
import com.custom.MaterialDialog;
import com.custom.MyDatePickDialog;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/10/27.
 * 个人资料
 */
public class MyInfo extends LActivity implements View.OnClickListener {
    private RelativeLayout rl1, rl2, rl3, rl4, rl5, rl6, rl7;
    private TextView tv_sex, tv_brithday, tv_phone, tv_mail;
    private ImageView my_imgs;
    private Dialog dialog;
    private String logoBase;
    private String img;
    private SimpleAdapter dialogAda;
    private MaterialDialog materialDialog;
    private String sex;
    private String sexid;
    private List<Map<String, String>> gender_list = new ArrayList<>();
    private ListView listView;
    private static final String IMGURL = Environment
            .getExternalStorageDirectory() + "/Android/data/com.xzdz/";

    private static final String IMAGE_FILE_NAME_TEMP = "xz_image.jpg";

    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private String phones;
    private String emails;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_myinfo);
        initBar();
        initView();
        setGender();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_myinfo));
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
        my_imgs = (ImageView) findViewById(R.id.setting_img);
        rl1 = (RelativeLayout) findViewById(R.id.rl_img);
        rl2 = (RelativeLayout) findViewById(R.id.rl_name);
        rl3 = (RelativeLayout) findViewById(R.id.rl_nametrue);
        rl4 = (RelativeLayout) findViewById(R.id.rl_sex);
        rl5 = (RelativeLayout) findViewById(R.id.rl_brithday);
        rl6 = (RelativeLayout) findViewById(R.id.rl_phone);
        rl7 = (RelativeLayout) findViewById(R.id.rl_mail);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_brithday = (TextView) findViewById(R.id.tv_bridthday);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_mail = (TextView) findViewById(R.id.tv_mial);
        View view = getLayoutInflater().inflate(R.layout.pic_show, null);
        dialog = new Dialog(this,
                R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        initDialog(view);
        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl6.setOnClickListener(this);
        rl7.setOnClickListener(this);
    }

    private void initData() {
///app/member/userinfo/sign/aggregation/uuid
        Map<String, String> map = new HashMap<String, String>();
        //L.e(img);
        //map.put("uuid", Token.get(this));
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "app/member/userinfo/sign/aggregation/" + Token.get(this);
        LReqEntity entity = new LReqEntity(url);
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 4);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_img) {
            //头像
            showBuyDialog();
        }
        if (id == R.id.rl_name) {
            //昵称
            Intent intent = new Intent(this, UpdateName.class);
            startActivity(intent);
        }
        if (id == R.id.rl_nametrue) {
            //真实姓名
            Intent intent = new Intent(this, UpdateNameTrue.class);
            startActivity(intent);
        }
        if (id == R.id.rl_sex) {
            //性别1男2女
            dialogAda = new SimpleAdapter(this, gender_list,
                    R.layout.sex_item, new String[]{"name"},
                    new int[]{R.id.tv});
            dialogShow();

        }
        if (id == R.id.rl_brithday) {
            //生日/app/member/editbirthday/sign/aggregation/
            Calendar c = Calendar.getInstance();
            MyDatePickDialog datePicker = new MyDatePickDialog(MyInfo.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    tv_brithday.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                    Btime();
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

            datePicker.show();


        }
        if (id == R.id.rl_phone) {
            //手机
            Intent intent = new Intent(MyInfo.this, InfPhone.class);
            startActivityForResult(intent, 5);
        }
        if (id == R.id.rl_mail) {
            //邮箱
            Intent intent = new Intent(MyInfo.this, InfEmail.class);
            startActivityForResult(intent, 6);
        }

    }

    // 性别选择
    public void dialogShow() {
        listView = new ListView(this);
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
        listView.setDividerHeight(0);
        listView.setAdapter(dialogAda);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        initClick();
        materialDialog = new MaterialDialog(this).setTitle("请选择")
                .setContentView(listView);
        materialDialog.show();
    }

    //性别选择填充数据
    public void setGender() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "男");
        gender_list.add(map);
        map = new HashMap<>();
        map.put("id", "2");
        map.put("name", "女");
        gender_list.add(map);
        // gender_list.add("取消");
    }

    //性别选择的点击事件
    private void initClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position == 0) {
                    sex = gender_list.get(position).get("name");
                    sexid = gender_list.get(position).get("id");
                    tv_sex.setText(sex);
                    // T.ss(sexid);
                    sex();
                }
                if (position == 1) {
                    sex = gender_list.get(position).get("name");
                    sexid = gender_list.get(position).get("id");
                    tv_sex.setText(sex);
                    //T.ss(sexid);
                    sex();
                }

                materialDialog.dismiss();

            }
        });


    }

    private void initDialog(View view) {
        Button btn_play = (Button) view.findViewById(R.id.btn_play);
        Button btn_pics = (Button) view.findViewById(R.id.btn_pics);
        Button btn_cncel = (Button) view.findViewById(R.id.btn_cncel);
        /**
         * 弹框点击事件
         *
         */

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //T.ss("拍照");
                openCamera();

            }
        });
        btn_pics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //T.ss("从相册中选择");
                openPhones();
            }
        });
        btn_cncel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //T.ss("取消");
                dialog.dismiss();
            }
        });
    }

    private void openPhones() {
        Intent intentFromGallery = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromGallery.setType("image/*"); // 设置文件类型
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, 2);
    }

    private void openCamera() {
        // 打开相机
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用,存储缓存图片
        if (HavaSdCard.hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(IMGURL + IMAGE_FILE_NAME_TEMP)));
        }
        startActivityForResult(intentFromCapture, 3);
    }


    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 4);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case 2:// 打开相册返回
                if (data != null) {
                    startPhotoZoom(data.getData());
                }
                break;
            case 3:
                if (resultCode == -1) {
                    File tempFile = new File(IMGURL + IMAGE_FILE_NAME_TEMP);
                    startPhotoZoom(Uri.fromFile(tempFile));
                } else {
                    T.ss("获取相机图片失败");
                }
                break;
            case 4:// 裁剪完成,删除照相机缓存的图片
                final File tempFile = new File(IMGURL + IMAGE_FILE_NAME_TEMP);
                if (tempFile.exists()) {
                    new Thread() {
                        public void run() {
                            tempFile.delete();
                        }
                    }.start();
                }
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] bytes = baos.toByteArray();
                        logoBase = Base64.encodeToString(bytes, Base64.DEFAULT);
                        my_imgs.setImageBitmap(photo);
                        img = logoBase;//当上传时可以上传img
                        picloade();

                    }
                }
                dialog.cancel();
                break;
            case 5:
                String phone = data.getExtras().getString("phone");
                if (phone.equals("1")) {
                    tv_phone.setText(phone);
                } else if (phone.equals("2")) {
                    //  tv_phone.setText(phones);
                } else {
                    tv_phone.setText(phone);
                    // phones = phone;
                }
                break;
            case 6:
                String email = data.getExtras().getString("email");

                if (email.equals("1")) {
                    tv_mail.setText(email);
                } else if (email.equals("2")) {
                    tv_mail.setText("");
                } else {
                    tv_mail.setText(email);
                    //emails = email;
                }
                break;
            default:
                break;
        }
    }

    private void picloade() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("pictures", img);// 头像
        //L.e(img);
        //map.put("uuid", Token.get(this));
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/member/editavatar/sign/aggregation/" + Token.get(this);
        LReqEntity entity = new LReqEntity(url, map);
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);

    }


    private void getJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                //T.ss("图片已上传");
                T.ss(jsonObject.getString("info"));
            } else {
                T.ss(jsonObject.getString("info"));
                String longs = jsonObject.getString("info");
//                if (longs.equals("请先登录")) {
//                    Intent intent = new Intent(this, LoginMain.class);
//                    startActivity(intent);
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //性别
    private void sex() {
        if (!sexid.equals(null)) {
            Map<String, String> map = new HashMap<>();
            //map.put("uuid", Token.get(this));
            map.put("sex", sexid.trim());// 性别
            Resources res = getResources();
            String url = res.getString(R.string.app_service_url)
                    + "/app/member/editsex/sign/aggregation/" + Token.get(this);
            LReqEntity entity = new LReqEntity(url, map);
            ActivityHandler handler = new ActivityHandler(this);
            handler.startLoadingData(entity, 2);
        }

    }

    private void getJsonsex(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                T.ss(jsonObject.getString("info"));
            } else {
                T.ss(jsonObject.getString("info"));
                //    String longs = jsonObject.getString("info");
//                if (longs.equals("请先登录")) {
//                    Intent intent = new Intent(this, LoginMain.class);
//                    startActivity(intent);
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //生日
    private void Btime() {

        Map<String, String> map = new HashMap<>();
        //map.put("uuid", Token.get(this));
        map.put("birthday", tv_brithday.getText().toString());// 生日
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/member/editbirthday/sign/aggregation/" + Token.get(this);
        LReqEntity entity = new LReqEntity(url, map);
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 3);


    }

    private void getJsontime(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                T.ss(jsonObject.getString("info"));
            } else {
                T.ss(jsonObject.getString("info"));
                //String longs = jsonObject.getString("info");
//                if (longs.equals("请先登录")) {
//                    Intent intent = new Intent(this, LoginMain.class);
//                    startActivity(intent);
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonData(msg.getStr());
            } else if (requestId == 2) {
                getJsonsex(msg.getStr());
            } else if (requestId == 3) {
                getJsontime(msg.getStr());
            } else if (requestId == 4) {
                getJsoninf(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }

    private void getJsoninf(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("list");
//                id: 1
//                username: 叶子
//                truename: 唯一
//                sex: 1
//                email: 123456@qq.com
//                mobile: 13543456789
//                birthday: 2010

            } else {
                T.ss(jsonObject.getString("info"));
                //String longs = jsonObject.getString("info");
//                if (longs.equals("请先登录")) {
//                    Intent intent = new Intent(this, LoginMain.class);
//                    startActivity(intent);
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showBuyDialog() {
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.Dialog_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}