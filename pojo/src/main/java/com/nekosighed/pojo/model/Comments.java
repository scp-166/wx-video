package com.nekosighed.pojo.model;

import java.io.Serializable;
import java.util.Date;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
public class Comments implements Serializable {
    /**
     * 
     */
    private String id;

    /**
     * 
     */
    private String fatherCommentId;

    /**
     * 
     */
    private String toUserId;

    /**
     * 视频id
     */
    private String videoId;

    /**
     * 留言者，评论的用户id
     */
    private String fromUserId;

    /**
     * 
     */
    private Date createTime;

    /**
     * 评论内容
     */
    private String comment;

    /**
     * comments
     */
    private static final long serialVersionUID = 1L;

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    public Comments(String id, String fatherCommentId, String toUserId, String videoId, String fromUserId, Date createTime, String comment) {
        this.id = id;
        this.fatherCommentId = fatherCommentId;
        this.toUserId = toUserId;
        this.videoId = videoId;
        this.fromUserId = fromUserId;
        this.createTime = createTime;
        this.comment = comment;
    }

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    public Comments() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFatherCommentId() {
        return fatherCommentId;
    }

    public void setFatherCommentId(String fatherCommentId) {
        this.fatherCommentId = fatherCommentId == null ? null : fatherCommentId.trim();
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId == null ? null : toUserId.trim();
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId == null ? null : videoId.trim();
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId == null ? null : fromUserId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}