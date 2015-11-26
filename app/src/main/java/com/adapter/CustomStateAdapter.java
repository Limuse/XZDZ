package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.entity.MetailflowEntity;
import com.xzdz.R;

import java.util.List;

/**
 * Created by huisou on 2015/11/3.
 */
public class CustomStateAdapter extends BaseAdapter {
    private Context context;
    private List<MetailflowEntity.DataEntity> list;

    public CustomStateAdapter(Context context,List<MetailflowEntity.DataEntity> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
         return list.size();
    }

    @Override
    public Object getItem(int position) {
         return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_metilflow, null);
            viewHolder.view1 = (View) convertView.findViewById(R.id.viewy1);
            viewHolder.view2 = (View) convertView.findViewById(R.id.viewy2);
            viewHolder.view3 = (View) convertView.findViewById(R.id.viewy3);
            viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv_tvmw);
            viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv_timemw);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.view1.setVisibility(View.VISIBLE);
        viewHolder.view2.setVisibility(View.VISIBLE);
        int iid = position;
        if (iid == 0) {

            viewHolder.view1.setVisibility(View.GONE);
            viewHolder.view2.setVisibility(View.GONE);
            viewHolder.view3.setVisibility(View.VISIBLE);
            viewHolder.iv_img.setBackground(context.getResources().getDrawable(R.mipmap.yus));
            viewHolder.tv1.setTextColor(context.getResources().getColor(R.color.app_green));
            viewHolder.tv2.setTextColor(context.getResources().getColor(R.color.app_green));

        }

       MetailflowEntity.DataEntity entity = list.get(position);
        viewHolder.tv1.setText(entity.getContext());
        viewHolder.tv2.setText(entity.getFtime());

        return convertView;
    }

    private class ViewHolder {
        private TextView tv1;
        private TextView tv2;
        private View view1;
        private View view2;
        private View view3;
        private ImageView iv_img;
        private int position;
    }
}
