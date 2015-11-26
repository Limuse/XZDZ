package com.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.custom.CustomDialog;
import com.leo.base.activity.LActivity;
import com.leo.base.util.LSharePreference;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/27.
 * 联系客服
 */
public class ContactService extends LActivity implements View.OnClickListener {
    private Button tv_tel;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_contaceservice);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_contacts));
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
        tv_tel = (Button) this.findViewById(R.id.tv_tel);
        tv_tel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_tel) {

            final CustomDialog alertDialog = new CustomDialog.Builder(this).
                    setMessage("确定联系客服吗？").
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "正在呼叫  " + tv_tel.getText(),
                                    Toast.LENGTH_LONG)
                                    .show();
                            Uri uri = Uri.parse("tel:"
                                    + tv_tel.getText());
                            Intent intent = new Intent(
                                    Intent.ACTION_CALL, uri);
                            startActivity(intent);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();
            alertDialog.show();
        }
    }
}




































































































































































