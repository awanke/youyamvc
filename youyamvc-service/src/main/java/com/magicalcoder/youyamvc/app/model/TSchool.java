package com.magicalcoder.youyamvc.app.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class TSchool{

    private Integer id;//主键
    private String name;//学校名称
    private String number;//学校编号(jsnjqx0001)
    private String address;//详细地址
    private String phone;//联系电话
    private Integer areaId;//地区id
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
    public String getNumber(){
        return number;
    }
    public void setNumber(String number){
        this.number = number;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public Integer getAreaId(){
        return areaId;
    }
    public void setAreaId(Integer areaId){
        this.areaId = areaId;
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
