package com.origincat.bookstore.enums;

import lombok.Getter;

public enum SignupEnum{
    CHECK(0, "审核中"),
    ERROR(-1,"操作失败"),
    SUCCESS(1, "操作成功");
    
    @Getter
    private int state;

    @Getter
    private String stateInfo;

    private SignupEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }
    public static SignupEnum stateOf(int state){
        for(SignupEnum stateEnum : values()){
            if(stateEnum.getState() == state){
                return stateEnum;
            }
        }
        return null;
    }
}