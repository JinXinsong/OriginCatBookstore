package com.origincat.bookstore.servlet.impl;

// import java.util.Date;

import com.origincat.bookstore.dao.UserDao;
import com.origincat.bookstore.enums.SignupEnum;
import com.origincat.bookstore.exceptions.LoginOperationException;
import com.origincat.bookstore.pojo.User;
import com.origincat.bookstore.servlet.SignupServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupServiceImpl implements SignupServlet {
    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public SignupEnum signup(User user) {
        if (user == null) {
            return SignupEnum.ERROR;
        }
        try {
            int effectedNum = userDao.add(user);
            if (effectedNum <= 0) {
                throw new LoginOperationException("注册失败");
            }
        } catch (Exception e) {
            throw new LoginOperationException("loginError:" + e.getMessage());
        }
        return SignupEnum.SUCCESS;
    }

    @Transactional
	@Override
	public User selectUser(User user) {
		return userDao.selectUser(user.getUserMail());
	}

    @Override
    public String signupUser(User user) {
        return userDao.signinUser(user);
    }
}