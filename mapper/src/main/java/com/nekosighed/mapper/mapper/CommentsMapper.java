package com.nekosighed.mapper.mapper;

import com.nekosighed.pojo.model.Comments;

import java.util.List;

public interface CommentsMapper {
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
    int insert(Comments record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    Comments selectByPrimaryKey(String id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    List<Comments> selectAll();

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    int updateByPrimaryKey(Comments record);
}