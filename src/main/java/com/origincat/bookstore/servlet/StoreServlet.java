package com.origincat.bookstore.servlet;

import com.origincat.bookstore.pojo.Store;

public interface StoreServlet {
    int add(Store store);

    Store select(Store store);
    // int editStore(Store store);
}