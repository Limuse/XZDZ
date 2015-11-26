package com.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MyApplication;
import com.activity.SpecificData;
import com.entity.SpecificEntity;
import com.leo.base.activity.fragment.LFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xzdz.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2015/6/26.
 */

public class Specific_page extends LFragment {
    private Map<String, String> map = new HashMap<String, String>();
    private View total;
    private SpecificEntity.ListEntity listEntity;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        total = (ViewGroup) inflater.inflate(
                R.layout.fragment_specific_page, null);
        return total;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        ImageView imageView = (ImageView) total.findViewById(R.id.iv_image);
        TextView name = (TextView) total.findViewById(R.id.tv_name);
        TextView after = (TextView) total.findViewById(R.id.tv_info1);
        TextView bust = (TextView) total.findViewById(R.id.tv_info2);
        TextView sleeve = (TextView) total.findViewById(R.id.tv_info3);
        TextView shoulder = (TextView) total.findViewById(R.id.tv_info4);
        TextView waicuff = (TextView) total.findViewById(R.id.tv_info5);
        TextView waist = (TextView) total.findViewById(R.id.tv_info6);
        TextView waist_top = (TextView) total.findViewById(R.id.tv_info1r);
        TextView hipline = (TextView) total.findViewById(R.id.tv_info2r);
        TextView circumference = (TextView) total.findViewById(R.id.tv_info3r);
        TextView knee = (TextView) total.findViewById(R.id.tv_info4r);
        TextView foot = (TextView) total.findViewById(R.id.tv_info5r);
        TextView pants = (TextView) total.findViewById(R.id.tv_info6r);


        name.setText(listEntity.getName());
        after.setText(listEntity.getAfter());
        bust.setText(listEntity.getBust());
        sleeve.setText(listEntity.getSleeve());
        shoulder.setText(listEntity.getShoulder());
        waicuff.setText(listEntity.getWaicuff());
        waist.setText(listEntity.getWaist());
        waist_top.setText(listEntity.getWaist_top());
        hipline.setText(listEntity.getHipline());
        circumference.setText(listEntity.getCircumference());
        knee.setText(listEntity.getKnee());
        foot.setText(listEntity.getFoot());
        pants.setText(listEntity.getPants());
        SpecificData.imageLoader.displayImage(listEntity.getType(),imageView,SpecificData.options);

    }


    public void getData(SpecificEntity.ListEntity listEntity) {
        this.listEntity = listEntity;
    }
}
