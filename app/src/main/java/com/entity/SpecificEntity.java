package com.entity;

import java.util.List;

/**
 * Created by admin on 2015/11/26.
 */
public class SpecificEntity {

    /**
     * list : [{"pants":"","waist_top":null,"type":"1","uid":"1","knee":"","hipline":"","waicuff":"","sleeve":"","circumference":"","name":"after","waist":"","bust":"","id":"4","after":null,"shoulder":"","foot":""},{"pants":"","waist_top":null,"type":"1","uid":"1","knee":"","hipline":"","waicuff":"","sleeve":"","circumference":"","name":"waist","waist":"","bust":"","id":"5","after":null,"shoulder":"","foot":""},{"pants":"","waist_top":null,"type":"1","uid":"1","knee":"","hipline":"","waicuff":"","sleeve":"","circumference":"","name":"hipline","waist":"","bust":"","id":"6","after":null,"shoulder":"","foot":""},{"pants":"","waist_top":null,"type":"1","uid":"1","knee":"","hipline":"","waicuff":"","sleeve":"","circumference":"","name":"sleeve","waist":"","bust":"","id":"7","after":null,"shoulder":"","foot":""},{"pants":"","waist_top":null,"type":"1","uid":"1","knee":"","hipline":"","waicuff":"","sleeve":"","circumference":"","name":"circumference","waist":"","bust":"","id":"8","after":null,"shoulder":"","foot":""},{"pants":"","waist_top":null,"type":"1","uid":"1","knee":"","hipline":"","waicuff":"","sleeve":"","circumference":"","name":"knee ","waist":"","bust":"","id":"9","after":null,"shoulder":"","foot":""},{"pants":"","waist_top":null,"type":"1","uid":"1","knee":"","hipline":"","waicuff":"","sleeve":"","circumference":"","name":"waicuff","waist":"","bust":"","id":"10","after":null,"shoulder":"","foot":""},{"pants":"","waist_top":null,"type":"1","uid":"1","knee":"","hipline":"","waicuff":"","sleeve":"","circumference":"","name":"foot","waist":"","bust":"","id":"11","after":null,"shoulder":"","foot":""},{"pants":"","waist_top":null,"type":"1","uid":"1","knee":"","hipline":"","waicuff":"","sleeve":"","circumference":"","name":"waist_top","waist":"","bust":"","id":"12","after":null,"shoulder":"","foot":""},{"pants":"","waist_top":null,"type":"1","uid":"1","knee":"","hipline":"","waicuff":"","sleeve":"","circumference":"","name":"pants","waist":"","bust":"","id":"13","after":null,"shoulder":"","foot":""}]
     * status : 1
     */
    private List<ListEntity> list;
    private int status;

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public int getStatus() {
        return status;
    }

    public static class ListEntity {
        /**
         * pants :
         * waist_top : null
         * type : 1
         * uid : 1
         * knee :
         * hipline :
         * waicuff :
         * sleeve :
         * circumference :
         * name : after
         * waist :
         * bust :
         * id : 4
         * after : null
         * shoulder :
         * foot :
         */
        private String pants;
        private String waist_top;
        private String type;
        private String uid;
        private String knee;
        private String hipline;
        private String waicuff;
        private String sleeve;
        private String circumference;
        private String name;
        private String waist;
        private String bust;
        private String id;
        private String after;
        private String shoulder;
        private String foot;

        public void setPants(String pants) {
            this.pants = pants;
        }

        public void setWaist_top(String waist_top) {
            this.waist_top = waist_top;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setKnee(String knee) {
            this.knee = knee;
        }

        public void setHipline(String hipline) {
            this.hipline = hipline;
        }

        public void setWaicuff(String waicuff) {
            this.waicuff = waicuff;
        }

        public void setSleeve(String sleeve) {
            this.sleeve = sleeve;
        }

        public void setCircumference(String circumference) {
            this.circumference = circumference;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setWaist(String waist) {
            this.waist = waist;
        }

        public void setBust(String bust) {
            this.bust = bust;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setAfter(String after) {
            this.after = after;
        }

        public void setShoulder(String shoulder) {
            this.shoulder = shoulder;
        }

        public void setFoot(String foot) {
            this.foot = foot;
        }

        public String getPants() {
            return pants;
        }

        public String getWaist_top() {
            return waist_top;
        }

        public String getType() {
            return type;
        }

        public String getUid() {
            return uid;
        }

        public String getKnee() {
            return knee;
        }

        public String getHipline() {
            return hipline;
        }

        public String getWaicuff() {
            return waicuff;
        }

        public String getSleeve() {
            return sleeve;
        }

        public String getCircumference() {
            return circumference;
        }

        public String getName() {
            return name;
        }

        public String getWaist() {
            return waist;
        }

        public String getBust() {
            return bust;
        }

        public String getId() {
            return id;
        }

        public String getAfter() {
            return after;
        }

        public String getShoulder() {
            return shoulder;
        }

        public String getFoot() {
            return foot;
        }
    }
}
