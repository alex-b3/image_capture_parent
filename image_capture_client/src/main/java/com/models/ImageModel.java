package com.models;

import javax.validation.constraints.NotNull;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by alex.bichovsky on 3/22/2017.
 */
@Entity
@Table(name = "images",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @Column(name = "date")
    private Date date;

    @NotNull
    private String url;

    @NotNull
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

    public ImageModel(){}

    public ImageModel(long id, String name, Date date, String url, String localPath) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.url = url;
        this.localPath = localPath;
    }
}
