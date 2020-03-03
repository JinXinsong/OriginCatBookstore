package com.origincat.bookstore.pojo;

import lombok.Data;

@Data
public class Book{
    private int bookID;
    
    private String bookName;

    private String bookInf;

    private double bookPrice;

    private String bookOwner;

    private int bookNum;

    private String bookStatu;
}