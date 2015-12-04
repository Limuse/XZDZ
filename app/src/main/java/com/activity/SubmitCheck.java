package com.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.AboutActivitySy;
import com.common.Token;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.T;
import com.xzdz.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by huisou on 2015/11/4.
 * 提交审核-提交信息
 */
public class SubmitCheck extends LActivity implements View.OnClickListener {
    private LinearLayout rl_sc1, rl_sc2, rl_sc3, rl_sc4, rl_sc5;
    private Button btn_backfirst;
    private TextView tv_kfphones;
    private TextView tv2_reason;
    private TextView tv3_name, tv3_phone, tv3_addr;
    private TextView tv4_name, tv4_phone, tv4_addr;
    private TextView tv5_name, tv5_phone, tv5_addr;
    private String statenum;
    private String order_id;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_submitcheck);
        AboutActivitySy.getInstance().addActivity(this);
        initBar();
        initView();
        getData();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_afersalse));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/repair/repair/sign/aggregation/?" + "uuid=" + Token.get(this) + "&order_id=" + order_id;
        LReqEntity entity = new LReqEntity(url);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);
    }

    // 返回获取的网络数据
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            if (requestId == 1) {
                getJsonData(msg.getStr());
            } else {
                T.ss("获取数据失败");
            }
        }
    }

    private void getJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("status");
            if (code == 1) {
                if (statenum.equals("3")) {
                    JSONObject object = jsonObject.getJSONObject("list");
                    String reson = object.getString("reason");
                    tv2_reason.setText("原因:" + reson);
                    String stas = object.getString("status");
                }
                if (statenum.equals("4")) {
                    JSONObject object = jsonObject.getJSONObject("list");
                    String name = object.getString("uname");
                    String phone = object.getString("uphone");
                    String addr = object.getString("address");
                    String sts = object.getString("status");
                    tv3_name.setText(name);
                    tv3_phone.setText(phone);
                    tv3_addr.setText(addr);
                }
                if (statenum.equals("5")) {
                    JSONObject object = jsonObject.getJSONObject("list");
                    String name = object.getString("uname");
                    String phone = object.getString("uphone");
                    String addr = object.getString("address");
                    String sts = object.getString("status");
                    tv4_name.setText(name);
                    tv4_phone.setText(phone);
                    tv4_addr.setText(addr);

                }
                if (statenum.equals("6")) {
                    JSONObject object = jsonObject.getJSONObject("list");
                    String name = object.getString("uname");
                    String phone = object.getString("uphone");
                    String addr = object.getString("address");
                    String sts = object.getString("status");
                    tv5_name.setText(name);
                    tv5_phone.setText(phone);
                    tv5_addr.setText(addr);
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

    private void initView() {
        //获得审核状态和订单id
        statenum = getIntent().getExtras().getString("statenum");
        order_id = getIntent().getExtras().getString("order_id");


        rl_sc1 = (LinearLayout) findViewById(R.id.lly_sc1);
        rl_sc2 = (LinearLayout) findViewById(R.id.lly_sc2);
        rl_sc3 = (LinearLayout) findViewById(R.id.lly_sc3);
        rl_sc4 = (LinearLayout) findViewById(R.id.lly_sc4);
        rl_sc5 = (LinearLayout) findViewById(R.id.lly_sc5);
        rl_sc1.setVisibility(View.GONE);
        rl_sc2.setVisibility(View.GONE);
        rl_sc3.setVisibility(View.GONE);
        rl_sc4.setVisibility(View.GONE);
        rl_sc5.setVisibility(View.GONE);

        tv2_reason = (TextView) findViewById(R.id.tv_reason);
        tv3_name = (TextView) findViewById(R.id.scnames3);
        tv3_phone = (TextView) findViewById(R.id.scphone3);
        tv3_addr = (TextView) findViewById(R.id.scaddr3);
        tv4_name = (TextView) findViewById(R.id.scnames4);
        tv4_phone = (TextView) findViewById(R.id.scphone4);
        tv4_addr = (TextView) findViewById(R.id.scaddr4);
        tv5_name = (TextView) findViewById(R.id.scnames5);
        tv5_phone = (TextView) findViewById(R.id.scphone5);
        tv5_addr = (TextView) findViewById(R.id.scaddr5);
        tv_kfphones = (TextView) findViewById(R.id.scu_palyphone1);
        btn_backfirst = (Button) findViewById(R.id.backfirst);
        tv_kfphones.setOnClickListener(this);
        btn_backfirst.setOnClickListener(this);

        if (statenum.equals("0")) {
            rl_sc1.setVisibility(View.VISIBLE);
        }
        if (statenum.equals("2")) {
            rl_sc1.setVisibility(View.VISIBLE);
        }
        if (statenum.equals("3")) {
            rl_sc2.setVisibility(View.VISIBLE);
            btn_backfirst.setText("重新提交审核");
        }
        if (statenum.equals("4")) {
            rl_sc3.setVisibility(View.VISIBLE);
        }
        if (statenum.equals("5")) {
            rl_sc4.setVisibility(View.VISIBLE);
        }
        if (statenum.equals("6")) {
            rl_sc5.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.scu_palyphone1) {
            T.ss("电话");
        }
        if (id == R.id.backfirst) {
            if (statenum.equals("3")) {
                Intent intent = new Intent(this, AfterSale.class);
                intent.putExtra("repstate", "3");
                intent.putExtra("order_id", order_id);
                startActivity(intent);
            } else {
                T.ss("返回首页");
                AboutActivitySy.getInstance().exit();
                finish();
            }
        }
    }
}
