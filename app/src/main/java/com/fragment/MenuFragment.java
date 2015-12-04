package com.fragment;

import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.MyApplication;
import com.activity.ContactService;
import com.activity.Customing;
import com.activity.MyCollect;
import com.activity.MyCustome;
import com.activity.MyInfo;
import com.activity.MyReam;
import com.activity.Myworked;
import com.activity.Set;
import com.activity.SpecificData;
import com.common.Token;
import com.custom.CircleImageView;
import com.custom.slidingMenu.SlidingMenu;
import com.handle.ActivityHandler;
import com.handle.FragmentHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xzdz.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuFragment extends LFragment implements View.OnClickListener{

	private SlidingMenu slidingMenu;
	private View rootView;
        private CircleImageView civM;
    private RelativeLayout rlM;
    private TextView tv_name;
    private RelativeLayout rl1, rl2, rl3, rl4, rl5, rl6, rl7;
    private RelativeLayout btn_right, btn_left;

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
        btn_left=(RelativeLayout)rootView.findViewById(R.id.btn_lefts);
        btn_right=(RelativeLayout)rootView.findViewById(R.id.btn_rights);
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
        getData();
    }
    private void getData(){
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/member/userinfo/sign/aggregation/?"+"uuid="+ Token.get(getActivity());
        LReqEntity entity = new LReqEntity(url);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        FragmentHandler handler = new FragmentHandler(MenuFragment.this);
        handler.startLoadingData(entity, 1);
    }
    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonData(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }

    private void getJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject jsonO = jsonObject.getJSONObject("list");

//                id: 1
//                username: 叶子
//                truename: 唯一
//                sex: 1
//                email: 123456@qq.com
//                mobile: 13543456789
//                birthday: 2010
//                head_portrait: Uploads/appavatar1448242206.jpg
                if (jsonO.length() > 0) {

                    String id = jsonO.getString("id");
                    String username = jsonO.getString("username");
                    if (!username.equals(null)) {
                        tv_name.setText(username);
                    } else {
                        tv_name.setText("西装定制");
                    }

                    String vimg=jsonO.getString("head_portrait");
                    /**
                     * 图片需要处理
                     */
                    ImageLoader imageLoader = null;

                    // 图片
                    if (imageLoader == null) {
                        imageLoader = MyApplication.getInstance().getImageLoader();
                    }

                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.mipmap.fc)
                            .showImageForEmptyUri(R.mipmap.fc)
                            .showImageOnFail(R.mipmap.fc)
                            .cacheInMemory(true).cacheOnDisk(true)
                            .considerExifParams(true)
                            .displayer(new FadeInBitmapDisplayer(200))
                            .build();
                    imageLoader.displayImage(vimg, civM, options);
                }

            } else {
                T.ss(jsonObject.getString("info"));
                //String longs = jsonObject.getString("info");
//                if (longs.equals("请先登录")) {
//                    Intent intent = new Intent(this, LoginMain.class);
//                    startActivity(intent);
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        if (id == R.id.btn_lefts) {
            //个人资料
            Intent intent = new Intent(getActivity(), MyInfo.class);
            startActivity(intent);
        }
        if (id == R.id.btn_rights) {
            //我的收藏
            Intent intent = new Intent(getActivity(), MyCollect.class);
            startActivity(intent);
        }
    }
}
