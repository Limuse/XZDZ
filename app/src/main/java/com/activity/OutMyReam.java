package com.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.custom.IlistView;
import com.leo.base.activity.LActivity;
import com.xzdz.R;

/**
 * Created by huisou on 2015/11/5.
 */
public class OutMyReam extends LActivity {
    private IlistView listview_outrb;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_outmyream);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_outmyredm));
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
        listview_outrb = (IlistView) findViewById(R.id.listview_rb);
    }
}
