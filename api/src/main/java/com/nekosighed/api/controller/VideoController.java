package com.nekosighed.api.controller;

import com.nekosighed.common.comonenum.BusinessErrorEnum;
import com.nekosighed.common.comonenum.status.VideoStatusEnum;
import com.nekosighed.common.utils.FFMPEGUtils;
import com.nekosighed.common.utils.JsonResult;
import com.nekosighed.common.utils.PagedResult;
import com.nekosighed.common.utils.UuidUtils;
import com.nekosighed.pojo.Dto.UploadVideoInfoDto;
import com.nekosighed.pojo.Dto.VideosDto;
import com.nekosighed.pojo.model.Bgm;
import com.nekosighed.pojo.model.Videos;
import com.nekosighed.service.imp.BgmServiceImpl;
import com.nekosighed.service.imp.SearchRecordsServiceImpl;
import com.nekosighed.service.imp.UsersLikeVideosServiceImpl;
import com.nekosighed.service.imp.VideoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.Date;
import java.util.Optional;

@Api(tags = "视频操作接口")
@Validated
@RestController
@RequestMapping("/video")
public class VideoController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);

    @Resource
    private BgmServiceImpl bgmService;

    @Resource
    private VideoServiceImpl videoService;

    @Resource
    private SearchRecordsServiceImpl searchRecordsService;

    @Resource
    private UsersLikeVideosServiceImpl usersLikeVideosService;

    @ApiOperation(value = "上传视频接口")
    @PostMapping(value = "/uploadVideo")
    public JsonResult uploadVideo(@Validated UploadVideoInfoDto uploadVideoInfoDto, @RequestParam(required = false) MultipartFile file) {

        if (file == null || file.getOriginalFilename() == null || file.getSize() == 0) {
            return JsonResult.detailResponse(BusinessErrorEnum.LACK_FILE);
        }

        String parentDir = "E:/wx_video";
        String secondVideoDir = "/" + uploadVideoInfoDto.getUserId() + "/video";
        String secondProcessVideoDir = "/" + uploadVideoInfoDto.getUserId() + "/processVideo";

        File outPutFile = new File(parentDir + secondVideoDir);

        // 要求是目录
        if (!outPutFile.isDirectory()) {
            outPutFile.mkdirs();
        }

        // 真正的文件
        outPutFile = new File(outPutFile, file.getOriginalFilename());

        try (BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream());
             BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outPutFile))
        ) {
            // 保存视频
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            return JsonResult.detailResponse(BusinessErrorEnum.FILE_OPERATION_ERROR);
        }

        // 最后要保存的文件名
        String targetVideo = parentDir + secondVideoDir + "/" + file.getOriginalFilename();
        String saveVideo = secondVideoDir + "/" + file.getOriginalFilename();

        // 不放在上面 IOUtils 中是因为避免 File 使用了未关闭流的文件，导致出错
        //       ffmpeag 出错了很多次
        // 如果存在bgm，需要进行视频、音频合并处理
        if (!StringUtils.isEmpty(uploadVideoInfoDto.getAudioId())) {
            Optional bgmOpt = Optional.ofNullable(bgmService.queryBgmById(uploadVideoInfoDto.getAudioId()));
            if (bgmOpt.isPresent()) {
                // 获取 bgm 的文件地址
                String audioPath = ((Bgm) bgmOpt.get()).getPath();
                // 补全文件地址
                audioPath = parentDir + audioPath;
                // 修改 保存的文件名
                targetVideo = parentDir + secondProcessVideoDir + "/" + file.getOriginalFilename();
                saveVideo = secondProcessVideoDir + "/" + file.getOriginalFilename();
                // 合并视频和音频
                FFMPEGUtils.mergerVideoAndAudio(
                        outPutFile.getAbsolutePath(),
                        audioPath,
                        uploadVideoInfoDto.getVideoSeconds(),
                        targetVideo
                );
                logger.info("合并成功");
            }
        }

        // 使用 ffmpeg 对视频进行截图
        // 复用指定 截图名称
        String targetPic = file.getOriginalFilename();
        int index = targetPic.lastIndexOf(".");
        String targetPrefix = targetPic.substring(0, index);
        String picSuffix = targetPic.substring(index + 1);
        targetPic = parentDir + secondVideoDir + "/" + targetPrefix + ".jpeg";

        if (!FFMPEGUtils.screenshots(targetVideo, targetPic)) {
            return JsonResult.detailResponse(BusinessErrorEnum.FILE_OPERATION_ERROR);
        }

        // 保存信息
        Videos video = new Videos();
        // 拷贝字段
        BeanUtils.copyProperties(uploadVideoInfoDto, video);

        video.setAudioId(uploadVideoInfoDto.getAudioId());
        video.setStatus(VideoStatusEnum.PUBLISH_SUCCESS.getValue());
        video.setVideoPath(saveVideo);
        video.setCoverPath(secondVideoDir + "/" + targetPrefix + ".jpeg");
        video.setCreateTime(new Date());

        if (Optional.ofNullable(videoService.saveVideo(video)).isPresent()) {
            // 返回id，便于上传视频 封面
            return JsonResult.success("上传视频成功", video.getId());
        } else {
            return JsonResult.detailResponse(BusinessErrorEnum.VIDEO_SAVE_ERROR);
        }
    }

    /**
     * 上传视频截图 接口
     * 由于小程序端调用 wx.chooseVideo 在手机端无法获取截图，目前弃用
     *
     * @param userId
     * @param videoId
     * @param file
     * @return
     */
    @ApiIgnore
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户id", name = "userId", dataType = "String", paramType = "form", required = true),
            @ApiImplicitParam(value = "视频id", name = "videoId", dataType = "String", paramType = "form", required = true)
    })

    @PostMapping("/uploadVideoCover")
    public JsonResult upLoadVideoCover(@NotNull(message = "用户id不能为空") @RequestParam String userId,
                                       @NotNull(message = "视频id不能为空") @RequestParam String videoId,
                                       MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null || file.getSize() == 0) {
            return JsonResult.detailResponse(BusinessErrorEnum.LACK_FILE);
        }

        String parentDir = "E:/wx_video";
        String secondCoverDir = "/" + userId + "/video";

        File outPutFile = new File(parentDir + secondCoverDir);

        // 要求是目录
        if (!outPutFile.isDirectory()) {
            outPutFile.mkdirs();
        }

        // 真正要保存的文件
        outPutFile = new File(outPutFile, file.getOriginalFilename());

        try (BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream());
             BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outPutFile));
        ) {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            return JsonResult.detailResponse(BusinessErrorEnum.FILE_OPERATION_ERROR);
        }

        // 更新视频缩略图
        if (videoService.updateVideoForCoverPath(videoId, secondCoverDir + file.getOriginalFilename()) == 1) {
            return JsonResult.success("上传缩略图成功");
        } else {
            return JsonResult.detailResponse(BusinessErrorEnum.VIDEO_COVER_SAVE_ERROR);
        }
    }

    @ApiOperation(value = "首页分页查询视频")
    @ApiImplicitParam(value = "期望查询页数", name = "pageNum", dataType = "Integer", paramType = "query")
    @PostMapping("/showVideo")
    public JsonResult showAllVideoByPage(@RequestParam(required = false) Integer pageNum) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        PagedResult result = videoService.getAllVideosByPage(null, pageNum, PAGE_SIZE);

        return JsonResult.success("成功获取分页数据", result);
    }

    @ApiOperation(value = "查询搜索视频")
    @ApiImplicitParam(value = "期望查询页数", name = "pageNum", dataType = "Integer", paramType = "form")
    @PostMapping("/showVideoByHotTips")
    public JsonResult showAllVideoByPageAndHotTips(@RequestBody VideosDto videosDto, @RequestParam(required = false) Integer pageNum) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        // 添加搜索记录
        searchRecordsService.addHotWord(videosDto.getVideoDesc());

        PagedResult result = videoService.getAllVideosByPage(videosDto, pageNum, PAGE_SIZE);
        return JsonResult.success("成功搜索内容", result);
    }

    @ApiOperation(value = "获得热词列表")
    @GetMapping("/hotWords")
    public JsonResult showHotWord() {
        return JsonResult.success("获取热词成功", searchRecordsService.getHotWords());
    }

    @ApiOperation(value = "视频点赞")
    @PostMapping("/likeVideo")
    public JsonResult likeVideo(@NotNull(message = "userId不能为空") String userId,
                                @NotNull(message = "videoId不能为空") String videoId,
                                @NotNull(message = "videoAuthorId不能为空") String videoAuthorId){
        videoService.videoHaveBeenLike(userId, videoId, videoAuthorId);
        return JsonResult.success();
    }

    @ApiOperation(value = "视频取消点赞")
    @PostMapping("/unlikeVideo")
    public JsonResult UnlikeVideo(@NotNull(message = "userId不能为空") String userId,
                                  @NotNull(message = "videoId不能为空") String videoId,
                                  @NotNull(message = "videoAuthorId不能为空") String videoAuthorId){
        videoService.videoHaveBennUnlike(userId, videoId, videoAuthorId);
        return JsonResult.success();
    }

    @ApiOperation(value = "判断用户是否喜欢该视频")
    @PostMapping("/isLikeVideo")
    public JsonResult isLike(@NotNull(message = "userId不能为空") String userId,
                             @NotNull(message = "videoId不能为空") String videoId){
        return JsonResult.success(usersLikeVideosService.isUserLikeVideo(userId, videoId)?"true":"no");
    }
}
