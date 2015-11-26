package com.entity;

import java.util.List;

/**
 * Created by huisou on 2015/11/25.
 */
public class MetailflowEntity {

    /**
     * data : [{"time":"2015-11-24 01:44:32","context":"杭州转运中心 已发出,下一站 浙江省杭州市九堡","ftime":"2015-11-24 01:44:32"},{"time":"2015-11-24 01:23:04","context":"杭州转运中心 已收入","ftime":"2015-11-24 01:23:04"},{"time":"2015-11-23 22:31:10","context":"浙江省杭州市三墩西公司 已发出,下一站 杭州转运中心","ftime":"2015-11-23 22:31:10"},{"time":"2015-11-23 20:36:37","context":"浙江省杭州市三墩西公司 已打包","ftime":"2015-11-23 20:36:37"},{"time":"2015-11-23 20:00:23","context":"浙江省杭州市三墩西公司(点击查询电话) 已揽收","ftime":"2015-11-23 20:00:23"}]
     * state : 在途
     * delivery_name : 圆通快递
     * img : http://huihaokj.cn//Uploads/Admin/image/20151116/20151116114824_91183.jpg
     */

    private String state;
    private String delivery_name;
    private String img;
    private List<DataEntity> data;
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDelivery_name(String delivery_name) {
        this.delivery_name = delivery_name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public String getDelivery_name() {
        return delivery_name;
    }

    public String getImg() {
        return img;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * time : 2015-11-24 01:44:32
         * context : 杭州转运中心 已发出,下一站 浙江省杭州市九堡
         * ftime : 2015-11-24 01:44:32
         */

        private String time;
        private String context;
        private String ftime;

        public void setTime(String time) {
            this.time = time;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getTime() {
            return time;
        }

        public String getContext() {
            return context;
        }

        public String getFtime() {
            return ftime;
        }
    }
}
