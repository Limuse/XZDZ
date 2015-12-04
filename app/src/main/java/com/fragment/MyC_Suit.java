package com.fragment;

import android.content.Intent;
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
import com.entity.MyCusdtomEntity;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.util.LSharePreference;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xzdz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/10/29.
 * 西装 id=1
 */
public class MyC_Suit extends LFragment {
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
        return inflater.inflate(R.layout.fragment_mycsuit, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    private void initView() {
        img = (ImageView) getActivity().findViewById(R.id.suit_img);
        btn_next = (Button) getActivity().findViewById(R.id.next3);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LSharePreference.getInstance(getActivity()).setString("cid_first",
                        entity.getId());
                Intent intent = new Intent(getActivity(), ViersonChanges.class);
                Bundle bundle = new Bundle();
                bundle.putString("ids", entity.getId());
                bundle.putSerializable("entity",  entity);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        initData();
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
                .showImageOnLoading(R.mipmap.suit)
                .showImageForEmptyUri(R.mipmap.suit)
                .showImageOnFail(R.mipmap.suit)
                .cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(200))
                .build();
        imageLoader.displayImage(entity.getFace_pic(), img, options);
    }
    private void initData() {
        list = MyCustome.myCustome.SendList();
        for (int i = 0; i < list.size(); i++) {
            MyCusdtomEntity.ListEntity listEntity = new MyCusdtomEntity.ListEntity();

            if (list.get(i).getTitle().equals("西装")) {
                listEntity.setId(list.get(i).getId());
                listEntity.setTitle(list.get(i).getTitle());
                listEntity.setPid(list.get(i).getPid());
                listEntity.setFace_pic(list.get(i).getFace_pic());
                listEntity.set_child(list.get(i).get_child());
                entity = listEntity;
            }
        }
        imgs();
    }

    public static MyC_Suit newInstance() {
        MyC_Suit fragment = new MyC_Suit();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
