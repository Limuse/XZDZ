package com.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.activity.ViersonChanges;
import com.common.Token;
import com.handle.ActivityHandler;
import com.handle.FragmentHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.xzdz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huisou on 2015/10/29.
 * 裤装  id=2
 */
public class MyC_Pants extends LFragment {
    private ImageView img;
    private Button btn_next;

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
        initData();
    }

    private void initView() {
        img = (ImageView) getActivity().findViewById(R.id.pant_img);
        btn_next = (Button) getActivity().findViewById(R.id.next2);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViersonChanges.class);
                startActivity(intent);
            }
        });
    }

    public static MyC_Pants newInstance() {
        MyC_Pants fragment = new MyC_Pants();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    private void initData() {
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/product/suit/sign/aggregation/";
        final Map<String, String> map = new HashMap<>();
        map.put("uuid", Token.get(getActivity()));
        map.put("id", "2");
        LReqEntity entity = new LReqEntity(url, map);
        // L.e(entity + "");
        FragmentHandler handler = new FragmentHandler(MyC_Pants.this);
        handler.startLoadingData(entity, 1);
    }

    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonSubmit(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }

    private void getJsonSubmit(String data) {
        //list.clear();
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject o = jsonObject.getJSONObject("list");


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
