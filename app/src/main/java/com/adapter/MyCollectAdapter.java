package com.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.MyApplication;
import com.activity.MyCollect;
import com.activity.MyCustome;
import com.common.Token;
import com.entity.MyWorkedEntity;
import com.handle.ActivityHandler;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xzdz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by huisou on 2015/11/6.
 */
public class MyCollectAdapter extends BaseAdapter {
    private Context context;
    private List<MyWorkedEntity> list;
    private Boolean flg = false;
    private MyCollect ins;
    private int inId;

    public MyCollectAdapter(Context context, List<MyWorkedEntity> list, MyCollect ins) {
        this.context = context;
        this.list = list;
        this.ins = ins;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        final MyWorkedEntity entity = list.get(position);
        viewHolder.tv_money.setText(entity.getTime());
        viewHolder.tv_title.setText(entity.getTitle());
        viewHolder.posti = position;
        if (flg == true) {
            viewHolder.del.setVisibility(View.VISIBLE);
            viewHolder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // /app/article/storearticle/sign/aggregation/
                    Resources res = context.getResources();
                    String url = res.getString(R.string.app_service_url)
                            + "app/article/delarticle/sign/aggregation/?" + "uuid=" + Token.get(context) + "&id=" + entity.getId();
                    LReqEntity entity = new LReqEntity(url);
                    // Fragment用FragmentHandler/Activity用ActivityHandler
                    ActivityHandler handler = new ActivityHandler(ins);
                    handler.startLoadingData(entity, 2);

                }
            });
        } else if (flg == false) {
            viewHolder.del.setVisibility(View.GONE);
        }

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
        imageLoader.displayImage(entity.getImg(), viewHolder.img, options);

        return convertView;
    }

    private void getJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                T.ss(jsonObject.getString("info"));
                //list.remove(inId);
                notifyDataSetChanged();
            }else{
                T.ss(jsonObject.getString("info"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        ins.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 2) {
                getJsonData(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }

    public class ViewHolder {
        private ImageView img;
        private RelativeLayout del;
        private TextView tv_title;
        private TextView tv_money;
        private int posti;
    }
}
