package com.magicalcoder.youyamvc.app.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
* Created by hdy.
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class UserWeb{
    private Long id;//主键
    private String userName;//登录名称
    private String mobile;//手机号码
    private String twoCodeImgSrc;//二维码图片
    private String nickname;//昵称
    private String userPassword;//登录密码存储加密后的值
    private String realName;//用户真名
    private BigDecimal scoreAmount;//积分余额
    private BigDecimal moneyAmount;//现金余额
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date registTime;//注册时间
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date lastLoginTime;//最后登录时间
    private Integer accountStatus;//账号状态
    private Integer sex;//性别
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date birthday;//生日
    private Integer babySex;//大宝宝性别
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date babyBirthday;//大宝宝生日
    private Integer babyTwoSex;//二宝宝性别
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date babyTwoBirthday;//二宝宝生日
    private Integer babyThreeSex;//三宝宝性别
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date babyThreeBirthday;//三宝宝生日
    private String headImgSrc;//头像地址
    private Integer accountLevel;//账号等级

    public Long getId(){
        return id;
    }
    public String getUserName(){
        return userName;
    }
    public String getMobile(){
        return mobile;
    }
    public String getTwoCodeImgSrc(){
        return twoCodeImgSrc;
    }
    public String getNickname(){
        return nickname;
    }
    public String getUserPassword(){
        return userPassword;
    }
    public String getRealName(){
        return realName;
    }
    public BigDecimal getScoreAmount(){
        return scoreAmount;
    }
    public BigDecimal getMoneyAmount(){
        return moneyAmount;
    }
    public Date getRegistTime(){
        return registTime;
    }
    public Date getLastLoginTime(){
        return lastLoginTime;
    }
    public Integer getAccountStatus(){
        return accountStatus;
    }
    public Integer getSex(){
        return sex;
    }
    public Date getBirthday(){
        return birthday;
    }
    public Integer getBabySex(){
        return babySex;
    }
    public Date getBabyBirthday(){
        return babyBirthday;
    }
    public Integer getBabyTwoSex(){
        return babyTwoSex;
    }
    public Date getBabyTwoBirthday(){
        return babyTwoBirthday;
    }
    public Integer getBabyThreeSex(){
        return babyThreeSex;
    }
    public Date getBabyThreeBirthday(){
        return babyThreeBirthday;
    }
    public String getHeadImgSrc(){
        return headImgSrc;
    }
    public Integer getAccountLevel(){
        return accountLevel;
    }
    public void setId(Long id){
        this.id = id;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public void setTwoCodeImgSrc(String twoCodeImgSrc){
        this.twoCodeImgSrc = twoCodeImgSrc;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }
    public void setRealName(String realName){
        this.realName = realName;
    }
    public void setScoreAmount(BigDecimal scoreAmount){
        this.scoreAmount = scoreAmount;
    }
    public void setMoneyAmount(BigDecimal moneyAmount){
        this.moneyAmount = moneyAmount;
    }
    public void setRegistTime(Date registTime){
        this.registTime = registTime;
    }
    public void setLastLoginTime(Date lastLoginTime){
        this.lastLoginTime = lastLoginTime;
    }
    public void setAccountStatus(Integer accountStatus){
        this.accountStatus = accountStatus;
    }
    public void setSex(Integer sex){
        this.sex = sex;
    }
    public void setBirthday(Date birthday){
        this.birthday = birthday;
    }
    public void setBabySex(Integer babySex){
        this.babySex = babySex;
    }
    public void setBabyBirthday(Date babyBirthday){
        this.babyBirthday = babyBirthday;
    }
    public void setBabyTwoSex(Integer babyTwoSex){
        this.babyTwoSex = babyTwoSex;
    }
    public void setBabyTwoBirthday(Date babyTwoBirthday){
        this.babyTwoBirthday = babyTwoBirthday;
    }
    public void setBabyThreeSex(Integer babyThreeSex){
        this.babyThreeSex = babyThreeSex;
    }
    public void setBabyThreeBirthday(Date babyThreeBirthday){
        this.babyThreeBirthday = babyThreeBirthday;
    }
    public void setHeadImgSrc(String headImgSrc){
        this.headImgSrc = headImgSrc;
    }
    public void setAccountLevel(Integer accountLevel){
        this.accountLevel = accountLevel;
    }

}
