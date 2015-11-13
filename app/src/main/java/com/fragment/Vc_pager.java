package com.fragment;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leo.base.activity.fragment.LFragment;
import com.xzdz.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huisou on 2015/11/11.
 */
public class Vc_pager extends LFragment {
    private Map<String, Integer> map = new HashMap<>();
    private ImageView img;
    private View total;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container = (ViewGroup) inflater.inflate(
                R.layout.fragment_specific_page_view, null);
        img=(ImageView)getActivity().findViewById(R.id.fc);
        total = inflater.inflate(R.layout.vc_pager_pager, null);
        container.addView(total);
        return container;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView(){


    }

    private void initData() {
    }

    public void getData(Map<String, Integer> map) {
        this.map = map;
        map.put("1",(R.mipmap.fc));
    }
}
