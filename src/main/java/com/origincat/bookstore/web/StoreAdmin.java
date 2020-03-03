package com.origincat.bookstore.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
// import javax.servlet.http.HttpSession;
import com.origincat.bookstore.dao.BookDao;
import com.origincat.bookstore.dao.StoreDao;
import com.origincat.bookstore.pojo.Book;
import com.origincat.bookstore.pojo.Store;
import com.origincat.bookstore.pojo.User;
import com.origincat.bookstore.servlet.SignupServlet;
import com.origincat.bookstore.servlet.StoreServlet;
import com.origincat.bookstore.utils.HttpServletRequestUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StoreAdmin {

    @Autowired
    SignupServlet signupServlet;

    @Autowired
    StoreServlet storeServlet;

    @Autowired
    BookDao bookDao;

    @Autowired
    StoreDao storeDao;

    @RequestMapping(value = "/storeadmin/home", method = RequestMethod.GET)
    public String StoreAdminHome(HttpServletRequest request, Model m) {
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
        List<Book> books = bookDao.getStoreBook(store.getStoreOwner());
        // PageInfo<Book> page = new PageInfo<>(books);
        m.addAttribute("page", books);

        return "storeadmin/home";
    }

    @RequestMapping(value = "/storeadmin/addbook", method = RequestMethod.GET)
    public String AddBook() {
        return "storeadmin/addbook";
    }

    @ResponseBody
    @RequestMapping(value = "/storeadmin/addbook", method = RequestMethod.POST)
    private Map<String, Object> AddBookPost(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String bookStr = HttpServletRequestUtil.getString(request, "bookStr");
        // HttpSession httpSession = request.getSession();
        ObjectMapper mapper = new ObjectMapper();
        Book book = null;
        try {
            book = mapper.readValue(bookStr, Book.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.getMessage());
            return modelMap;
        }


        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userMail")) {
                    book.setBookOwner(cookie.getValue());
                    bookDao.save(book);
                }
            }
        }
        modelMap.put("success", true);
        // modelMap.put("sessionID", httpSession.getId());

        return modelMap;

    }

    @RequestMapping(value = "/storeadmin/editstore", method = RequestMethod.GET)
    public String EditStore() {
        return "storeadmin/editstore";
    }

    @ResponseBody
    @RequestMapping(value = "/storeadmin/editstore", method = RequestMethod.POST)
    private Map<String, Object> EditStorePost(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String bookStr = HttpServletRequestUtil.getString(request, "bookStr");
        // HttpSession httpSession = request.getSession();
        ObjectMapper mapper = new ObjectMapper();
        Store store = null;
        try {
            store = mapper.readValue(bookStr, Store.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.getMessage());
            return modelMap;
        }

        Store newStore = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userMail")) {
                    newStore = storeDao.selectUser(cookie.getValue());
                    if(store.getStoreName() != null && !"".equals(store.getStoreName())){
                        newStore.setStoreName(store.getStoreName());
                    }
                    if(store.getStoreInf() != null && !"".equals(store.getStoreInf())){
                        newStore.setStoreInf(store.getStoreInf());
                    }
                    if(store.getStoreLoc() != null && !"".equals(store.getStoreLoc())){
                        newStore.setStoreLoc(store.getStoreLoc());
                    }
                    if(store.getStorePhone() != null && !"".equals(store.getStorePhone())){
                        newStore.setStorePhone(store.getStorePhone());;
                    }
                    storeDao.updateStore(newStore);
                }
            }
        }
        modelMap.put("success", true);
        // modelMap.put("sessionID", httpSession.getId());

        return modelMap;

    }
}