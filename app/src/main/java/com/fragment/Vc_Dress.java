package com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.activity.Fabric;
import com.activity.FabricChange;
import com.activity.ViersonChange;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.util.T;
import com.xzdz.R;

/**
 * Created by huisou on 2015/10/30.
 * 礼服
 */
public class Vc_Dress extends LFragment{
    private Button btn_next;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vcdress, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
//        img = (ImageView) getActivity().findViewById(R.id.coat_img);
        btn_next = (Button) getActivity().findViewById(R.id.nextc3);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Fabric.class);
                startActivity(intent);
               // T.ss("下一步");
            }
        });
    }

    public static Vc_Dress newInstance() {
        Vc_Dress fragment = new Vc_Dress();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}