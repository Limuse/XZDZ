package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xzdz.R;

/**
 * Created by huisou on 2015/11/5.
 */
public class MyWorkedGridvAdapter extends BaseAdapter {
    private Context context;
    public MyWorkedGridvAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 0;
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
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_myworked, null);
            viewHolder.img_del=(ImageView)convertView.findViewById(R.id.del);
            viewHolder.img_pic=(ImageView)convertView.findViewById(R.id.gv_img);
            viewHolder.tv_title=(TextView)convertView.findViewById(R.id.gv_title);
            viewHolder.tv_time=(TextView)convertView.findViewById(R.id.gv_time);
            viewHolder.btn_con=(Button)convertView.findViewById(R.id.btn_gv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    public class ViewHolder{
        private ImageView img_del;
        private ImageView img_pic;
        private TextView tv_title;
        private TextView tv_time;
        private Button btn_con;

    }
}
