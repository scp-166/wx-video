package com.nekosighed.pojo.model;

import java.io.Serializable;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
public class UsersLikeVideos implements Serializable {
    /**
     * 
     */
    private String id;

    /**
     * 用户
     */
    private String userId;

    /**
     * 视频
     */
    private String videoId;

    /**
     * users_like_videos
     */
    private static final long serialVersionUID = 1L;

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    public UsersLikeVideos(String id, String userId, String videoId) {
        this.id = id;
        this.userId = userId;
        this.videoId = videoId;
    }

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    public UsersLikeVideos() {
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

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId == null ? null : videoId.trim();
    }
}