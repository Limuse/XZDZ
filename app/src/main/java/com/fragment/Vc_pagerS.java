package com.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MyApplication;
import com.entity.VcEntity;
import com.leo.base.activity.fragment.LFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xzdz.R;

/**
 * Created by huisou on 2015/11/30.
 */
public class Vc_pagerS extends LFragment {
    private VcEntity vcEntitys;
    private View total;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        total = (ViewGroup) inflater.inflate(
                R.layout.vc_pager_pagers, null);

        return total;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    private void initView() {
        ImageView img = (ImageView) total.findViewById(R.id.fcs);
        TextView tv = (TextView) total.findViewById(R.id.tv_texts);
        tv.setText(vcEntitys.getTitle());
        /**
         * 图片需要处理
         */
        ImageLoader imageLoader = null;

        // 图片
        if (imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        imageLoader.displayImage(vcEntitys.getThumbnail(), img, options);
    }


    public void getData(VcEntity vcEntity) {
        this.vcEntitys = vcEntity;

    }
}
