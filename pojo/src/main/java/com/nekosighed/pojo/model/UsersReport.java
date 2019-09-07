package com.nekosighed.pojo.model;

import java.io.Serializable;
import java.util.Date;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
public class UsersReport implements Serializable {
    /**
     * 
     */
    private String id;

    /**
     * 被举报用户id
     */
    private String dealUserId;

    /**
     * 
     */
    private String dealVideoId;

    /**
     * 类型标题，让用户选择，详情见 枚举
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 举报人的id
     */
    private String userid;

    /**
     * 举报时间
     */
    private Date createDate;

    /**
     * users_report
     */
    private static final long serialVersionUID = 1L;

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    public UsersReport(String id, String dealUserId, String dealVideoId, String title, String content, String userid, Date createDate) {
        this.id = id;
        this.dealUserId = dealUserId;
        this.dealVideoId = dealVideoId;
        this.title = title;
        this.content = content;
        this.userid = userid;
        this.createDate = createDate;
    }

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    public UsersReport() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDealUserId() {
        return dealUserId;
    }

    public void setDealUserId(String dealUserId) {
        this.dealUserId = dealUserId == null ? null : dealUserId.trim();
    }

    public String getDealVideoId() {
        return dealVideoId;
    }

    public void setDealVideoId(String dealVideoId) {
        this.dealVideoId = dealVideoId == null ? null : dealVideoId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}