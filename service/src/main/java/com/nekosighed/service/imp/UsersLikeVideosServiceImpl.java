package com.nekosighed.service.imp;

import com.nekosighed.mapper.mapper.UsersLikeVideosMapper;
import com.nekosighed.pojo.model.UsersLikeVideos;
import com.nekosighed.service.UsersLikeVideosService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UsersLikeVideosServiceImpl implements UsersLikeVideosService {
    @Resource
    private UsersLikeVideosMapper usersLikeVideosMapper;

    /**
     * 查询用户是否和视频有关联
     *
     * @param userId
     * @param videoId
     * @return
     */
    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {
        List<UsersLikeVideos> list = usersLikeVideosMapper.queryUserLikeVideos(userId, videoId);
        return list != null && list.size() > 0;
    }
}
