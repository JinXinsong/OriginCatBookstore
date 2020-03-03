package com.origincat.bookstore.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class User {
    //ID
    private Integer userID;
    //邮箱
    private String userMail;
    //密码
    private String userPasswd;
    //手机号
    private String userPhone;
    //创建时间
    private Date createTime;
    //更新时间
    private Date lastEditTime;
    //用户类型
    private String userKind;
    //用户昵称
    private String userName;
}