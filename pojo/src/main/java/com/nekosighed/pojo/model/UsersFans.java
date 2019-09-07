package com.nekosighed.pojo.model;

import java.io.Serializable;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
public class UsersFans implements Serializable {
    /**
     * 
     */
    private String id;

    /**
     * 用户
     */
    private String userId;

    /**
     * 粉丝
     */
    private String fanId;

    /**
     * users_fans
     */
    private static final long serialVersionUID = 1L;

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    public UsersFans(String id, String userId, String fanId) {
        this.id = id;
        this.userId = userId;
        this.fanId = fanId;
    }

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    public UsersFans() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getFanId() {
        return fanId;
    }

    public void setFanId(String fanId) {
        this.fanId = fanId == null ? null : fanId.trim();
    }
}