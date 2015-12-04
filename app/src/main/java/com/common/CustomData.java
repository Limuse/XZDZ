package com.common;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.entity.MyCusdtomEntity;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.xzdz.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/12/4.
 */
public class CustomData {
    private TakeAsyncTask Task;
    private List<MyCusdtomEntity.ListEntity> list = new ArrayList<>();
    private List<MyCusdtomEntity.ListEntity.ChildEntity> childlist = new ArrayList<>();
    private List<MyCusdtomEntity.ListEntity.ChildEntity.childV> chivlist = new ArrayList<>();
    private LActivity context;

    public CustomData(LActivity context) {
        this.context = context;
        Task = new TakeAsyncTask();
        Task.execute();
    }

    private class TakeAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            if (Task.isCancelled() == true) {
                return null;
            }

            String result = null;
            String url = null;//app/product/deletework/sign/aggregation/?uuid=67a74d37afc24bd68c850ae6ebdfdd7d&product_id=16
            Resources res = context.getResources();
            url = res.getString(R.string.app_service_url)
                    + "/app/product/categoryinfo/sign/aggregation/?uuid=" + Token.get(context);
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
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("status");
                if (code == 1) {
                    JSONArray array = jsonObject.getJSONArray("list");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        MyCusdtomEntity.ListEntity listEntity = new MyCusdtomEntity.ListEntity();
                        listEntity.setId(object.getString("id"));
                        listEntity.setPid(object.getString("pid"));
                        listEntity.setTitle(object.getString("title"));
                        listEntity.setFace_pic(object.getString("face_pic"));
                        JSONArray array_child = object.getJSONArray("_child");

                        for (int t = 0; t < array_child.length(); t++) {
                            JSONObject ob = array_child.getJSONObject(t);
                            MyCusdtomEntity.ListEntity.ChildEntity childEntity = new MyCusdtomEntity.ListEntity.ChildEntity();
                            childEntity.setId(ob.getString("id"));
                            childEntity.setPid(ob.getString("pid"));
                            childEntity.setTitle(ob.getString("title"));
                            childEntity.setFace_pic(ob.getString("face_pic"));
                            if (!ob.opt("_child").equals("")) {
                                JSONArray arr = ob.getJSONArray("_child");

                                for (int o = 0; o < arr.length(); o++) {
                                    JSONObject jsd = arr.getJSONObject(o);
                                    MyCusdtomEntity.ListEntity.ChildEntity.childV childV = new MyCusdtomEntity.ListEntity.ChildEntity.childV();
                                    childV.setId(jsd.getString("id"));
                                    childV.setPid(jsd.getString("pid"));
                                    childV.setTitle(jsd.getString("title"));
                                    childV.setFace_pic(jsd.getString("face_pic"));
                                    childV.set_child(jsd.getString("_child"));
                                    chivlist.add(childV);
                                }

                            } else {
                                String as = ob.getString("_child");
                            }

                            childEntity.set_child(chivlist);
                            childlist.add(childEntity);
                        }
                        listEntity.set_child(childlist);
                        list.add(listEntity);
                    }
                } else {
                    //  T.ss(jsonObject.getString("info").toString());
                    //String longs = jsonObject.getString("info");
//                if (longs.equals("请先登录")) {
//                    LSharePreference.getInstance(this).setBoolean("login", false);
//                    Intent intent = new Intent(this, LoginMain.class);
//                    startActivity(intent);
//                }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


    public List<MyCusdtomEntity.ListEntity.ChildEntity> OneData(String cid) {
        List<MyCusdtomEntity.ListEntity.ChildEntity> childEntity=null;
        MyCusdtomEntity.ListEntity listEntity = new MyCusdtomEntity.ListEntity();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(cid)) {
                listEntity.setId(list.get(i).getId());
                listEntity.setPid(list.get(i).getPid());
                listEntity.set_child(list.get(i).get_child());
                listEntity.setTitle(list.get(i).getTitle());
                childEntity = listEntity.get_child();
            }
        }
        return childEntity;
    }


}
