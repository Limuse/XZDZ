package com.fragment;

import android.os.Bundle;

import com.leo.base.activity.fragment.LFragment;

/**
 * Created by huisou on 2015/10/29.
 */
public class MyDCoat extends LFragment {

    public static MyDCoat newInstance() {
        MyDCoat fragment = new MyDCoat();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
