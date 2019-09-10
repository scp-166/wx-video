package com.nekosighed.api.controller;

import com.nekosighed.common.comonenum.BusinessErrorEnum;
import com.nekosighed.common.comonenum.status.VideoStatusEnum;
import com.nekosighed.common.utils.FFMPEGUtils;
import com.nekosighed.common.utils.JsonResult;
import com.nekosighed.common.utils.UuidUtils;
import com.nekosighed.pojo.Dto.UploadVideoInfoDto;
import com.nekosighed.pojo.model.Bgm;
import com.nekosighed.pojo.model.Videos;
import com.nekosighed.service.imp.BgmServiceImpl;
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

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.Date;
import java.util.Optional;

@Api(tags = "视频操作接口")
@Validated
@RestController
@RequestMapping("/video")
public class VideoController {
    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);

    @Resource
    private BgmServiceImpl bgmService;

    @Resource
    private VideoServiceImpl videoService;

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
        String targetVideo = parentDir + secondVideoDir + file.getOriginalFilename();

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
                targetVideo = parentDir + secondProcessVideoDir + "/" + UuidUtils.createUUID() + ".mp4";
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

        // 保存信息
        Videos video = new Videos();
        // 拷贝字段
        BeanUtils.copyProperties(uploadVideoInfoDto, video);

        video.setAudioId(uploadVideoInfoDto.getAudioId());
        video.setStatus(VideoStatusEnum.PUBLISH_SUCCESS.getValue());
        video.setVideoPath(secondProcessVideoDir + file.getOriginalFilename());
        video.setCreateTime(new Date());

        if (Optional.ofNullable(videoService.saveVideo(video)).isPresent()) {
            // 返回id，便于上传视频 封面
            return JsonResult.success("上传视频成功", video.getId());
        } else {
            return JsonResult.detailResponse(BusinessErrorEnum.VIDEO_SAVE_ERROR);
        }
    }

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
}