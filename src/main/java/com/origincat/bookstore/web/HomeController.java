package com.origincat.bookstore.web;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.origincat.bookstore.dao.BookDao;
import com.origincat.bookstore.dao.StoreDao;
import com.origincat.bookstore.pojo.Book;
import com.origincat.bookstore.pojo.Store;
import com.origincat.bookstore.pojo.User;
import com.origincat.bookstore.servlet.SignupServlet;
import com.origincat.bookstore.servlet.StoreServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController{
    @Autowired
    SignupServlet signupServlet;

    @Autowired
    StoreServlet storeServlet;

    @Autowired
    BookDao bookDao;

    @Autowired
    StoreDao storeDao;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String HomeMethodGET(Model m){
        // List<Book> books = bookDao.findAllBook();
        // PageInfo<Book> page = new PageInfo<>(books);
        List<Store> stores = storeDao.getAllStore();
        m.addAttribute("page", stores);

        return "home";
    }
    
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String HomeMethod(HttpServletRequest request, Model m){
        Cookie[] cookies = request.getCookies();
        User user = new User();
        Store store = new Store();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userMail")) {
                    user.setUserMail(cookie.getValue());
                    user = signupServlet.selectUser(user);
                    store.setStoreOwner(user.getUserMail());
                    store = storeServlet.select(store);
                }
            }
        }
        m.addAttribute("storeName", store.getStoreName());
        m.addAttribute("storePhone", store.getStorePhone());
        m.addAttribute("storeLoc", store.getStoreLoc());
        m.addAttribute("storeInf", store.getStoreInf());

        // PageHelper.startPage(0 , 4, "bookID bookName bookInf bookPrice bookOwner
        // bookNum bookStatu");
        List<Book> books = bookDao.findAllBook();
        // PageInfo<Book> page = new PageInfo<>(books);
        m.addAttribute("page", books);
        
        return "home";
    }

}