package com.nekosighed.service.imp;

import com.nekosighed.mapper.mapper.UsersMapper;
import com.nekosighed.pojo.model.Users;
import com.nekosighed.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImp implements UsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public boolean queryUserInfoByUserName(String username) {
        Users users = new Users();
        users.setUsername(username);
        int result = usersMapper.insert(users);
        return result != 0;
    }

    @Override
    public void saveUser(Users users) {

    }
}
