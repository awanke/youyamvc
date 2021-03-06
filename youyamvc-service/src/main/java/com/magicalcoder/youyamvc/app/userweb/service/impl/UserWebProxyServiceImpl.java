package com.magicalcoder.youyamvc.app.userweb.service.impl;

import com.magicalcoder.youyamvc.app.userweb.dao.UserWebProxyDao;
import com.magicalcoder.youyamvc.app.userweb.service.UserWebProxyService;
import com.magicalcoder.youyamvc.app.userweb.service.UserWebService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
/**
    代理类 保证了service dao的自动生成 有改动业务 直接写在此类处
*/
/**
* Created by hdy.
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@Component("userWebProxyService")
public class UserWebProxyServiceImpl implements UserWebProxyService{

    @Resource(name="userWebProxyDao")
    private UserWebProxyDao userWebProxyDao;

    @Resource(name="userWebService")
    private UserWebService userWebService;

}
