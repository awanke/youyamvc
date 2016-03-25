package com.magicalcoder.youyamvc.app.userweb.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by hdy.
* 799374340@qq.com
*/public class UserWebDto implements Serializable {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTwoCodeImgSrc() {
        return twoCodeImgSrc;
    }

    public void setTwoCodeImgSrc(String twoCodeImgSrc) {
        this.twoCodeImgSrc = twoCodeImgSrc;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public BigDecimal getScoreAmount() {
        return scoreAmount;
    }

    public void setScoreAmount(BigDecimal scoreAmount) {
        this.scoreAmount = scoreAmount;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getBabySex() {
        return babySex;
    }

    public void setBabySex(Integer babySex) {
        this.babySex = babySex;
    }

    public Date getBabyBirthday() {
        return babyBirthday;
    }

    public void setBabyBirthday(Date babyBirthday) {
        this.babyBirthday = babyBirthday;
    }

    public Integer getBabyTwoSex() {
        return babyTwoSex;
    }

    public void setBabyTwoSex(Integer babyTwoSex) {
        this.babyTwoSex = babyTwoSex;
    }

    public Date getBabyTwoBirthday() {
        return babyTwoBirthday;
    }

    public void setBabyTwoBirthday(Date babyTwoBirthday) {
        this.babyTwoBirthday = babyTwoBirthday;
    }

    public Integer getBabyThreeSex() {
        return babyThreeSex;
    }

    public void setBabyThreeSex(Integer babyThreeSex) {
        this.babyThreeSex = babyThreeSex;
    }

    public Date getBabyThreeBirthday() {
        return babyThreeBirthday;
    }

    public void setBabyThreeBirthday(Date babyThreeBirthday) {
        this.babyThreeBirthday = babyThreeBirthday;
    }

    public String getHeadImgSrc() {
        return headImgSrc;
    }

    public void setHeadImgSrc(String headImgSrc) {
        this.headImgSrc = headImgSrc;
    }

    public Integer getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(Integer accountLevel) {
        this.accountLevel = accountLevel;
    }
}
