package com.origincat.bookstore.servlet.impl;

import javax.management.RuntimeErrorException;

import com.origincat.bookstore.dao.StoreDao;
import com.origincat.bookstore.pojo.Store;
import com.origincat.bookstore.servlet.StoreServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreServletImpl implements StoreServlet {
    @Autowired
    private StoreDao storeDao;

	@Transactional
    @Override
	public int add(Store store) {
        int effectedNum = storeDao.add(store);
        if(effectedNum <= 0){
            throw new RuntimeErrorException(null, "新建店铺失败");
        }
        return 1;
    }
    
    @Transactional
    @Override
    public Store select(Store store) {
        return storeDao.selectUser(store.getStoreOwner());
    }
    
}