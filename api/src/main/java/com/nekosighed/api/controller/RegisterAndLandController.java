package com.nekosighed.api.controller;

import com.nekosighed.common.utils.JsonResult;
import com.nekosighed.pojo.model.Users;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
public class RegisterAndLandController {

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult register(@Validated  @RequestBody Users users){
        // 1. 判断不为空，利用 Validation
        // 2. 用户是否存在
        // 3. 进行注册
        System.out.println(users);
        return JsonResult.success();

    }
}