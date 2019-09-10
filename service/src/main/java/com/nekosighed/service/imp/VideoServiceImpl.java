package com.nekosighed.service.imp;

import com.nekosighed.common.utils.UuidUtils;
import com.nekosighed.mapper.mapper.VideosMapper;
import com.nekosighed.pojo.model.Videos;
import com.nekosighed.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class VideoServiceImpl implements VideoService {
    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Resource
    private VideosMapper videosMapper;

    /**
     * 保存 video 信息
     * @param videos
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Videos saveVideo(Videos videos) {
        if (videos.getId() == null){
            videos.setId(UuidUtils.createUUID());
        }
        if(videosMapper.insert(videos) == 1){
            return videos;
        } else {
            logger.warn("视频保存失败: {}", videos);
            return null;
        }
    }

    /**
     * 根据视频id 更新 封面图
     * @param videoId
     * @param coverPath
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int updateVideoForCoverPath(String videoId, String coverPath) {
        return videosMapper.updateForCover(videoId, coverPath);
    }
}
