package com.fragment;

import android.os.Bundle;

import com.leo.base.activity.fragment.LFragment;

/**
 * Created by huisou on 2015/10/29.
 */
public class MyC_Suit extends LFragment{
    public static MyC_Suit newInstance() {
        MyC_Suit fragment = new MyC_Suit();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
