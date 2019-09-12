package com.nekosighed.mapper.mapper.vo;

import com.nekosighed.pojo.Vo.VideosVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosVoMapper {

    List<VideosVo> queryAllVideo(@Param("videoDesc") String videoDesc);
}