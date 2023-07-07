package com.dhee.service;

import com.dhee.dao.UserDao;
import com.dhee.entity.UserEntity;

import java.util.ArrayList;

public class UserService {


    private UserDao userDao = new UserDao();

    public UserEntity login(UserEntity userEntity) {
        ArrayList<UserEntity> userList = userDao.select();
        for (UserEntity user : userList) {
            if(user.getUserName().equals(userEntity.getUserName())&&user.getPassword().equals(userEntity.getPassword())){
                return user;
            }
        }
        return null;
    }
}
