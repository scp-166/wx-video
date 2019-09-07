package com.nekosighed.api.controller;


import com.nekosighed.common.comonenum.ConstantEnum;
import com.nekosighed.common.utils.RedisUtils;
import com.nekosighed.common.utils.UuidUtils;
import com.nekosighed.pojo.Vo.UsersVo;
import com.nekosighed.pojo.model.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BaseController {
    @Resource
    RedisUtils redisUtils;

    /**
     * redis session 名 前缀
     */
    final static String REDIS_SESSION_PREFIX = "redis-session-prefix";

    /**
     * 给指定用户设置 redis session
     *
     * @param usersWithId
     * @return 携带有 uuidToken 的 usersVo
     */
    UsersVo setRedisSessionForUsers(Users usersWithId) {
        String uuid = UuidUtils.createUUID();
        redisUtils.set(REDIS_SESSION_PREFIX + ":" + usersWithId.getId(), uuid, ConstantEnum.REDIS_SESSION_EXPIRE_TIME.getValue());
        UsersVo usersVo = new UsersVo();
        // 复制属性
        BeanUtils.copyProperties(usersWithId, usersVo);
        // 补充属性
        usersVo.setUuidToken(uuid);
        return usersVo;
    }
}