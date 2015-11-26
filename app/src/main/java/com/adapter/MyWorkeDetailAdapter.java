package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.MyApplication;
import com.custom.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xzdz.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/11/26.
 */
public class MyWorkeDetailAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> map = new ArrayList<>();

    public MyWorkeDetailAdapter(Context context, List<Map<String, String>> map) {
        this.context = context;
        this.map = map;
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return map.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView = null;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_myworkedetailitem, null);
            holderView.gimg = (CircleImageView) convertView.findViewById(R.id.detail_img);
            holderView.tv1 = (TextView) convertView.findViewById(R.id.tv_dd1);
            holderView.tv2 = (TextView) convertView.findViewById(R.id.tv_dd2);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        Map<String, String> mmp = map.get(position);
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
        imageLoader.displayImage(mmp.get("img"), holderView.gimg, options);
        holderView.tv1.setText(mmp.get("tv1"));
        holderView.tv2.setText(mmp.get("tv2"));


        return convertView;
    }

    private class HolderView {
        private CircleImageView gimg;
        private TextView tv1;
        private TextView tv2;
    }
}
