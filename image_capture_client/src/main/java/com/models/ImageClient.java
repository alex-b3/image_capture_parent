package com.models;

import java.util.Date;

/**
 * Created by alex.bichovsky on 3/22/2017.
 */

public class ImageClient {
    private long id;
    private String name;
    private Date date;
    private String url;
    private String localPath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public ImageClient(){}

    public ImageClient(long id, String name, Date date, String url, String localPath) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.url = url;
        this.localPath = localPath;
    }
}
