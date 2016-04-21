package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class Student{

    private Long classId;//所属班级
    private Integer sex;//性别
    private String name;//学生名称
    private Long identyKey;//主键值

    public Long getClassId(){
        return classId;
    }
    public void setClassId(Long classId){
        this.classId = classId;
    }
    public Integer getSex(){
        return sex;
    }
    public void setSex(Integer sex){
        this.sex = sex;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Long getIdentyKey(){
        return identyKey;
    }
    public void setIdentyKey(Long identyKey){
        this.identyKey = identyKey;
    }

}
