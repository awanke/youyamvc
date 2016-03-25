package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class TUsers{

    private Integer id;//主键
    private String userName;//登录名
    private String password;//密码

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

}
