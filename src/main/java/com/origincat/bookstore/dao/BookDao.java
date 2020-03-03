package com.origincat.bookstore.dao;

import java.util.List;

import com.origincat.bookstore.pojo.Book;

// import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
  
@Mapper
public interface BookDao {
  
    @Select("select * from book ")
    public List<Book> findAllBook();
      
    @Insert("insert into book (bookName, bookInf, bookPrice, bookOwner, bookNum, bookStatu) values (#{bookName}, #{bookInf}, #{bookPrice}, #{bookOwner}, #{bookNum}, #{bookStatu}) ")
    public int save(Book Book);
          
    @Select("select * from book where bookOwner = #{bookOwner} ")
    public List<Book> getStoreBook(String bookOwner);

    @Select("select * from book where bookID = #{bookID}")
    public Book getOneBook(int bookID);
        
    @Update("update book set bookName=#{bookName},bookInf=#{bookInf},bookPrice=#{bookPrice},bookNum=#{bookNum},bookStatu=#{bookStatu} where bookID=#{bookID} ")
    public int update(Book Book);
  
}