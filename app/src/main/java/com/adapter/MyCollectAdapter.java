package com.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
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
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xzdz.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisou on 2015/11/6.
 */
public class MyCollectAdapter extends BaseAdapter {
    private Context context;
    private List<MyWorkedEntity> list;
    private Boolean flg = false;
    private MyCollect ins;
    private String ids;
    private TakeAsyncTask Task;
    private int ppp;

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
        final MyWorkedEntity en = list.get(position);
        viewHolder.tv_money.setText(en.getTime());
        viewHolder.tv_title.setText(en.getTitle());
        viewHolder.posti = position;
        ids = en.getId();
        if (flg == true) {
            viewHolder.del.setVisibility(View.VISIBLE);
            viewHolder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ppp = position;
                    Task = new TakeAsyncTask();
                    Task.execute(en.getId());
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
        imageLoader.displayImage(en.getImg(), viewHolder.img, options);

        return convertView;
    }

    private class TakeAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            if (Task.isCancelled() == true) {
                return null;
            }
            String result = null;
            String url = null;
            Resources res = context.getResources();
            url = res.getString(R.string.app_service_url)
                    + "/app/article/delarticle/sign/aggregation/?uuid=" + Token.get(context) + "&id=" + params[0];
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
                    T.ss("数据已删除");
                    list.remove(ppp);
                    notifyDataSetChanged();
                } else {
                    T.ss(jsonObject.getString("msg"));
                }
            } catch (Exception e) {
                e.printStackTrace();
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
