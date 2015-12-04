package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.MyApplication;
import com.activity.Fabric;
import com.activity.FabricChangeSubmit;
import com.activity.MyWorkDetail;
import com.activity.Myworked;
import com.activity.OrderDetails;
import com.activity.ViersonChanges;
import com.common.CustomData;
import com.common.Token;
import com.entity.MyCusdtomEntity;
import com.entity.MyWorkedEntity;
import com.leo.base.util.L;
import com.leo.base.util.LSharePreference;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xzdz.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/11/5.
 */
public class MyWorkedGridvAdapter extends BaseAdapter {
    private Context context;
    private List<MyWorkedEntity> list;
    private Boolean flg = false;
    private TakeAsyncTask Task;
    private Myworked myworked;
    private int ppp;
    private CustomData cdata;

    public MyWorkedGridvAdapter(Context context, List<MyWorkedEntity> list, Myworked myworked) {
        this.context = context;
        this.list = list;
        this.myworked = myworked;
        cdata = new CustomData(myworked);
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

                    ppp = position;
                    Task = new TakeAsyncTask();
                    Task.execute(map.getId(), "0");


                }
            });
        }
        viewHolder.rlll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // T.ss(map.getId() + "详情页");
                Intent intent = new Intent(context, MyWorkDetail.class);
                intent.putExtra("pid", map.getId());
                context.startActivity(intent);
            }
        });
        viewHolder.btn_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // T.ss(map.getId() + "继续定制");
                String type = map.getType();

                ppp = position;


                Task = new TakeAsyncTask();
                Task.execute(map.getId(), map.getType());


            }
        });
        return convertView;
    }


    private class TakeAsyncTask extends AsyncTask<String, Void, String> {
        private String TypeDatas = null;
        private String mapID = null;

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            if (Task.isCancelled() == true) {
                return null;
            }
            mapID = params[0];
            TypeDatas = params[1];
            String result = null;
            String url = null;
            Resources res = context.getResources();
            if (TypeDatas.equals("1")) {
                url = res.getString(R.string.app_service_url)
                        + "/app/product/continuesuit/sign/aggregation/?uuid=" + Token.get(context) + "&product_id=" + params[0];

            }
            if (TypeDatas.equals("2")) {
                url = res.getString(R.string.app_service_url)
                        + "/app/product/continuesuit/sign/aggregation/?uuid=" + Token.get(context) + "&product_id=" + params[0];

            }
            if (TypeDatas.equals("3")) {
                url = res.getString(R.string.app_service_url)
                        + "/app/product/continuesuit/sign/aggregation/?uuid=" + Token.get(context) + "&product_id=" + params[0];

            }
            if (TypeDatas.equals("0")) {
                url = res.getString(R.string.app_service_url)
                        + "/app/product/deletework/sign/aggregation/?uuid=" + Token.get(context) + "&product_id=" + params[0];

            }
            //

            HttpGet request = new HttpGet(url);
            try {
                HttpResponse httpResponse = new DefaultHttpClient().execute(request);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    result = EntityUtils.toString(httpResponse.getEntity());
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("status");
                if (code == 1) {
                    if (TypeDatas.equals("0")) {
                        T.ss("数据已删除");
                        list.remove(ppp);
                        notifyDataSetChanged();
                    }
                    if (TypeDatas.equals("1")) {
                        JSONObject jsonOb = jsonObject.getJSONObject("list");
                        String cid_first = jsonOb.getString("cid_first");
                        ArrayList<MyCusdtomEntity.ListEntity.ChildEntity> allentity = (ArrayList) cdata.OneData(cid_first);
                        LSharePreference.getInstance(context).setString("cid_first",
                                cid_first);
                        if (allentity.size() > 0) {
                            Intent intent = new Intent(context, Fabric.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("allentity", allentity);
                            bundle.putString("productId", mapID);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        } else {
                            T.ss("数据访问失败");
                        }
                    }
                    if (TypeDatas.equals("2")) {
                        JSONObject jsonOb = jsonObject.getJSONObject("list");
                        String cid_first = jsonOb.getString("cid_first");
                        LSharePreference.getInstance(context).setString("cid_first",
                                cid_first);
                        ArrayList<MyCusdtomEntity.ListEntity.ChildEntity> allentity = (ArrayList) cdata.OneData(cid_first);
                        if (allentity.size() > 0) {
                            Intent intent = new Intent(context, FabricChangeSubmit.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("allentity", allentity);
                            bundle.putString("productId", mapID);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        } else {
                            T.ss("数据访问失败");
                        }
                    }
                    if (TypeDatas.equals("3")) {
                        JSONObject jsonOb = jsonObject.getJSONObject("list");
                        String order_id = jsonOb.getString("order_id");
                        //L.e(order_id + "LLLLLLLLLLLLL");
                        Intent intent = new Intent(context, OrderDetails.class);
                        intent.putExtra("order_id", order_id);
                        context.startActivity(intent);
                    }

                } else {
                    T.ss(jsonObject.getString("info"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public class ViewHolder {
        private RelativeLayout img_del, rlll;
        private ImageView img_pic;
        private TextView tv_title;
        private TextView tv_time;
        private Button btn_con;

    }
}
