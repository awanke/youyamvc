package com.magicalcoder.youyamvc.app.school.dao.impl;

import com.magicalcoder.youyamvc.app.school.dao.SchoolProxyDao;
import com.magicalcoder.youyamvc.app.model.School;
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
@Component("schoolProxyDao")
public class SchoolProxyDaoImpl implements SchoolProxyDao {
    @Resource(name="sqlSessionTemplate")
    private  SqlSessionTemplate  sqlSessionTemplate;
}
