package com.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.MyApplication;
import com.activity.MyCustome;
import com.activity.ViersonChanges;
import com.common.Token;
import com.entity.MyCusdtomEntity;
import com.handle.ActivityHandler;
import com.handle.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xzdz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/10/29.
 * 裤装  id=2
 */
public class MyC_Pants extends LFragment {
    private ImageView img;
    private Button btn_next;
    private List<MyCusdtomEntity.ListEntity> list = new ArrayList<>();
    private MyCusdtomEntity.ListEntity entity = new MyCusdtomEntity.ListEntity();

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
        initView();
    }

    private void initView() {
        img = (ImageView) getActivity().findViewById(R.id.pant_img);
        btn_next = (Button) getActivity().findViewById(R.id.next2);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LSharePreference.getInstance(getActivity()).setString("cid_first",
                        entity.getId());
                Intent intent = new Intent(getActivity(), ViersonChanges.class);
                Bundle bundle = new Bundle();
                bundle.putString("ids", entity.getId());
                bundle.putSerializable("entity", entity);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        initData();
    }


    public static MyC_Pants newInstance() {
        MyC_Pants fragment = new MyC_Pants();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void imgs() {
        /**
         * 图片需要处理
         */
        ImageLoader imageLoader = null;

        // 图片
        if (imageLoader == null) {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.pants)
                .showImageForEmptyUri(R.mipmap.pants)
                .showImageOnFail(R.mipmap.pants)
                .cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(200))
                .build();
        imageLoader.displayImage(entity.getFace_pic(), img, options);
    }

    private void initData() {
        list = MyCustome.myCustome.SendList();
        for (int i = 0; i < list.size(); i++) {
            MyCusdtomEntity.ListEntity listEntityp = new MyCusdtomEntity.ListEntity();
            if (list.get(i).getTitle().equals("裤装")) {
                listEntityp.setId(list.get(i).getId());
                listEntityp.setTitle(list.get(i).getTitle());
                listEntityp.setPid(list.get(i).getPid());
                listEntityp.setFace_pic(list.get(i).getFace_pic());
                listEntityp.set_child(list.get(i).get_child());
                entity = listEntityp;
              //  L.e(entity.getTitle()+entity.getPid()+entity.getId());
            }
        }
//        L.e(entity.getFace_pic() + "ddddddddddd");
//        L.e(list.size() + "ddddddddddd");
//        L.e(entity.toString()+"dddddddddddddd");
        imgs();
    }


}
