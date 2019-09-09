package com.nekosighed.api.controller;


import com.nekosighed.common.utils.JsonResult;
import com.nekosighed.service.imp.BgmServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "背景音乐相关")
@Validated
@RestController
@RequestMapping("/bgm")
public class BgmController extends BaseController {
    @Resource
    BgmServiceImpl bgmServiceImpl;

    @ApiOperation(value = "获取背景音乐列表")
    @GetMapping("/bgmList")
    public JsonResult getBgmList() {
        return JsonResult.success("获取bgm列表", bgmServiceImpl.queryBgmList());
    }
}
