package com.activity;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.HavaSdCard;
import com.leo.base.activity.LActivity;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xzdz.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
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
    private static final String IMGURL = Environment
            .getExternalStorageDirectory() + "/Android/data/com.xzdz/";

    private static final String IMAGE_FILE_NAME_TEMP = "xz_image.jpg";

    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_myinfo);
        initBar();
        initView();
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
        my_imgs=(ImageView)findViewById(R.id.setting_img);
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
        tv_mail=(TextView) findViewById(R.id.tv_mial);
        View view = getLayoutInflater().inflate(R.layout.pic_show, null);
        dialog = new Dialog(this,
                R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
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

    @Override
    public void onClick(View v) {
    int id=v.getId();
        if(id==R.id.rl_img){
            //头像
            showBuyDialog();
        }
        if(id==R.id.rl_name){
            //昵称
        }
        if(id==R.id.rl_nametrue){
            //真实姓名
        }
        if(id==R.id.rl_sex){
            //性别
        }
        if(id==R.id.rl_brithday){
            //生日
        }
        if(id==R.id.rl_phone){
            //手机
        }
        if(id==R.id.rl_mail){
            //邮箱
        }

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
//            case 5:
//                String name = data.getExtras().getString("name");
//                if (name.equals("1")) {
//                    tv_name.setText(names);
//                } else if (name.equals("2")) {
//                    tv_name.setText(names);
//                } else {
//                    tv_name.setText(name);
//                    names = name;
//                }
//                break;
//            case 6:
//                String sdnum = data.getExtras().getString("sdnum");
//
//                if (sdnum.equals("1")) {
//                    tv_num.setText("");
//                } else if (sdnum.equals("2")) {
//                    tv_num.setText("");
//                } else {
//                    tv_num.setText(sdnum);
//                    names = sdnum;
//                }
//                break;
            default:
                break;
        }
    }
    private void picloade() {

//        Map<String, String> map = new HashMap<String, String>();
//        map.put("pictures", img);// 头像
//        map.put("uuid", Token.get(this));
//        Resources res = getResources();
//        String url = res.getString(R.string.app_service_url)
//                + "/huihao/member/amendavatar/1/sign/aggregation/";
//        LReqEntity entity = new LReqEntity(url, map);
//        ActivityHandler handler = new ActivityHandler(this);
//        handler.startLoadingData(entity, 1);

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
