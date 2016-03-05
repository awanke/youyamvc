package com.magicalcoder.youyamvc.app.classteacher.dao.impl;

import com.magicalcoder.youyamvc.app.classteacher.dao.ClassTeacherProxyDao;
import com.magicalcoder.youyamvc.app.model.ClassTeacher;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@Component("classTeacherProxyDao")
public class ClassTeacherProxyDaoImpl implements ClassTeacherProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}
