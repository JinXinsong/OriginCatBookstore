package com.origincat.bookstore.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
// import javax.servlet.http.HttpSession;
import com.origincat.bookstore.dao.BookDao;
import com.origincat.bookstore.dao.StoreDao;
import com.origincat.bookstore.pojo.Book;
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
public class EditBook {

    @Autowired
    SignupServlet signupServlet;

    @Autowired
    StoreServlet storeServlet;

    @Autowired
    BookDao bookDao;

    @Autowired
    StoreDao storeDao;

    @RequestMapping(value = "/storeadmin/editbook", method = RequestMethod.GET)
    public String EditStore(final Model m, final int bookID) {
        m.addAttribute("bookID", bookID);

        return "storeadmin/editbook";
    }

    @ResponseBody
    @RequestMapping(value = "/storeadmin/editbook", method = RequestMethod.POST)
    private Map<String, Object> EditStorePost(final HttpServletRequest request) {
        final Map<String, Object> modelMap = new HashMap<String, Object>();
        final String bookStr = HttpServletRequestUtil.getString(request, "bookStr");
        final ObjectMapper mapper = new ObjectMapper();
        Book book = null;
        try {
            book = mapper.readValue(bookStr, Book.class);
        } catch (final Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.getMessage());
            return modelMap;
        }

        final Book newBook = bookDao.getOneBook(book.getBookID());
        if(!book.getBookName().equals("")){
            newBook.setBookName(book.getBookName());
        }
        if(!book.getBookInf().equals("")){
            newBook.setBookInf(book.getBookInf());
        }
        if(book.getBookNum()!=newBook.getBookNum()){
            newBook.setBookNum(book.getBookNum());
        }
        if(Math.abs(book.getBookPrice()-0) > 0.00000001){
            newBook.setBookPrice(book.getBookPrice());
        }
        if(!book.getBookStatu().equals(newBook.getBookStatu())){
            newBook.setBookStatu(book.getBookStatu());
        }

        bookDao.update(newBook);
        
        modelMap.put("success", true);
        // modelMap.put("sessionID", httpSession.getId());

        return modelMap;

    }
}