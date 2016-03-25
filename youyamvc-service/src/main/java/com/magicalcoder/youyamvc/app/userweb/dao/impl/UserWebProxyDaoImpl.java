package com.magicalcoder.youyamvc.app.userweb.dao.impl;

import com.magicalcoder.youyamvc.app.userweb.dao.UserWebProxyDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
* Created by hdy.
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@Component("userWebProxyDao")
public class UserWebProxyDaoImpl implements UserWebProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}
