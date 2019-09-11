package com.nekosighed.pojo.Vo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
@ToString
@Getter
@Setter
public class VideosVo implements Serializable {
    /**
     * 
     */
    private String id;

    /**
     * 发布者id
     */
    private String userId;

    /**
     * 用户使用音频的信息
     */
    private String audioId;

    /**
     * 视频描述
     */
    private String videoDesc;

    /**
     * 视频存放的路径
     */
    private String videoPath;

    /**
     * 视频秒数
     */
    private Float videoSeconds;

    /**
     * 视频宽度
     */
    private Integer videoWidth;

    /**
     * 视频高度
     */
    private Integer videoHeight;

    /**
     * 视频封面图
     */
    private String coverPath;

    /**
     * 喜欢/赞美的数量
     */
    private Long likeCounts;

    /**
     * 视频状态：
     1、发布成功
     2、禁止播放，管理员操作
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像
     */
    private String faceImage;

    /**
     * videos
     */
    private static final long serialVersionUID = 1L;

    public VideosVo() {
    }

    public VideosVo(String id, String userId, String audioId, String videoDesc, String videoPath, Float videoSeconds, Integer videoWidth, Integer videoHeight, String coverPath, Long likeCounts, Integer status, Date createTime, String username, String faceImage) {
        this.id = id;
        this.userId = userId;
        this.audioId = audioId;
        this.videoDesc = videoDesc;
        this.videoPath = videoPath;
        this.videoSeconds = videoSeconds;
        this.videoWidth = videoWidth;
        this.videoHeight = videoHeight;
        this.coverPath = coverPath;
        this.likeCounts = likeCounts;
        this.status = status;
        this.createTime = createTime;
        this.username = username;
        this.faceImage = faceImage;
    }
}