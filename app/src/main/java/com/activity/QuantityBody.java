package com.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.common.Token;
import com.custom.MaterialDialog;
import com.custom.MyDatePickDialog;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.xzdz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huisou on 2015/11/2.
 * 预约量体
 */
public class QuantityBody extends LActivity implements View.OnClickListener {
    private RelativeLayout rl_province, rl_timed, rl_dtime;
    private EditText et_name, et_phone, et_xaddr, tv_province, tv_ymd;
    private Button btn_sure;
    private TextView tv_dtime;
    private String provinceID, cityID, countryID;
    private List<Map<String, String>> time_list = new ArrayList<>();
    private SimpleAdapter dialogAda;
    private ListView listView;
    private MaterialDialog materialDialog;
    private String order_id;
    private String name, phone, xaddr, years, daytime;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_quantitybody);
        initBar();
        initView();
        setTimeData();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_quantitybody));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setTimeData() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "0");
        map.put("name", "8:00~9:00");
        time_list.add(map);
        map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "9:00~10:00");
        time_list.add(map);
        map = new HashMap<>();
        map.put("id", "2");
        map.put("name", "10:00~11:00");
        time_list.add(map);
        map = new HashMap<>();
        map.put("id", "3");
        map.put("name", "11:00~12:00");
        time_list.add(map);
        map = new HashMap<>();
        map.put("id", "4");
        map.put("name", "13:00~14:00");
        time_list.add(map);
        map = new HashMap<>();
        map.put("id", "5");
        map.put("name", "14:00~15:00");
        time_list.add(map);
        map = new HashMap<>();
        map.put("id", "6");
        map.put("name", "15:00~16:00");
        time_list.add(map);
        map = new HashMap<>();
        map.put("id", "7");
        map.put("name", "16:00~17:00");
        time_list.add(map);
        map = new HashMap<>();
        map.put("id", "8");
        map.put("name", "17:00~18:00");
        time_list.add(map);

    }

    private void initView() {
        rl_province = (RelativeLayout) findViewById(R.id.rl_address);
        rl_timed = (RelativeLayout) findViewById(R.id.rl_time);
        rl_dtime = (RelativeLayout) findViewById(R.id.rl_timesd);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_xaddr = (EditText) findViewById(R.id.et_xxaddr);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        tv_province = (EditText) findViewById(R.id.tv_bbb);
        tv_dtime = (TextView) findViewById(R.id.tv_daysd);
        tv_ymd = (EditText) findViewById(R.id.tv_day);
        rl_province.setOnClickListener(this);
        rl_timed.setOnClickListener(this);
        rl_dtime.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
        if (getIntent().getExtras().getString("typeQid").equals("1")) {
            order_id = getIntent().getExtras().getString("order_id");
        } else if (getIntent().getExtras().getString("typeQid").equals("2")) {
            order_id = getIntent().getExtras().getString("order_id");
            String name = getIntent().getExtras().getString("name");
            String year = getIntent().getExtras().getString("year");
            String phone = getIntent().getExtras().getString("phone");
            String address = getIntent().getExtras().getString("address");
            String daytime = getIntent().getExtras().getString("daytime");
            String pc = getIntent().getExtras().getString("pppp");
            et_name.setText(name);
            et_phone.setText(phone);
            et_xaddr.setText(address);
            tv_ymd.setText(year);
            tv_dtime.setText(daytime);
            tv_province.setText(pc);
        }


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_address) {
            T.ss("省市区");
            Intent intent = new Intent(this, ProviceInfoPlace.class);
            startActivityForResult(intent, 1);
        }
        if (id == R.id.rl_time) {
            //T.ss("年月日");
            Calendar c = Calendar.getInstance();
            MyDatePickDialog datePicker = new MyDatePickDialog(QuantityBody.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    tv_ymd.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

            datePicker.show();
        }
        if (id == R.id.rl_timesd) {
            T.ss("预约时间段");
            dialogAda = new SimpleAdapter(this, time_list,
                    R.layout.dialog_list_time, new String[]{"name"},
                    new int[]{R.id.tv});
            dialogShow();

        }
        if (id == R.id.btn_sure) {
            submitData();
            //T.ss("确定");
        }
    }

    private void initClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_dtime.setText(time_list.get(position).get("name"));
                materialDialog.dismiss();
            }
        });
    }

    public void dialogShow() {
        listView = new ListView(this);
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
        listView.setDividerHeight(0);
        listView.setAdapter(dialogAda);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        initClick();
        materialDialog = new MaterialDialog(this).setTitle("请选择")
                .setContentView(listView);
        materialDialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 1:
                if (data != null) {
                    String name = data.getExtras().getString("name");
                    tv_province.setText(name);
                    provinceID = data.getExtras().getString("pid"); // 省ID
                    cityID = data.getExtras().getString("cityid");   //  市ID
                    if (data.getExtras().getString("countryid") == null) {
                        countryID = null;
                    } else {
                        countryID = data.getExtras().getString("countryid"); // 区ID
                    }
                }
                break;
            default:
                break;
        }
    }


    private void submitData() {
        // app/order/appointment/sign/aggregation
        name = et_name.getText().toString();
        phone = et_phone.getText().toString();
        xaddr = et_xaddr.getText().toString();
        years = tv_ymd.getText().toString();
        daytime = tv_dtime.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("order_id", order_id);
        map.put("uname", name);//收件人姓名
        map.put("uphone", phone);//手机号
        map.put("address", xaddr);//详细地址
        map.put("city", cityID);//
        map.put("country", countryID);//
        map.put("province", provinceID);//
        map.put("ymd", years);//年月日
        map.put("time", daytime);//时间
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/order/appointment/sign/aggregation/?uuid=" + Token.get(this);
        LReqEntity entity = new LReqEntity(url, map);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);
       // L.e(entity.toString());
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
                // name,phone,xaddr,years,daytime;
                T.ss(jsonObject.getString("info"));
                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("xaddr", xaddr);
                intent.putExtra("years", years);
                intent.putExtra("pppc", tv_province.getText().toString());
                intent.putExtra("daytime", daytime);
                setResult(1, intent);
                finish();
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
