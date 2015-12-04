package com.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MyApplication;
import com.alipay.sdk.app.PayTask;
import com.common.AboutActivitySy;
import com.common.PayResult;
import com.common.SignUtils;
import com.common.Token;
import com.handle.ActivityHandler;
import com.leo.base.activity.LActivity;
import com.leo.base.entity.LMessage;
import com.leo.base.net.LReqEntity;
import com.leo.base.util.L;
import com.leo.base.util.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xzdz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by huisou on 2015/10/30.
 * 订单详情
 */
public class OrderDetails extends LActivity implements View.OnClickListener {
    private RelativeLayout rl_lt, rl_lts, rl_detail, rl_redbox;
    private ImageView img_info;
    private TextView tv_title, tv_mianl, tv_price, tv_yfprice, tv_red, tv_allprice, tv_name, tv_phone, tv_daytime, tv_year, tv_address;
    private Button btn_payfor;
    private EditText et_text;
    private String order_id;
    private String NAME, PHONE, DAYTIME, YEAR, ADDRESS;
    private String ppppp;

    @Override
    protected void onLCreate(Bundle bundle) {
        setContentView(R.layout.activity_orderdetails);
        AboutActivitySy.getInstance().addActivity(this);
        initBar();
        initView();
    }

    private void initBar() {
        // Bar.setTrans(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.app_orderdetail));
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_white));
        //toolbar.setBackgroundColor(Color.parseColor("#00ffffff"));
        toolbar.setNavigationIcon(R.mipmap.right_too);//左边图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        order_id = getIntent().getExtras().getString("order_id");

        rl_lt = (RelativeLayout) findViewById(R.id.rl_passs);
        rl_lts = (RelativeLayout) findViewById(R.id.rl_pass2);
        tv_name = (TextView) findViewById(R.id.tv_lname);
        tv_phone = (TextView) findViewById(R.id.tv_llphone);
        tv_daytime = (TextView) findViewById(R.id.tv_time);
        tv_year = (TextView) findViewById(R.id.tv_dtimes);
        tv_address = (TextView) findViewById(R.id.tv_addrs);
        rl_detail = (RelativeLayout) findViewById(R.id.rl_mails);
        rl_redbox = (RelativeLayout) findViewById(R.id.rl_clred);
        img_info = (ImageView) findViewById(R.id.img_img);
        tv_title = (TextView) findViewById(R.id.tv_tvtype);
        tv_mianl = (TextView) findViewById(R.id.tv_tvtypes);
        tv_price = (TextView) findViewById(R.id.tv_tvprice);
        tv_yfprice = (TextView) findViewById(R.id.img_right2);
        tv_red = (TextView) findViewById(R.id.tv_redbox);
        tv_allprice = (TextView) findViewById(R.id.tv_malls);
        btn_payfor = (Button) findViewById(R.id.btn_payfor);
        et_text = (EditText) findViewById(R.id.et_please_num);
        rl_lt.setOnClickListener(this);
        rl_lts.setOnClickListener(this);
        rl_detail.setOnClickListener(this);
        rl_redbox.setOnClickListener(this);
        btn_payfor.setOnClickListener(this);
        initData();
    }

    private void initData() {
        // app/order/order/sign/aggregation
        Resources res = getResources();
        String url = res.getString(R.string.app_service_url)
                + "/app/order/order/sign/aggregation/?uuid=" + Token.get(this) + "&order_id=" + order_id;
        LReqEntity entity = new LReqEntity(url);
        // Fragment用FragmentHandler/Activity用ActivityHandler
        ActivityHandler handler = new ActivityHandler(this);
        handler.startLoadingData(entity, 1);
        // L.e(url);
    }

    private void getJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            // L.e(data.toString());
            int code = jsonObject.getInt("status");
            if (code == 1) {
                JSONObject ob = jsonObject.getJSONObject("list");
                //量体信息
                JSONObject ap = ob.getJSONObject("appointment");
                String uname = ap.getString("uname");
                String uphone = ap.getString("uphone");
                String ymd = ap.getString("ymd");
                String time = ap.getString("time");
                String address = ap.getString("address");
                if (uname.equals("") || uphone.equals("") || ymd.equals("") || time.equals("") || address.equals("")) {
                    rl_lt.setVisibility(View.VISIBLE);
                    rl_lts.setVisibility(View.GONE);
                } else {
                    rl_lt.setVisibility(View.GONE);
                    rl_lts.setVisibility(View.VISIBLE);
                    NAME = uname;
                    PHONE = uphone;
                    DAYTIME = time;
                    YEAR = ymd;
                    ADDRESS = address;
                }
                tv_name.setText(NAME);
                tv_phone.setText(PHONE);
                tv_daytime.setText(DAYTIME);
                tv_year.setText(YEAR);
                tv_address.setText(ADDRESS);
                //商品清单信息
                JSONObject order = ob.getJSONObject("order");
                String img = order.getString("img");
                String title = order.getString("title");
                String parts = order.getString("parts");
                String deposit = order.getString("deposit");
                String paytype = order.getString("paytype");
                tv_title.setText(title);
                tv_mianl.setText(parts);
                tv_yfprice.setText(deposit);
                /**
                 * 图片需要处理
                 */
                ImageLoader imageLoader = null;

                // 图片
                if (imageLoader == null) {
                    imageLoader = MyApplication.getInstance().getImageLoader();
                }
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(false)
                        .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
                        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                        .build();
                imageLoader.displayImage(img, img_info, options);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_passs) {
            //T.ss("添加预约量体信息");
            Intent intent = new Intent(this, QuantityBody.class);
            intent.putExtra("order_id", order_id);
            intent.putExtra("typeQid", "1");
            startActivityForResult(intent, 1);
        }
        if (id == R.id.rl_pass2) {
            // T.ss("修改预约量体信息");
            Intent intent = new Intent(this, QuantityBody.class);
            intent.putExtra("typeQid", "2");
            intent.putExtra("order_id", order_id);
            intent.putExtra("name", NAME);
            intent.putExtra("year", YEAR);
            intent.putExtra("phone", PHONE);
            intent.putExtra("address", ADDRESS);
            intent.putExtra("pppp", ppppp);
            intent.putExtra("daytime", DAYTIME);
            startActivityForResult(intent, 1);
        }
        if (id == R.id.rl_mails) {
            //T.ss("商品清单");
        }
        if (id == R.id.rl_clred) {
            // T.ss("红包");
            Intent intent = new Intent(this, MyReam.class);
            startActivity(intent);
        }
        if (id == R.id.btn_payfor) {
            // T.ss("支付定金");
            String price = tv_yfprice.getText().toString();
            String title = tv_title.getText().toString();
            /**
             * 服务端返回支付宝的数据，然后支付成功后跳转-----支付成功界面
             */
            String orderInfo = getOrderInfo(title, order_id, price);
            // 对订单做RSA 签名
            String sign = sign(orderInfo);
            try {
                // 仅需对sign 做URL编码
                sign = URLEncoder.encode(sign, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // 完整的符合支付宝参数规范的订单信息
            final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                    + getSignType();

            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(OrderDetails.this);
                    // 调用支付接口，获取支付结果
                    String result = alipay.pay(payInfo);

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };

            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
//            Intent intent = new Intent(OrderDetails.this, SureOrder.class);
//            intent.putExtra("order_id", order_id);
//            startActivity(intent);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 1:
                /**
                 *  intent.putExtra("name", name);
                 intent.putExtra("phone", phone);
                 intent.putExtra("xaddr", xaddr);
                 intent.putExtra("years", years);
                 intent.putExtra("daytime", daytime);
                 */
                if (data != null) {
                    String name = data.getExtras().getString("name");
                    String phone = data.getExtras().getString("phone");
                    String xaddr = data.getExtras().getString("xaddr");
                    String years = data.getExtras().getString("years");
                    String pppc = data.getExtras().getString("pppc");
                    String daytime = data.getExtras().getString("daytime");
                    ppppp = pppc;
                    NAME = name;
                    PHONE = phone;
                    DAYTIME = daytime;
                    YEAR = years;
                    ADDRESS = xaddr;
                    rl_lts.setVisibility(View.VISIBLE);
                    rl_lt.setVisibility(View.GONE);
                }
                if (ppppp.equals(null)) {
                    tv_address.setText(ADDRESS);
                } else {
                    tv_address.setText(ppppp + ADDRESS);
                }
                tv_name.setText(NAME);
                tv_phone.setText(PHONE);
                tv_daytime.setText(DAYTIME);
                tv_year.setText(YEAR);

                break;
            default:
                break;
        }
    }


    /**
     * 支付宝相关
     *
     * @param bundle
     */

    //商户PID
    public static final String PARTNER = "2088801482639641";
    //商户收款账号
    public static final String SELLER = "173452787@qq.com";
    //商户私钥，pkcs8格式

    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMWLaDI92FYQLSLzf8ap1EyNeddulkMtfF/OfbeN2IFV9hEbRJ8aklz5ltONdDuDbpQYk/2u5faoR3p+2vBudTHLPaqSF/aGHhxqiE7msfCO7qK2l7P236IfeC7REhrsjMlHegR4khkTKGxJ3ViA8e3CphS+nDbcZ4RB98D3V9wZAgMBAAECgYB/TPQLlHEqHOiJYnNQGbcW0gDXhItOpSxGLTI0rDL+PeABbBoPkbcKNUkt8TUa+Pq0cxZDX4cQKadOWBtM31GjbmJw6cIDpGiFEtqAWeApomAepXzHhQkS6E5Q7Up2VdaWBDmZwylBEI+jzL9UFvl2wtfinyfiP9N+B8RAVj8akQJBAPsrbcN90/v0MtuHXHayhkKy3/SKXARDDipQcFvJXJoel89t5qI4+wymsUE9jrR3Rz94XhpK6n4VCcSqxC/BqnUCQQDJV/fvDm/GMNyxkEKsOQcaMkMYyP4m8ai48BdGdkAOjARqBFuWn08yxTsCl5NmZ39Sfm1SBBnrXxG+Rf9s8E6VAkEA4cGlRSOiGELkO4GtUFsiZm0U814xWvtSjiIC+90/yJ9a4Gzt8j25GSPzCqrjy72yYpA0NPnFT1Jll0nOUQaHyQJBALhErSF5GezNzT0opQxPiON9upCVapyjsGHJQdP35zopBOUuJdBc7rOtdAs0Q++Fqy4JjO1x2XLjaC62i0TdTV0CQQCvhigqDi1K7bVkGNQtfUZX7G/bmEYcaeXAQHH3p25RSeGoe554VF0PAItc1ruK7z/PbktIfoyXDEuYxR+D3UHV";
    //支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFi2gyPdhWEC0i83/GqdRMjXnXbpZDLXxfzn23jdiBVfYRG0SfGpJc+ZbTjXQ7g26UGJP9ruX2qEd6ftrwbnUxyz2qkhf2hh4caohO5rHwju6itpez9t+iH3gu0RIa7IzJR3oEeJIZEyhsSd1YgPHtwqYUvpw23GeEQffA91fcGQIDAQAB";


    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Intent intent = new Intent(OrderDetails.this, SureOrder.class);
                        intent.putExtra("order_id", order_id);
                        startActivity(intent);

                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderDetails.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OrderDetails.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                            // finish();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(OrderDetails.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        // orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
        orderInfo += "&out_trade_no=" + "\"" + body + "\"";
        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + getResources().getString(R.string.app_service_url) + "app/pay/success_back/sign/aggregation/?uuid=" + Token.get(this)
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
//         orderInfo += "&extern_token=" + "\"" +"http://huihaowfx.huisou.com/huihao/pay/success_back/1/sign/aggregation/"
//        + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
