package com.origincat.bookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.origincat.bookstore.dao.UserDao;
import com.origincat.bookstore.pojo.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDaoTest{
    @Autowired
    private UserDao userDao;

    @Test
    public void selectUser(){
        User user = new User();
        user.setUserMail("jinxinsong@outlook.com");
        User user1 = userDao.selectUser(user.getUserMail());
        assertEquals(user1.getUserPasswd(), "732573");
    }

}