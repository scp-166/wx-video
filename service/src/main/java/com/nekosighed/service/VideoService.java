package com.nekosighed.service;

import com.nekosighed.common.utils.PagedResult;
import com.nekosighed.pojo.Dto.VideosDto;
import com.nekosighed.pojo.model.Videos;

public interface VideoService {
    /**
     * 保存 videos
     *
     * @param videos
     * @return
     */
    Videos saveVideo(Videos videos);

    /**
     * 更新 video 的 CoverPath 字段
     *
     * @param videoId
     * @param coverPath
     * @return
     */
    int updateVideoForCoverPath(String videoId, String coverPath);

    /**
     * 分页查询 视频信息
     *
     * @param videosDto
     * @param page
     * @param pageSize
     */
    PagedResult getAllVideosByPage(VideosDto videosDto, Integer page, Integer pageSize);
}
