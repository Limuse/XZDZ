package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custom.CircleImageView;
import com.custom.ImageCycleView;
import com.custom.SlidingMenu;
import com.leo.base.activity.LActivity;
import com.leo.base.util.T;
import com.xzdz.R;

import java.util.ArrayList;

public class MainActivity extends LActivity implements View.OnClickListener {

    private SlidingMenu mMenu;

    private CircleImageView civM;
    private RelativeLayout rlM;
    private TextView tv_name;
    private RelativeLayout rl1,rl2,rl3,rl4,rl5,rl6,rl7;
    private Button btn_right,btn_left;

    private ImageCycleView mAdView;
    private ArrayList<String> pageImageList = new ArrayList<String>();
    private ArrayList<String> pageImageId = new ArrayList<String>();

    protected void onLCreate(Bundle bundle) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mMenu = (SlidingMenu) findViewById(R.id.id_menu);
        initLayout();

    }

    public void toggleMenu(View view) {
        T.ss("aaaa");
    }

    public void initLayout() {
        pageImageList.add(0,"ssss");
        pageImageList.add(1,"ssss");   pageImageId.add(0,"ssss");
        pageImageId.add(1,"ssss");
        mAdView=(ImageCycleView)findViewById(R.id.ImageCycleView);
        mAdView.setImageResources(pageImageList, pageImageId, mAdCycleViewListener);


        rlM = (RelativeLayout) this.findViewById(R.id.my);
        civM=(CircleImageView) this.findViewById(R.id.image_main);
        tv_name=(TextView) this.findViewById(R.id.tv_name);
        rl1=(RelativeLayout)this.findViewById(R.id.rl1);
        rl2=(RelativeLayout)this.findViewById(R.id.rl2);
        rl3=(RelativeLayout)this.findViewById(R.id.rl3);
        rl4=(RelativeLayout)this.findViewById(R.id.rl4);
        rl5=(RelativeLayout)this.findViewById(R.id.rl5);
        rl6=(RelativeLayout)this.findViewById(R.id.rl6);
        rl7=(RelativeLayout)this.findViewById(R.id.rl7);
        btn_left=(Button)this.findViewById(R.id.btn_left);
        btn_right=(Button)this.findViewById(R.id.btn_right);
        civM.setOnClickListener(this);
        tv_name.setOnClickListener(this);
        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl6.setOnClickListener(this);
        rl7.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.image_main){
           //头像
        }
        if(id==R.id.tv_name){
            //昵称
        }
        if(id==R.id.rl1){
           //我要定制
            Intent intent=new Intent(this,MyCustome.class);
            startActivity(intent);
        }
        if(id==R.id.rl2){
            //我的作品
            Intent intent=new Intent(this,Myworked.class);
            startActivity(intent);
        }
        if(id==R.id.rl3){
            //正在定制
            Intent intent=new Intent(this,Customing.class);
            startActivity(intent);
        }
        if(id==R.id.rl4){
            //量体数量
            Intent intent=new Intent(this,SpecificData.class);
            startActivity(intent);
        }
        if(id==R.id.rl5){
            //我的红包
            Intent intent=new Intent(this,MyReam.class);
            startActivity(intent);
        }
        if(id==R.id.rl6){
            //联系客服
            Intent intent=new Intent(this,ContactService.class);
            startActivity(intent);
        }
        if(id==R.id.rl7){
            //设置
            Intent intent=new Intent(this,Set.class);
            startActivity(intent);
        }
        if(id==R.id.btn_left){
            //个人资料
            Intent intent=new Intent(this,MyInfo.class);
            startActivity(intent);
        }
        if(id==R.id.btn_right){
            //我的收藏
            Intent intent=new Intent(this,MyCollect.class);
            startActivity(intent);
        }
    }

    public void open(View v){
        mMenu.toggle();
    }


    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        public void onImageClick(int position, View imageView) {

        }
    };

}
