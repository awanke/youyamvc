package com.magicalcoder.youyamvc.app.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class TStudent{

    private Integer id;//主键
    private String number;//学生编号 jsnjqx0001
    private String realName;//学生姓名
    private String pyrealName;//学生姓名拼音
    private Integer classId;//班级id
    private Integer gender;//性别 0 女 1男
    private Integer createId;//创建人
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;//创建时间
    private Integer modifyId;//修改人
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date modifyTime;//修改时间

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public String getNumber(){
        return number;
    }
    public void setNumber(String number){
        this.number = number;
    }
    public String getRealName(){
        return realName;
    }
    public void setRealName(String realName){
        this.realName = realName;
    }
    public String getPyrealName(){
        return pyrealName;
    }
    public void setPyrealName(String pyrealName){
        this.pyrealName = pyrealName;
    }
    public Integer getClassId(){
        return classId;
    }
    public void setClassId(Integer classId){
        this.classId = classId;
    }
    public Integer getGender(){
        return gender;
    }
    public void setGender(Integer gender){
        this.gender = gender;
    }
    public Integer getCreateId(){
        return createId;
    }
    public void setCreateId(Integer createId){
        this.createId = createId;
    }
    public Date getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    public Integer getModifyId(){
        return modifyId;
    }
    public void setModifyId(Integer modifyId){
        this.modifyId = modifyId;
    }
    public Date getModifyTime(){
        return modifyTime;
    }
    public void setModifyTime(Date modifyTime){
        this.modifyTime = modifyTime;
    }

}
