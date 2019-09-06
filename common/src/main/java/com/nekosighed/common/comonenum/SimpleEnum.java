package com.nekosighed.common.comonenum;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SimpleEnum {
    /**
     * 常规值的枚举
     */
    RESPONSE_SUCCESS(200, "Response success", "响应成功"),
    RESPONSE_ERROR(500, "Response error", "响应失败");

    private int num;
    private String code;
    private String msg;

}
