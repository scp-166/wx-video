package com.nekosighed.common.comonenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {
    /**
     * 错误枚举
     */
    UNKNOWN_ERROR(1, "UNKNOWN_ERROR", "未知错误"),
    HTTP_MEDIA_TYPE_NOT_SUPPORT(2, "HTTP_MEDIA_TYPE_NOT_SUPPORT", "访问类型不支持"),
    JSON_PARSING_ERROR(3, "JSON_PARSING_ERROR", "json解析错误"),
    OTHER_PARSING_ERROR(3, "OTHER_PARSING_ERROR", "其他解析错误");
    private int num;
    private String code;
    private String msg;

}
