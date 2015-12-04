package com.entity;

/**
 * Created by huisou on 2015/11/30.
 */
public class VcEntity {
    /**
     * id: 1
     title: 单排一粒扣
     thumbnail: http://huihaokj.cn//U
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private String thumbnail;
}
