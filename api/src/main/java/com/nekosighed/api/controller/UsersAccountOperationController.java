package com.nekosighed.api.controller;

import com.nekosighed.common.comonenum.BusinessErrorEnum;
import com.nekosighed.common.utils.JsonResult;
import com.nekosighed.common.utils.MD5Utils;
import com.nekosighed.pojo.Vo.UsersVo;
import com.nekosighed.pojo.model.Users;
import com.nekosighed.service.imp.UsersServiceImp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Api(tags = "小程序", description = "总体接口")
@Validated
@RestController
public class UsersAccountOperationController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UsersAccountOperationController.class);

    @Resource
    private UsersServiceImp serviceImp;

    @ApiOperation(value = "注册接口", tags = "小程序交互")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult register(@Validated @RequestBody Users users) throws Exception {
        // 1. 判断不为空，利用 Validation
        // 2. 用户是否存在
        if (serviceImp.queryUserInfoByUserName(users.getUsername())) {
            return JsonResult.detailResponse(BusinessErrorEnum.USERS_ALREADY_EXIST);
        }
        // 3. 进行注册
        users.setNickname(users.getUsername());
        // md5 加密
        users.setPassword(MD5Utils.getMD5Str(users.getPassword()));
        users.setFansCounts(0);
        users.setFollowCounts(0);
        users.setReceiveLikeCounts(0);

        users = serviceImp.saveUser(users);

        UsersVo usersVo = setRedisSessionForUsers(users);

        // 清空密码
        usersVo.setPassword("");
        // 返回注册信息
        return JsonResult.success("注册成功", users);
    }

    @ApiOperation(value = "登录接口", tags = "小程序交互")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult login(@Validated @RequestBody Users users) throws Exception {
        // 1. 用户是否存在
        if (serviceImp.isUserNotExistByUserName(users.getUsername())) {
            return JsonResult.detailResponse(BusinessErrorEnum.USERS_NOT_FOUND);
        }
        // 2. 校验密码
        Users targetUser = serviceImp.getDetailUserInfoByUserName(users.getUsername());
        if (Optional.ofNullable(targetUser.getPassword()).orElse("").equals(MD5Utils.getMD5Str(users.getPassword()))) {
            // 设置 redis session
            UsersVo usersVo = setRedisSessionForUsers(targetUser);
            // 返回用户信息
            usersVo.setPassword("");
            return JsonResult.success("登录成功", usersVo);
        } else {
            return JsonResult.detailResponse(BusinessErrorEnum.PASSWORD_ERROR);
        }
    }

    @ApiOperation(value = "注销接口", tags = "小程序交互")
    @ApiImplicitParam(value = "用户id", name = "userId", required = true, paramType = "query", dataType = "String")
    @PostMapping(value = "/logout")
    public JsonResult logout(@Validated @NotNull(message = "用户id不能为空") @RequestParam String userId){
        // 删除 redis session
        delRedisSessionForUsers(userId);
        return JsonResult.success("注销成功");
    }
}
