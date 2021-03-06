package com.nekosighed.mapper.mapper;

import com.nekosighed.pojo.model.UsersLikeVideos;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersLikeVideosMapper {
    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    int deleteByPrimaryKey(String id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    int insert(UsersLikeVideos record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    UsersLikeVideos selectByPrimaryKey(String id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    List<UsersLikeVideos> selectAll();

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    int updateByPrimaryKey(UsersLikeVideos record);

    /**
     * 通过 userId 和 videoId 删除记录
     *
     * @param userId
     * @param videoId
     * @return
     */
    int deleteByUserIdVideoId(@Param("userId") String userId, @Param("videoId") String videoId);

    /**
     * 通过 userId 和 videoId 查询记录
     *
     * @param userId
     * @param videoId
     * @return
     */
    List<UsersLikeVideos> queryUserLikeVideos(@Param("userId") String userId, @Param("videoId") String videoId);
}