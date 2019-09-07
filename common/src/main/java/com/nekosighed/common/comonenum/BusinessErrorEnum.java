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
    USERS_ALREADY_EXIST(20002, "USERS_ALREADY_EXIST", "用户已存在")
    ;
    private int num;
    private String code;
    private String msg;

}
