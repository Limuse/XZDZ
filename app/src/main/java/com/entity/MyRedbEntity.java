package com.entity;

import java.util.List;

/**
 * Created by huisou on 2015/11/23.
 * 红包
 */
public class MyRedbEntity {
    /**
     * id : 1
     * coupon_id : 1
     * end_time : 2015-12-07 00:00:00
     * money : 10.00
     */

    private String id;
    private String coupon_id;
    private String end_time;
    private String money;

    public void setId(String id) {
        this.id = id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getMoney() {
        return money;
    }
}

