package com.nekosighed.common.comonenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PrefixEnum {
    /**
     * 前缀命名枚举
     */
    REDIS_SESSION_PREFIX("REDIS_SESSION_PREFIX", "redis中为用户存储session的前缀")
    ;
    private String name;
    private String desc;
}
