package com.nekosighed.service;

import java.util.List;

public interface UsersLikeVideosService {
    /**
     * 查询相关记录
     *
     * @param userId
     * @param videoId
     * @return
     */
    boolean isUserLikeVideo(String userId, String videoId);
}
