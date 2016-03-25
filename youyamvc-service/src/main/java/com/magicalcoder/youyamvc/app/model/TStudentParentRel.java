package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class TStudentParentRel{

    private Integer id;//主键
    private Integer studentId;//学生id
    private Integer userId;//用户id
    private String relation;//亲属关系

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public Integer getStudentId(){
        return studentId;
    }
    public void setStudentId(Integer studentId){
        this.studentId = studentId;
    }
    public Integer getUserId(){
        return userId;
    }
    public void setUserId(Integer userId){
        this.userId = userId;
    }
    public String getRelation(){
        return relation;
    }
    public void setRelation(String relation){
        this.relation = relation;
    }

}
