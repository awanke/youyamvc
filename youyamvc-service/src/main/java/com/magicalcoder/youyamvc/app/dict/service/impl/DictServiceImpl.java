package com.magicalcoder.youyamvc.app.dict.service.impl;

import com.magicalcoder.youyamvc.app.dict.dao.DictDao;
import com.magicalcoder.youyamvc.app.dict.service.DictService;
import com.magicalcoder.youyamvc.app.model.dict.Dict;
import com.magicalcoder.youyamvc.app.model.dict.Dictionary;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("dictService")
public class DictServiceImpl implements DictService {
	@Resource(name="dictDao")
	private DictDao dictDao;

	@Override
	public List<Dict> getAllDict() {
		return dictDao.getAllDict();
	}

	@Override
	public void refresh() {
		System.out.println("手动：数据库字典初始化...");
		Dictionary.flush();
		List<Dict> allDict = getAllDict(); 
		if(ListUtils.isNotBlank(allDict)){
			for(Dict dict : allDict){
				Dictionary.autoInit(dict);
			}
		}
		System.out.println("手动：数据库字典初始化完成^ ^");
	}
	
}
