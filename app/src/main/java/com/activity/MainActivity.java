package com.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.custom.ImageCycleView;
import com.custom.slidingMenu.SlidingMenu;
import com.fragment.MenuFragment;
import com.leo.base.activity.LActivity;
import com.xzdz.R;

import java.util.ArrayList;

public class MainActivity extends LActivity{
    private SlidingMenu mSlidingMenu;
    private SlidingMenu.CanvasTransformer mTransformer;
    private ImageCycleView mAdView;
    private ArrayList<String> pageImageList = new ArrayList<String>();
    private ArrayList<String> pageImageId = new ArrayList<String>();

    protected void onLCreate(Bundle bundle) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initLayout();
    }

    public void open(View view) {
        mSlidingMenu.toggle();
    }

    public void initLayout() {
        pageImageList.add(0, "ssss");
        pageImageList.add(1, "ssss");
        pageImageId.add(0, "ssss");
        pageImageId.add(1, "ssss");
        pageImageList.add(2, "ssss");
        pageImageId.add(2, "ssss");
        mAdView = (ImageCycleView) findViewById(R.id.ImageCycleView);
        mAdView.setImageResources(pageImageList, pageImageId, mAdCycleViewListener);

        mTransformer=new SlidingMenu.CanvasTransformer() {
            public void transformCanvas(Canvas canvas, float percentOpen) {
                float scale = (float)(percentOpen*0.25 + 0.75);
                canvas.scale(scale, scale, canvas.getWidth()/2,canvas.getHeight()/2);
            }
        };
        mSlidingMenu=new SlidingMenu(this);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setBehindCanvasTransformer(mTransformer);
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mSlidingMenu.setFadeDegree(1f);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        mSlidingMenu.setMenu(R.layout.frame_menu);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_menu, new MenuFragment(mSlidingMenu)).commit();

    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        public void onImageClick(int position, View imageView) {
        }
    };

}
