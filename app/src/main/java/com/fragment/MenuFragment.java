package com.fragment;

import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activity.ContactService;
import com.activity.Customing;
import com.activity.MyCollect;
import com.activity.MyCustome;
import com.activity.MyInfo;
import com.activity.MyReam;
import com.activity.Myworked;
import com.activity.Set;
import com.activity.SpecificData;
import com.custom.CircleImageView;
import com.custom.slidingMenu.SlidingMenu;
import com.leo.base.activity.LActivity;
import com.leo.base.activity.fragment.LFragment;
import com.xzdz.R;

@SuppressLint("ValidFragment")
public class MenuFragment extends LFragment implements View.OnClickListener{

	private SlidingMenu slidingMenu;
	private View rootView;
        private CircleImageView civM;
    private RelativeLayout rlM;
    private TextView tv_name;
    private RelativeLayout rl1, rl2, rl3, rl4, rl5, rl6, rl7;
    private Button btn_right, btn_left;

    public MenuFragment(SlidingMenu slidingMenu) {
		this.slidingMenu = slidingMenu;
	}

	public MenuFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.menu_fragment, container, false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        initView();
	}

    private void initView() {
        rlM = (RelativeLayout) rootView.findViewById(R.id.my);
        civM=(CircleImageView) rootView.findViewById(R.id.image_main);
        tv_name=(TextView) rootView.findViewById(R.id.tv_name);
        rl1=(RelativeLayout)rootView.findViewById(R.id.rl1);
        rl2=(RelativeLayout)rootView.findViewById(R.id.rl2);
        rl3=(RelativeLayout)rootView.findViewById(R.id.rl3);
        rl4=(RelativeLayout)rootView.findViewById(R.id.rl4);
        rl5=(RelativeLayout)rootView.findViewById(R.id.rl5);
        rl6=(RelativeLayout)rootView.findViewById(R.id.rl6);
        rl7=(RelativeLayout)rootView.findViewById(R.id.rl7);
        btn_left=(Button)rootView.findViewById(R.id.btn_left);
        btn_right=(Button)rootView.findViewById(R.id.btn_right);
        civM.setOnClickListener(this);
        tv_name.setOnClickListener(this);
        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl6.setOnClickListener(this);
        rl7.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_main) {
            //头像
        }
        if (id == R.id.tv_name) {
            //昵称
        }
        if (id == R.id.rl1) {
            //我要定制
            Intent intent = new Intent(getActivity(), MyCustome.class);
            startActivity(intent);
        }
        if (id == R.id.rl2) {
            //我的作品
            Intent intent = new Intent(getActivity(), Myworked.class);
            startActivity(intent);
        }
        if (id == R.id.rl3) {
            //正在定制
            Intent intent = new Intent(getActivity(), Customing.class);
            startActivity(intent);
        }
        if (id == R.id.rl4) {
            //量体数量
            Intent intent = new Intent(getActivity(), SpecificData.class);
            startActivity(intent);
        }
        if (id == R.id.rl5) {
            //我的红包
            Intent intent = new Intent(getActivity(), MyReam.class);
            startActivity(intent);
        }
        if (id == R.id.rl6) {
            //联系客服
            Intent intent = new Intent(getActivity(), ContactService.class);
            startActivity(intent);
        }
        if (id == R.id.rl7) {
            //设置
            Intent intent = new Intent(getActivity(), Set.class);
            startActivity(intent);
        }
        if (id == R.id.btn_left) {
            //个人资料
            Intent intent = new Intent(getActivity(), MyInfo.class);
            startActivity(intent);
        }
        if (id == R.id.btn_right) {
            //我的收藏
            Intent intent = new Intent(getActivity(), MyCollect.class);
            startActivity(intent);
        }
    }
}
