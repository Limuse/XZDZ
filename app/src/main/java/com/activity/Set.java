package com.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.CacheUtill;
import com.common.Token;
import com.custom.CustomDialog;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.xzdz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huisou on 2015/10/27.
 * 设置
 */
public class Set extends LActivity implements View.OnClickListener {
    private RelativeLayout rl1, rl2, rl3, rl4;
    private TextView tv_clear;
    private Button btn_out;
    private Boolean tr = true;
    private String fils = Environment.getExternalStorageDirectory()
            + "/Android/data/com.xzdz/cache";
    private File file1, file2;
    private boolean cleanFlag = false;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_set);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_set));
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
        rl1 = (RelativeLayout) findViewById(R.id.rl_pass);
        rl2 = (RelativeLayout) findViewById(R.id.rl_mails);
        rl3 = (RelativeLayout) findViewById(R.id.rl_phones);
        rl4 = (RelativeLayout) findViewById(R.id.rl_clear);
        tv_clear = (TextView) findViewById(R.id.tv_cle);
        btn_out = (Button) findViewById(R.id.btn_outline);

        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        btn_out.setOnClickListener(this);
        tr = LSharePreference.getInstance(this).getBoolean("login");
        if (tr == false) {
            btn_out.setText("登录");
        } else {
            btn_out.setText("退出登录");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean tts = LSharePreference.getInstance(this).getBoolean("login");
        if (tts == true) {
            btn_out.setText("退出登录");
        } else {
            btn_out.setText("登录");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_pass) {
            //密码修改
            Intent intent = new Intent(this, UpdatePwd.class);
            startActivity(intent);
        }
        if (id == R.id.rl_mails) {
            //邮箱绑定
            Intent intent = new Intent(this, BuildEmail.class);
            startActivity(intent);
        }
        if (id == R.id.rl_phones) {
            //手机绑定
            Intent intent = new Intent(this, Phone.class);
            intent.putExtra("id", "1");
            startActivity(intent);
        }
        if (id == R.id.rl_clear) {
            //清理缓存
            // T.ss("清除缓存");
            if (cleanFlag) {
                T.ss("已经很干净啦！");
            } else {
                final CustomDialog alertDialog = new CustomDialog.Builder(this).
                        setMessage("清除缓存吗？").
                        setNegativeButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                try {
                                    if (tv_clear.getText().toString().trim()
                                            .equals("0.0Byte")) {

                                        T.ss("暂无缓存");
                                    } else {
                                        boolean flag1 = CacheUtill.deleteFolderFile(file1.getPath(), true);
                                        boolean flag2 = CacheUtill.deleteFolderFile(file2.getPath(), true);
                                        if (flag1 && flag2) {
                                            getcache();
                                            T.ss("清除成功");
                                            tv_clear.setText("无缓存");
                                            cleanFlag = true;
                                        } else {
                                            T.ss("清除失败");
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                dialog.dismiss();
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();


                alertDialog.show();
            }
        }
        if (id == R.id.btn_outline) {
            //退出登录
            if (tr == false) {
                //btn_out.setText("退出登录");
                // LSharePreference.getInstance(this).setBoolean("login", false);
                Intent intent = new Intent(this, LoginMain.class);
                startActivity(intent);
            } else if (tr == true) {
                btn_out.setText("登录");
                LSharePreference.getInstance(this).setBoolean("login", false);
                tr = false;
            }
        }
    }


    private void getcache() {
        try {
            file1 = new File(Environment.getExternalStorageDirectory(), "cache");
            if (!file1.exists()) {
                file1.mkdirs();
            }
            long folderSize = CacheUtill.getFolderSize(file1);

            file2 = new File(Environment.getExternalStorageDirectory()
                    + "/Android/data/com.xzdz/");
            if (!file2.exists()) {
                file2.mkdirs();
            }
            long folderSize2 = CacheUtill.getFolderSize(file2);
            String cacheSize = CacheUtill
                    .getFormatSize((folderSize + folderSize2));

            if (cacheSize.equals("0.0B")) {
                tv_clear.setText("无缓存");
            } else {
                tv_clear.setText(cacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
