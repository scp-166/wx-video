package com.nekosighed.mapper.mapper;

import com.nekosighed.pojo.model.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersMapper {
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
    int insert(Users record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    Users selectByPrimaryKey(String id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    List<Users> selectAll();

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    int updateByPrimaryKey(Users record);

    /**
     * 通过 username 查询出一个 Users 记录
     * @param username
     * @return
     */
    Users selectByUsername(@Param("username") String username);

    /**
     * 由于视频被点赞而增加一次个人受点赞数
     *
     * @param userId
     * @return
     */
    int incReceiveLikeCount(@Param("userId") String userId);

    /**
     * 由于视频被取消点赞而减少一次个人受点赞数
     *
     * @param userId
     * @return
     */
    int decReceiveLikeCount(@Param("userId") String userId);
}