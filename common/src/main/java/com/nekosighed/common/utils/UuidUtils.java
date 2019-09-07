package com.nekosighed.common.utils;

import java.util.UUID;

public class UuidUtils {
    /**
     * 生成 uuid
     * @return
     */
    public static String createUUID(){
        return UUID.randomUUID().toString();
    }
}
