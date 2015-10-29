package com.fragment;

import android.os.Bundle;

import com.leo.base.activity.fragment.LFragment;

/**
 * Created by huisou on 2015/10/29.
 * 上衣
 */
public class MyC_Coat extends LFragment {
    public static MyC_Coat newInstance() {
        MyC_Coat fragment = new MyC_Coat();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
