package com.nekosighed.common.comonenum;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConstantEnum {
    /**
     * 常量 枚举
     */
    REDIS_SESSION_EXPIRE_TIME(60, "REDIS_SESSION_EXPIRE", "redis session 过期时间")
    ;
    private int value;
    private String code;
    private String msg;

}
