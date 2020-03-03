package com.origincat.bookstore.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.origincat.bookstore.enums.SignupEnum;
import com.origincat.bookstore.pojo.Store;
import com.origincat.bookstore.pojo.User;
import com.origincat.bookstore.servlet.SignupServlet;
import com.origincat.bookstore.servlet.StoreServlet;
import com.origincat.bookstore.utils.HttpServletRequestUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SignupController {

    @Autowired
    SignupServlet signupServlet;

    @Autowired
    StoreServlet storeServlet;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String requestMethodSignup() {
        return "signup";
    }

    @ResponseBody
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    private Map<String, Object> registerUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String userStr = HttpServletRequestUtil.getString(request, "userStr");
        ObjectMapper mapper = new ObjectMapper();
        User user = null;
        try {
            user = mapper.readValue(userStr, User.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (signupServlet.selectUser(user) != null) {
            modelMap.put("success", false);
            modelMap.put("msg", "该邮箱已被注册");
        } else if (user != null) {
            Cookie cookie = new Cookie("userMail", user.getUserMail());
            response.addCookie(cookie);
            user.setCreateTime(new Date());
            user.setLastEditTime(new Date());
            SignupEnum se = signupServlet.signup(user);
            if(user.getUserKind().equals("2")){
                Store store = new Store();
                store.setStorePhone(user.getUserPhone());
                store.setStoreInf("高冷店家,暂无简介");
                store.setStoreLoc("地址暂无");
                store.setStoreOwner(user.getUserMail());
                store.setStoreName("默认店名");
                store.setStoreImg("https://www.jinxinsong.cn/images/thumbnail.svg");
                storeServlet.add(store);
            }
            if (se.getState() == SignupEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("userKind", user.getUserKind());
            } else {
                modelMap.put("success", false);
                modelMap.put("msg", "请输入正确信息");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("msg", "请输入用户信息");
        }

        return modelMap;
    }

    // @RequestMapping(value = "/mailtest", method = RequestMethod.POST)
    // private Map<String, Object> MailTest(HttpServletRequest request) {
    //     Map<String, Object> modelMap = new HashMap<String, Object>();
    //     String userStr = HttpServletRequestUtil.getString(request, "mailtestStr");
    //     ObjectMapper mapper = new ObjectMapper();
    //     User user = null;
    //     try {
    //         user = mapper.readValue(userStr, User.class);
    //     } catch (Exception e) {
    //         modelMap.put("success", false);

    //         modelMap.put("errMsg", e.getMessage());
    //         return modelMap;
    //     }

    //     User userTest = signupServlet.selectUser(user);
    //     if (userTest != null) {
    //         modelMap.put("success", true);
    //     } else {
    //         modelMap.put("success", false);
    //     }

    //     return null;

    // }
}