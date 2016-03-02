package com.magicalcoder.youyamvc.app.dict.dao.impl;

import com.magicalcoder.youyamvc.app.dict.dao.DictDao;
import com.magicalcoder.youyamvc.app.model.dict.Dict;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("dictDao")
public class DictDaoImpl implements DictDao {
	@Resource(name="sqlSessionTemplate")
	private  SqlSessionTemplate  sqlSessionTemplate;

	@Override
	public List<Dict> getAllDict() {
		return sqlSessionTemplate.selectList("DictMapper.getAllDict");
	}
	
}
