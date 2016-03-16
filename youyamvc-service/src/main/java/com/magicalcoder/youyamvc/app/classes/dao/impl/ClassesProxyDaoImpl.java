package com.magicalcoder.youyamvc.app.classes.dao.impl;

import com.magicalcoder.youyamvc.app.classes.dao.ClassesProxyDao;
import com.magicalcoder.youyamvc.app.model.Classes;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
@Component("classesProxyDao")
public class ClassesProxyDaoImpl implements ClassesProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}
