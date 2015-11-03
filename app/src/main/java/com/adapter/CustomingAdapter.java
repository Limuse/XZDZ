package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.MyApplication;
import com.entity.CustomingEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xzdz.R;

import java.util.List;

/**
 * Created by huisou on 2015/11/3.
 */
public class CustomingAdapter extends BaseAdapter {
    private Context context;
    private List<CustomingEntity> listcu;

    public CustomingAdapter(Context context, List<CustomingEntity> listcu) {
        this.context = context;
        this.listcu = listcu;

    }

    @Override
    public int getCount() {
        return listcu.size();
    }

    @Override
    public Object getItem(int position) {
        return listcu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_customing, null);
            viewholder.img = (ImageView) convertView.findViewById(R.id.list_img);
            viewholder.tv_state = (TextView) convertView.findViewById(R.id.list_lint);
            viewholder.tv_title = (TextView) convertView.findViewById(R.id.list_title);
            viewholder.tv_num = (TextView) convertView.findViewById(R.id.list_num);
            viewholder.tv_time = (TextView) convertView.findViewById(R.id.list_time);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        CustomingEntity cuentity = listcu.get(position);
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
        imageLoader.displayImage(cuentity.getImgs(), viewholder.img, options);
        viewholder.tv_state.setText(cuentity.getState());
        viewholder.tv_title.setText(cuentity.getTitle());
        viewholder.tv_num.setText(cuentity.getNum());
        viewholder.tv_time.setText(cuentity.getTime());
        return convertView;
    }

    private class ViewHolder {
        private ImageView img;
        private TextView tv_state;
        private TextView tv_title;
        private TextView tv_num;
        private TextView tv_time;
    }
}
