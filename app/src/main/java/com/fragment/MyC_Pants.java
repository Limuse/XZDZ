package com.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.base.activity.fragment.LFragment;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/29.
 * 裤装
 */
public class MyC_Pants extends LFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mycpant, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public static MyC_Pants newInstance() {
        MyC_Pants fragment = new MyC_Pants();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}