package com.nekosighed.pojo.model;

import java.io.Serializable;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
public class SearchRecords implements Serializable {
    /**
     * 
     */
    private String id;

    /**
     * 搜索的内容
     */
    private String content;

    /**
     * search_records
     */
    private static final long serialVersionUID = 1L;

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    public SearchRecords(String id, String content) {
        this.id = id;
        this.content = content;
    }

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    public SearchRecords() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}