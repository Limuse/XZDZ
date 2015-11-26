package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.MyApplication;
import com.activity.MyWorkDetail;
import com.entity.MyWorkedEntity;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xzdz.R;

import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/11/5.
 */
public class MyWorkedGridvAdapter extends BaseAdapter {
    private Context context;
    private List<MyWorkedEntity> list;
    private Boolean flg = false;

    public MyWorkedGridvAdapter(Context context, List<MyWorkedEntity> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_myworked, null);
            viewHolder.rlll = (RelativeLayout) convertView.findViewById(R.id.rlll);
            viewHolder.img_del = (RelativeLayout) convertView.findViewById(R.id.del);
            viewHolder.img_pic = (ImageView) convertView.findViewById(R.id.gv_img);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.gv_title);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.gv_time);
            viewHolder.btn_con = (Button) convertView.findViewById(R.id.btn_gv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final MyWorkedEntity map = list.get(position);
        viewHolder.tv_title.setText(map.getTitle());
        viewHolder.tv_time.setText(map.getTime());
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
        imageLoader.displayImage(map.getImg(), viewHolder.img_pic, options);

        if (flg == false) {
            viewHolder.img_del.setVisibility(View.GONE);
        } else if (flg == true) {
            viewHolder.img_del.setVisibility(View.VISIBLE);
            viewHolder.img_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        viewHolder.rlll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.ss(map.getId() + "详情页");
                Intent intent = new Intent(context, MyWorkDetail.class);
                intent.putExtra("pid", map.getId());
                context.startActivity(intent);
            }
        });
        viewHolder.btn_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.ss(map.getId() + "继续定制");

            }
        });
        return convertView;
    }

    public class ViewHolder {
        private RelativeLayout img_del, rlll;
        private ImageView img_pic;
        private TextView tv_title;
        private TextView tv_time;
        private Button btn_con;

    }
}
