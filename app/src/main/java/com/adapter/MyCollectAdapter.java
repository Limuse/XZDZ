package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.entity.MyWorkedEntity;
import com.leo.base.util.T;
import com.xzdz.R;

import java.util.List;

/**
 * Created by huisou on 2015/11/6.
 */
public class MyCollectAdapter extends BaseAdapter {
    private Context context;
    private List<MyWorkedEntity> list;
    private Boolean flg = false;

    public MyCollectAdapter(Context context, List<MyWorkedEntity> list) {
        this.context = context;
        this.list = list;
    }

    public Boolean Update(Boolean bbb) {
        flg = bbb;
        return flg;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_mycollect, null);
            viewHolder.del = (RelativeLayout) convertView.findViewById(R.id.dels);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.gv_imgs);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.gv_titles);
            viewHolder.tv_money = (TextView) convertView.findViewById(R.id.gv_money);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyWorkedEntity entity = list.get(position);
        viewHolder.tv_money.setText(entity.getTime());
        viewHolder.tv_title.setText(entity.getTitle());
        if (flg == true) {
            viewHolder.del.setVisibility(View.VISIBLE);
            viewHolder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        T.ss("show");
                }
            });
        } else if (flg == false) {
            viewHolder.del.setVisibility(View.GONE);
        }
        return convertView;
    }

    public class ViewHolder {
        private ImageView img;
        private RelativeLayout del;
        private TextView tv_title;
        private TextView tv_money;

    }
}
