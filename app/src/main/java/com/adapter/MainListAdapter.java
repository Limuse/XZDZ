package com.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.MyApplication;
import com.activity.MainActivity;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xzdz.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/11/3.
 */
public class MainListAdapter extends BaseAdapter {

    private List<Map<String, String>> list;
    private Context context;

    public  MainListAdapter(List<Map<String, String>> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.activity_main_list_item, null);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.title1 = (TextView) convertView.findViewById(R.id.title1);
            viewHolder.title2 = (TextView) convertView.findViewById(R.id.title2);
            viewHolder.title3 = (TextView) convertView.findViewById(R.id.title3);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MainActivity.imageLoader.displayImage(list.get(position).get("image"), viewHolder.image, MainActivity.options);
        viewHolder.title1.setText(list.get(position).get("title1"));
        viewHolder.title2.setText(list.get(position).get("title2"));
        viewHolder.title3.setText(list.get(position).get("title3"));
        return convertView;
    }

    private class ViewHolder {
        ImageView image;
        TextView title1;
        TextView title2;
        TextView title3;
    }
}
