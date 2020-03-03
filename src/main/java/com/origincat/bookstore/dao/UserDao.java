package com.origincat.bookstore.dao;

import com.origincat.bookstore.pojo.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao{

    @Insert("insert into user(userMail, userPasswd, userPhone, createTime, lastEditTime, userKind, userName) values(#{userMail}, #{userPasswd}, #{userPhone}, #{createTime}, #{lastEditTime}, #{userKind}, #{userName})")
    public int add(User user);

    @Select("select * from user where userMail = #{userMail}")
    public User selectUser(String userMail);

    @Select("select userKind from user where (userMail = #{userMail} and userPasswd = #{userPasswd}) ")
    public String signinUser(User user);
}