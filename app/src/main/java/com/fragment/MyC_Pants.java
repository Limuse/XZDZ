package com.fragment;

import android.os.Bundle;

import com.leo.base.activity.fragment.LFragment;

/**
 * Created by huisou on 2015/10/29.
 * 裤装
 */
public class MyC_Pants extends LFragment {
    public static MyC_Pants newInstance() {
        MyC_Pants fragment = new MyC_Pants();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
