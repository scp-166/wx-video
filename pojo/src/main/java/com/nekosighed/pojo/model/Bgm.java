package com.nekosighed.pojo.model;

import java.io.Serializable;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
public class Bgm implements Serializable {
    /**
     * 
     */
    private String id;

    /**
     * 
     */
    private String author;

    /**
     * 
     */
    private String name;

    /**
     * 播放地址
     */
    private String path;

    /**
     * bgm
     */
    private static final long serialVersionUID = 1L;

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    public Bgm(String id, String author, String name, String path) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.path = path;
    }

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    public Bgm() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }
}