package com.nekosighed.api.controller;


import com.nekosighed.common.comonenum.ConstantEnum;
import com.nekosighed.common.comonenum.PrefixEnum;
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
     * 期望分页每页的数量
     */
    final static Integer PAGE_SIZE = 2;


    /**
     * 给指定用户设置 redis session
     *
     * @param usersWithId
     * @return 携带有 uuidToken 的 usersVo
     */
    UsersVo setRedisSessionForUsers(Users usersWithId) {
        String uuid = UuidUtils.createUUID();
        redisUtils.set(PrefixEnum.REDIS_SESSION_PREFIX.getName() + ":" + usersWithId.getId(), uuid, ConstantEnum.REDIS_SESSION_EXPIRE_TIME.getValue());
        UsersVo usersVo = new UsersVo();
        // 复制属性
        BeanUtils.copyProperties(usersWithId, usersVo);
        // 补充属性
        usersVo.setUuidToken(uuid);
        return usersVo;
    }

    /**
     * 根据 userId 删除 redis session
     *
     * @param userId
     */
    void delRedisSessionForUsers(String userId) {
        redisUtils.del(PrefixEnum.REDIS_SESSION_PREFIX.getName() + ":" + userId);
    }
}
