package com.magicalcoder.youyamvc.app.dict.listener;

import com.magicalcoder.youyamvc.app.dict.service.DictService;
import com.magicalcoder.youyamvc.app.model.dict.Dict;
import com.magicalcoder.youyamvc.app.model.dict.Dictionary;
import com.magicalcoder.youyamvc.core.cache.xmemcached.utils.MemcachedClientUtils;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class DictionaryListener implements ApplicationListener<ContextRefreshedEvent> {
	@Resource(name="dictService")
	private DictService dictService;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("数据库字典初始化...");
		try{
			List<Dict> allDict = this.dictService.getAllDict();
			if(ListUtils.isNotBlank(allDict)){
				for(Dict dict : allDict){
					Dictionary.autoInit(dict);
				}
			}
			System.out.println("数据库字典初始化完成^ ^");
		}catch (Exception e){
			e.printStackTrace();
			System.err.println("数据库连接失败，请检查src/main/resources/jdbc.properties");
		}

		try {
			MemcachedClientUtils.get().get("1");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Memcache连接失败，请检查src/main/resources/xmemcached.properties");
		}
	}


}
