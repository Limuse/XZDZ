package com.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xzdz.R;

import java.util.ArrayList;

/**
 * Created by huisou on 2015/11/9.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> fileList;// = new ArrayList<String>();
    private ArrayList<Bitmap> bitmapList;// = new ArrayList<Bitmap>();
    public ImageAdapter(Context context,ArrayList<String> fileList,ArrayList<Bitmap> bitmapList){
        this.context=context;
        this.fileList=fileList;
        this.bitmapList=bitmapList;
    }
    public int getCount() {
        return fileList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.listview_imglist, null);
        ImageView imageView = (ImageView) v
                .findViewById(R.id.send_list_image);
        imageView.setImageBitmap(bitmapList.get(position));
        ImageView del = (ImageView) v.findViewById(R.id.send_list_del);
        del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fileList.remove(position);
                if (fileList.size() == 0) {
                }
                bitmapList.remove(position);
                notifyDataSetChanged();
            }
        });
        return v;
    }
}
