package com.origincat.bookstore.servlet;

import com.origincat.bookstore.enums.SignupEnum;
import com.origincat.bookstore.pojo.User;

public interface SignupServlet {
    SignupEnum signup(User user);

    User selectUser(User user);

    String signupUser(User user);
}