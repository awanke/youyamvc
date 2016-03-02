package com.magicalcoder.youyamvc.core.identifyingcode;

/**
 * Created by www.magicalcoder.com on 2015/8/5.
 * 799374340@qq.com
 */
public interface IdentifyingCodeConstant {

    String CHECK_CODE_SESSION_KEY = "CheckCodeKey";


    String CHINA = "ch";
    String NUMBER = "n";
    String ENGLISH = "l";
    String NUMBER_ENGLISH = "nl";

    //使用类型 session memcache方式
    int STORE_TYPE_SESSION  = 0;
    int STORE_TYPE_MEMCACHE  = 1;


}
