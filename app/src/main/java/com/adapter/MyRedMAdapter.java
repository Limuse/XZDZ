package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.MyRedbEntity;
import com.xzdz.R;

import java.util.List;

/**
 * Created by huisou on 2015/11/23.
 */
public class MyRedMAdapter extends BaseAdapter {
    private Context context;
    private List<MyRedbEntity> list;

    public MyRedMAdapter(Context context, List<MyRedbEntity> list) {
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
        HolderView holderView = null;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_redbox, null);
            holderView.tv_money = (TextView) convertView.findViewById(R.id.mongy);
            holderView.tv_text = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        MyRedbEntity myRedbEntity = list.get(position);
        holderView.tv_money.setText(myRedbEntity.getMoney());
        holderView.tv_text.setText(myRedbEntity.getEnd_time());
        return convertView;
    }

    public class HolderView {
        private TextView tv_money;
        private TextView tv_text;
    }
}
