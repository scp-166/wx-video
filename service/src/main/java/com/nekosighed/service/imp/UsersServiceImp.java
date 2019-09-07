package com.nekosighed.service.imp;

import com.nekosighed.common.utils.UuidUtils;
import com.nekosighed.mapper.mapper.UsersMapper;
import com.nekosighed.pojo.model.Users;
import com.nekosighed.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UsersServiceImp implements UsersService {
    @Autowired
    private UsersMapper usersMapper;

    /**
     * 根据 username 查找 users信息
     * @param username
     * @return 查到为 true
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserInfoByUserName(String username) {
        Users result = usersMapper.selectByUsername(username);
        return result != null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users saveUser(Users users) {
        users.setId(UuidUtils.createUUID());
        usersMapper.insert(users);
        return users;
    }

    /**
     * 通过 username 判断用户是否存在
     *
     * @param username
     * @return 存在为 true
     */
    public boolean isUserExistByUserName(String username){
        return queryUserInfoByUserName(username);
    }

    /**
     * 通过 username 判断用户是否不存在
     * @param username
     * @return
     */
    public boolean isUserNotExistByUserName(String username){
        return !queryUserInfoByUserName(username);
    }

    /**
     * 通过 username 获得用户信息
     *
     * @param username
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users getDetailUserInfoByUserName(String username){
        return usersMapper.selectByUsername(username);
    }
}
