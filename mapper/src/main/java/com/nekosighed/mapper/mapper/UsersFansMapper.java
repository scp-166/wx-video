package com.nekosighed.mapper.mapper;

import com.nekosighed.pojo.model.UsersFans;

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
}