package com.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.activity.Fabric;
import com.custom.VcViewPager;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.util.T;
import com.xzdz.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/10/30.
 * 休闲
 */
public class Vc_Releaxe extends LFragment {
    private Button btn_next;
    private ViewPager viewPager;
    private List<Fragment> fragmentLists = new ArrayList<>();
    private TextView tv_text;
    private Map<Integer, Drawable> map = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragemt_vcreleaxe, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    private void initView() {
        fragmentLists.clear();
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewimg);
        btn_next = (Button) getActivity().findViewById(R.id.nextc1);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Fabric.class);
                startActivity(intent);
//                T.ss("下一步");
            }
        });
        initPage();
    }


    private void initPage() {
//        map.put(1,getResources().getDrawable(R.mipmap.fc));
//        map.put(2,getResources().getDrawable(R.mipmap.fc));
//        map.put(3,getResources().getDrawable(R.mipmap.fc));
//        map.put(4,getResources().getDrawable(R.mipmap.fc));
        for (int i = 0; i < 5; i++) {
            Vc_pager fragmentSpecificPage = new Vc_pager();
//            fragmentSpecificPage.getData(specInfo.get(i));
            fragmentLists.add(fragmentSpecificPage);
        }
        WindowManager wm = Vc_Releaxe.this.getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(width / -10);
        viewPager.setAdapter(new MyAdapter(Vc_Releaxe.this.getActivity().getSupportFragmentManager()));
        tv_text = (TextView) getActivity().findViewById(R.id.tv_text);

    }

    private class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return fragmentLists.get(position);
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);

//            container = (ViewGroup) LayoutInflater.from(getActivity()).inflate(
//                    R.layout.fragment_specific_page_view, null);
//            ImageView img = (ImageView) container.findViewById(R.id.fc);
//            Drawable maps= map.get(position);
//
//            for(int i=0;i<map.size();i++){
//                img.setBackground(maps);
//            }


        }

        public int getCount() {
            return fragmentLists.size();
        }


    }


}
