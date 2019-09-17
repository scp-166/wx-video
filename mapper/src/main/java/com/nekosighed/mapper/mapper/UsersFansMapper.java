package com.nekosighed.mapper.mapper;

import com.nekosighed.pojo.model.UsersFans;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersFansMapper {
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
    int insert(UsersFans record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    UsersFans selectByPrimaryKey(String id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    List<UsersFans> selectAll();

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    int updateByPrimaryKey(UsersFans record);

    /**
     * 通过 publisherId 和 userId 查询记录
     * @param publisherId
     * @param userId
     */
    void deleteByPublisherIdFanId(@Param("publisherId") String publisherId, @Param("userId") String userId);
}