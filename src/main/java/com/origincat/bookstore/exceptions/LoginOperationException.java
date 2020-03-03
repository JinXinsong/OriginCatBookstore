package com.origincat.bookstore.exceptions;

public class LoginOperationException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 312465461324658746L;

    public LoginOperationException(String msg) {
        super(msg);
    }
}