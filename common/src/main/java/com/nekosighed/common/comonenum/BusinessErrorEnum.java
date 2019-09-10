package com.nekosighed.common.comonenum;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessErrorEnum {
    /**
     * 业务错误枚举
     */
    INTERNAL_BUSINESS_ERROR(10001, "INTERNAL_BUSINESS_ERROR", "内部业务错误"),
    USERS_NOT_FOUND(20001, "USERS_NOT_FOUND", "用户不存在"),
    USERS_ALREADY_EXIST(20002, "USERS_ALREADY_EXIST", "用户已存在"),
    PASSWORD_ERROR(30001, "PASSWORD_ERROR", "密码错误"),
    LACK_FILE(40001, "LACK_FILE", "缺少文件"),
    FILE_OPERATION_ERROR(40002, "FILE_OPERATION_ERROR", "文件操作失败"),
    VIDEO_SAVE_ERROR(50001, "VIDEO_SAVE_ERROR", "视频保存失败"),
    VIDEO_COVER_SAVE_ERROR(50002, "VIDEO_COVER_SAVE_ERROR", "文件缩略图保存失败")
    ;
    private int num;
    private String code;
    private String msg;

}
