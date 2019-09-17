package com.nekosighed.service;

import com.nekosighed.pojo.model.Users;

public interface UsersService {

    Users queryUserInfoByUserName(String username);

    Users saveUser(Users users);

    boolean isUserExistByUserName(String username);

    boolean isUserNotExistByUserName(String username);

    Users queryUserInfoByUserId(String userId);

    void upLoadFaceImg(Users users);

    void followMe(String userId, String publisherId);

    void cancelFollowMe(String userId, String publisherId);


}
