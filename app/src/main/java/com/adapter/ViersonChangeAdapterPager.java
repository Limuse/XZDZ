package com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.activity.MyCustome;
import com.activity.ViersonChange;
import com.fragment.Vc_Dress;
import com.fragment.Vc_Releaxe;
import com.fragment.Vc_Suit;

/**
 * Created by huisou on 2015/10/30.
 */
public class ViersonChangeAdapterPager extends FragmentPagerAdapter {
    final String[] titles = {"休闲", "正装", "礼服"};
    public ViersonChangeAdapterPager(ViersonChange viersonChange, android.support.v4.app.FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = Vc_Releaxe.newInstance();
                break;
            case 1:
                fragment = Vc_Suit.newInstance();
                break;
            default:
                fragment = Vc_Dress.newInstance();
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
        return 3;
    }
}
