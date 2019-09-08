package com.nekosighed.api.controller;


import com.nekosighed.common.comonenum.BusinessErrorEnum;
import com.nekosighed.common.utils.JsonResult;
import com.nekosighed.pojo.model.Users;
import com.nekosighed.service.imp.UsersServiceImp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.*;

@Api(tags = "小程序用户操作", description = "用户操作接口")
@RestController
@RequestMapping("/operation")
public class UsersOperationController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UsersOperationController.class);

    @Resource
    UsersServiceImp serviceImp;

    @ApiOperation(value = "用户上传头像接口")
    @PostMapping(value = "/uploadFace", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public JsonResult upload(@Validated @NotNull(message = "用户id不能为空") @RequestParam String userId, MultipartFile file) {

        if (file == null || file.getOriginalFilename() == null || file.getSize() == 0) {
            return JsonResult.detailResponse(BusinessErrorEnum.LACK_FILE);
        }

        String parentDir = "E:/wx_video";
        String secondDir = "/" + userId + "/face/";

        File OutPutFile = new File(parentDir + secondDir);
        // 要求是目录
        if (!OutPutFile.isDirectory()) {
            OutPutFile.mkdirs();
        }

        // 真正的文件
        OutPutFile = new File(OutPutFile, file.getOriginalFilename());


        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(OutPutFile));
             BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream())) {
            // 拷贝文件
            IOUtils.copy(inputStream, outputStream);

            // 保存头像
            Users user = new Users();
            user.setId(userId);
            user.setFaceImage(secondDir + file.getOriginalFilename());
            serviceImp.upLoadFaceImg(user);

            // 返回 头像地址
            return JsonResult.success("上传成功", user.getFaceImage());
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.detailResponse(BusinessErrorEnum.FILE_OPERATION_ERROR);
        }

    }
}
