package com.nekosighed.common.comonenum.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  VideoStatusEnum {
    /**
     * 视频状态 枚举
     */
    AUDIT_IN_PROGRESS(0, "审核中"),
    AUDIT_FAILURE(1, "审核失败"),
    PUBLISH_SUCCESS(2, "发布成功"),
    Prohibited(3, "禁用")
    ;
    private  int value;
    private  String msg;
}
