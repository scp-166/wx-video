package com.nekosighed.api.controller;

import com.nekosighed.common.comonenum.BusinessErrorEnum;
import com.nekosighed.common.utils.JsonResult;
import com.nekosighed.pojo.Dto.UploadVideoInfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Api(tags = "视频操作接口")
@Validated
@RestController
@RequestMapping("/video")
public class VideoController {
    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);

    @ApiOperation(value = "上传视频接口")
    @PostMapping(value = "/uploadVideo")
    public JsonResult uploadVideo(@Validated  UploadVideoInfoDto uploadVideoInfoDto, @RequestParam(required = false) MultipartFile file){

        if (file == null || file.getOriginalFilename() == null || file.getSize() == 0){
            return JsonResult.detailResponse(BusinessErrorEnum.LACK_FILE);
        }

        String parentDir = "E:/wx_video";
        String secondDir = "/" + uploadVideoInfoDto.getUserId() + "/video";

        File outPutFile = new File(parentDir + secondDir);

        // 要求是目录
        if (!outPutFile.isDirectory()){
            outPutFile.mkdirs();
        }

        // 真正的文件

        outPutFile = new File(outPutFile, file.getOriginalFilename());

        try(BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream());
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outPutFile))
        ){
            IOUtils.copy(inputStream, outputStream);

            return JsonResult.success("上传视频成功");
        } catch (IOException e){
            return JsonResult.detailResponse(BusinessErrorEnum.FILE_OPERATION_ERROR);
        }
    }
}
