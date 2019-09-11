package com.nekosighed.mapper.mapper;

import com.nekosighed.pojo.Vo.VideosVo;
import com.nekosighed.pojo.model.Videos;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosMapper {
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
    int insert(Videos record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    Videos selectByPrimaryKey(String id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    List<Videos> selectAll();

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-07 13:23:25
     */
    int updateByPrimaryKey(Videos record);

    /**
     * 更新 视频封面
     * @param videoId
     * @param coverPath
     * @return
     */
    int updateForCover(@Param("videoId") String videoId, @Param("coverPath") String coverPath);

}