package com.nekosighed.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nekosighed.common.utils.PagedResult;
import com.nekosighed.common.utils.UuidUtils;
import com.nekosighed.mapper.mapper.UsersLikeVideosMapper;
import com.nekosighed.mapper.mapper.UsersMapper;
import com.nekosighed.mapper.mapper.VideosMapper;
import com.nekosighed.mapper.mapper.vo.VideosVoMapper;
import com.nekosighed.pojo.Dto.VideosDto;
import com.nekosighed.pojo.Vo.VideosVo;
import com.nekosighed.pojo.model.UsersLikeVideos;
import com.nekosighed.pojo.model.Videos;
import com.nekosighed.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Resource
    private VideosMapper videosMapper;

    @Resource
    private VideosVoMapper videosVoMapper;

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private UsersLikeVideosMapper usersLikeVideosMapper;

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

    /**
     * 分页查询 视频相关信息
     *
     * @param videosDto
     * @param page
     * @param pageSize
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult getAllVideosByPage(VideosDto videosDto, Integer page, Integer pageSize) {
        String desc = "";
        if (videosDto != null){
            desc = videosDto.getVideoDesc();
        }

        // 开始分页
        PageHelper.startPage(page, pageSize);
        List<VideosVo> videosVoList = videosVoMapper.queryAllVideo(desc);
        // 切割分页内容到 PageInfo 中
        PageInfo<VideosVo> pageInfo = new PageInfo<>(videosVoList);
        // 包装内容
        PagedResult pagedResult = new PagedResult();

        pagedResult.setExpectPageSize(pageSize);
        pagedResult.setActualPageSize(pageInfo.getSize());
        pagedResult.setCurrentPage(pageInfo.getPageNum());
        pagedResult.setTotalPages(pageInfo.getPages());
        pagedResult.setTotalRecord(pageInfo.getTotal());
        pagedResult.setRows(videosVoList);

        return pagedResult;
    }

    /**
     * 视频被点赞
     *
     * @param userId
     * @param videoId
     * @param videoAuthorId
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void videoHaveBeenLike(String userId, String videoId, String videoAuthorId) {
        // 添加一次关联表记录，其中键都被设置了索引，避免重复重复添加
        usersLikeVideosMapper.insert(new UsersLikeVideos(UuidUtils.createUUID(), userId, videoId));
        // 视频作者增加获得点赞数
        usersMapper.incReceiveLikeCount(videoAuthorId);
        // 视频增加获得点赞数
        videosMapper.incLikeCount(videoId);
    }

    /**
     * 视频被取消点赞
     *
     * @param userId
     * @param videoId
     * @param videoAuthorId
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void videoHaveBennUnlike(String userId, String videoId, String videoAuthorId) {
        // 删除关联表记录
        usersLikeVideosMapper.deleteByUserIdVideoId(userId, videoId);
        // 视频作品增加获得点赞数
        usersMapper.decReceiveLikeCount(videoAuthorId);
        // 视频减少获得点赞数
        videosMapper.decLikeCount(videoId);

    }
}
