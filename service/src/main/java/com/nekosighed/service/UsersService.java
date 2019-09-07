package com.nekosighed.service;

import com.nekosighed.pojo.model.Users;

import org.springframework.stereotype.Service;

public interface UsersService {

    boolean queryUserInfoByUserName(String username);

    Users saveUser(Users users);


}
