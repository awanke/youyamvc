package com.magicalcoder.youyamvc.app.model;

import org.springframework.format.annotation.DateTimeFormat;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class TTeacherClassRel{

    private Integer id;//主键
    private Integer teacherId;//老师id
    private Integer classId;//班级id

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public Integer getTeacherId(){
        return teacherId;
    }
    public void setTeacherId(Integer teacherId){
        this.teacherId = teacherId;
    }
    public Integer getClassId(){
        return classId;
    }
    public void setClassId(Integer classId){
        this.classId = classId;
    }

}
