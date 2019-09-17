package com.nekosighed.api.controller;


import com.nekosighed.common.comonenum.BusinessErrorEnum;
import com.nekosighed.common.utils.JsonResult;
import com.nekosighed.pojo.Vo.UsersVo;
import com.nekosighed.pojo.model.Users;
import com.nekosighed.pojo.model.UsersReport;
import com.nekosighed.service.imp.UsersReportServiceImpl;
import com.nekosighed.service.imp.UsersServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    UsersServiceImpl serviceImp;

    @Resource
    UsersReportServiceImpl usersReportService;

    @ApiOperation(value = "用户上传头像接口")
    @PostMapping(value = "/uploadFace", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public JsonResult upload(@Validated @NotNull(message = "用户id不能为空") @RequestParam String userId, MultipartFile file) {

        if (file == null || file.getOriginalFilename() == null || file.getSize() == 0) {
            return JsonResult.detailResponse(BusinessErrorEnum.LACK_FILE);
        }

        String parentDir = "E:/wx_video";
        String secondDir = "/" + userId + "/face/";

        File outPutFile = new File(parentDir + secondDir);
        // 要求是目录
        if (!outPutFile.isDirectory()) {
            outPutFile.mkdirs();
        }

        // 真正的文件
        outPutFile = new File(outPutFile, file.getOriginalFilename());


        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outPutFile));
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

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @ApiImplicitParam(value = "用户id", name = "userId", required = true, dataType = "String", paramType = "query")
    @PostMapping("/userInfo")
    public JsonResult getUserInfo(@NotNull @RequestParam String userId){
        Users users = serviceImp.queryUserInfoByUserId(userId);
        if (users == null){
            return JsonResult.detailResponse(BusinessErrorEnum.USERS_NOT_FOUND);
        }
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(users, usersVo);
        return JsonResult.success("获取成功", usersVo);
    }

    /**
     * 获取发布者信息
     * @param userId
     * @return
     */
    @ApiImplicitParam(value = "发布者id", name = "publisherId", required = true, dataType = "String", paramType = "query")
    @PostMapping("/publisherInfo")
    public JsonResult getUserInfoWithUnAuth(@NotNull @RequestParam String userId){
        Users users = serviceImp.queryUserInfoByUserId(userId);
        if (users == null){
            return JsonResult.detailResponse(BusinessErrorEnum.USERS_NOT_FOUND);
        }
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(users, usersVo);
        return JsonResult.success("获取成功", usersVo);
    }

    /**
     * 关注
     * 保留功能
     *
     * @param userId
     * @param publisherId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户id", name = "userId", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(value = "发布者id", name = "publisherId", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/followMe")
    public JsonResult followMe(@NotNull(message = "userId不能为空") String userId,
                               @NotNull(message = "publisherId不能为空") String publisherId){
        serviceImp.followMe(userId, publisherId);
        return JsonResult.success();
    }

    /**
     * 取消关注
     * 保留功能
     *
     * @param userId
     * @param publisherId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户id", name = "userId", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(value = "发布者id", name = "publisherId", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/cancelFollowMe")
    public JsonResult cancelFollowMe(@NotNull(message = "userId不能为空") String userId,
                                     @NotNull(message = "publisherId不能为空") String publisherId){
        serviceImp.cancelFollowMe(userId, publisherId);
        return JsonResult.success();
    }

    /**
     * 用户举报接口
     *
     * @param usersReport
     * @return
     */
    @ApiOperation(value = "举报接口")
    @PostMapping("/report")
    public JsonResult report(@Validated UsersReport usersReport){
        usersReportService.addSingleReport(usersReport);
        return JsonResult.success();
    }
}
