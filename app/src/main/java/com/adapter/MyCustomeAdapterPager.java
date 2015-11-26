package com.adapter;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.activity.MyCustome;
import com.fragment.MyC_Coat;
import com.fragment.MyC_Pants;
import com.fragment.MyC_Suit;
import com.fragment.MyDCoat;

/**
 * Created by huisou on 2015/10/29.
 */
public class MyCustomeAdapterPager extends FragmentPagerAdapter {
    final String[] titles = {"裤装", "上衣", "西装", "大衣"};

    public MyCustomeAdapterPager(MyCustome myCustome, android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = MyC_Pants.newInstance();
                break;
            case 1:
                fragment = MyC_Coat.newInstance();
                break;
            case 2:
                fragment = MyC_Suit.newInstance();
                break;
            default:
                fragment = MyDCoat.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
