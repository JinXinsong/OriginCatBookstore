package com.origincat.bookstore.dao;

import java.util.List;

import com.origincat.bookstore.pojo.Store;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
 
@Mapper    
public interface StoreDao{
    @Insert("insert into store(storeName, storePhone, storeInf, storeLoc, storeOwner, storeImg) values(#{storeName}, #{storePhone}, #{storeInf}, #{storeLoc}, #{storeOwner}, #{storeImg})")
    public int add(Store store);

    @Select("select * from store where storeOwner = #{storeOwner}")
    public Store selectUser(String storeOwner);

    @Update("UPDATE store SET storeName = #{storeName}, storePhone = #{storePhone}, storeInf = #{storeInf}, storeLoc = #{storeLoc}, storeOwner = #{storeOwner}, storeImg = #{storeImg} WHERE storeID = #{storeID}")
    public int updateStore(Store store);

    @Select("select * from store")
    public List<Store> getAllStore();
}