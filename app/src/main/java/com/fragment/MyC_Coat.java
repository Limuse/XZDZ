package com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.activity.ViersonChange;
import com.leo.base.activity.fragment.LFragment;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/29.
 * 上衣
 */
public class MyC_Coat extends LFragment {
    private ImageView img;
    private Button btn_next;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myccoat, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        img = (ImageView) getActivity().findViewById(R.id.coat_img);
        btn_next = (Button) getActivity().findViewById(R.id.next1);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViersonChange.class);
                startActivity(intent);
            }
        });
    }

    public static MyC_Coat newInstance() {
        MyC_Coat fragment = new MyC_Coat();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
