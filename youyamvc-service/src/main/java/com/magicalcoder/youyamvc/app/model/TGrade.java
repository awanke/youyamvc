package com.magicalcoder.youyamvc.app.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class TGrade{

    private Integer id;//主键
    private String name;//年级名称
    private Integer schoolId;//学校id
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
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Integer getSchoolId(){
        return schoolId;
    }
    public void setSchoolId(Integer schoolId){
        this.schoolId = schoolId;
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
