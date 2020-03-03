package com.origincat.bookstore.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.origincat.bookstore.pojo.User;
import com.origincat.bookstore.servlet.SignupServlet;
import com.origincat.bookstore.utils.HttpServletRequestUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController{

    @Autowired
    SignupServlet signupServlet;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String requestMethodIndex(){
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    private Map<String, Object> MailTest(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String userStr = HttpServletRequestUtil.getString(request, "userStr");
        //HttpSession httpSession = request.getSession();
        ObjectMapper mapper = new ObjectMapper();
        User user = null;
        try {
            user = mapper.readValue(userStr, User.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.getMessage());
            return modelMap;
        }

        String userKind = signupServlet.signupUser(user);
        if (userKind != null && userKind != "") {
            // HttpSession httpSession = request.getSession();
            // httpSession.setAttribute("user", user);
            Cookie cookie = new Cookie("userMail", user.getUserMail());
            response.addCookie(cookie);
            modelMap.put("success", true);
            // modelMap.put("sessionID", httpSession.getId());
            modelMap.put("userKind", userKind);
        } else {
            modelMap.put("success", false);
            modelMap.put("msg", "账号密码错误");
        }

        return modelMap;

    }
}