package com.origincat.bookstore.web;

import java.util.List;

import com.origincat.bookstore.dao.BookDao;
import com.origincat.bookstore.pojo.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StoreBook{
    @Autowired
    BookDao bookDao;

    @RequestMapping(value = "/storebook")
    public String storeBook(String storeOwner, Model m){
        List<Book> books = bookDao.getStoreBook(storeOwner);

        m.addAttribute("page", books);
        
        return "storebook" ;
    }
}