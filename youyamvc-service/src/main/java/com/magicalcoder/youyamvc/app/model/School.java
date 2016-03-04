package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class School{

    private Long id;//学校主键
    private String schoolName;//学校名称
    private String adress;//学校地址
    private String schoolDesc;//学校描述

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getSchoolName(){
        return schoolName;
    }
    public void setSchoolName(String schoolName){
        this.schoolName = schoolName;
    }
    public String getAdress(){
        return adress;
    }
    public void setAdress(String adress){
        this.adress = adress;
    }
    public String getSchoolDesc(){
        return schoolDesc;
    }
    public void setSchoolDesc(String schoolDesc){
        this.schoolDesc = schoolDesc;
    }

}
