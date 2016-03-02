package com.magicalcoder.youyamvc.app.dict.service;


import com.magicalcoder.youyamvc.app.model.dict.Dict;

import java.util.List;

public interface DictService {

	List<Dict> getAllDict();
	void refresh();
}
