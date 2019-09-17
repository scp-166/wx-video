package com.nekosighed.service.imp;

import com.nekosighed.common.utils.UuidUtils;
import com.nekosighed.mapper.mapper.UsersFansMapper;
import com.nekosighed.mapper.mapper.UsersMapper;
import com.nekosighed.pojo.model.Users;
import com.nekosighed.pojo.model.UsersFans;
import com.nekosighed.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersFansMapper usersFansMapper;

    /**
     * 根据 username 查找 users信息
     *
     * @param username
     * @return 查到为 true
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfoByUserName(String username) {
        return usersMapper.selectByUsername(username);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users saveUser(Users users) {
        users.setId(UuidUtils.createUUID());
        if (usersMapper.insert(users) == 1) {
            return users;
        } else {
            logger.warn("用户保存失败: {}", users);
            return null;
        }
    }

    /**
     * 通过 username 判断用户是否存在
     *
     * @param username
     * @return 存在为 true
     */
    @Override
    public boolean isUserExistByUserName(String username) {
        return queryUserInfoByUserName(username) != null;
    }

    /**
     * 通过 username 判断用户是否不存在
     *
     * @param username
     * @return
     */
    @Override
    public boolean isUserNotExistByUserName(String username) {
        return queryUserInfoByUserName(username) == null;
    }

    /**
     * 根据 用户 id 查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public Users queryUserInfoByUserId(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

    /**
     * 更新 头像信息
     *
     * @param users
     */
    @Override
    public void upLoadFaceImg(Users users) {
        Optional optional = Optional.ofNullable(queryUserInfoByUserId(users.getId()));
        if (optional.isPresent()) {
            Users sourceUser = (Users) (optional.get());
            // 保存 头像
            sourceUser.setFaceImage(users.getFaceImage());
            usersMapper.updateByPrimaryKey(sourceUser);
        }
    }

    /**
     * 关注
     *
     * @param userId
     * @param publisherId
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void followMe(String userId, String publisherId) {
        // 添加一条关联表记录
        usersFansMapper.insert(new UsersFans(UuidUtils.createUUID(), publisherId, userId));
        // 添加发布者粉丝数量
        usersMapper.incFansCount(publisherId);
        // 添加关注者关注数量
        usersMapper.incFollowCount(userId);
    }

    /**
     * 取消关注
     *
     * @param userId
     * @param publisherId
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void cancelFollowMe(String userId, String publisherId) {
        // 删除关联表记录
        usersFansMapper.deleteByPublisherIdFanId(publisherId, userId);
        // 减少发布者粉丝数量
        usersMapper.decFansCount(publisherId);
        // 减少关注者关注数量
        usersMapper.decFollowCount(userId);
    }
}
