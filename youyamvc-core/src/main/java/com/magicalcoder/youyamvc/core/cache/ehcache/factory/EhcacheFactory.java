package com.magicalcoder.youyamvc.core.cache.ehcache.factory;

import java.util.HashMap;
import java.util.Map;

import com.magicalcoder.youyamvc.core.cache.ehcache.constant.EhcacheConstant;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/** Ehcache工厂 用来获取配置cache
 *hdy qq:799374340
 *2013-6-3 上午10:59:52
 */
public class EhcacheFactory {
	private  static EhcacheFactory instance = new EhcacheFactory();
	public static EhcacheFactory get(){
		return instance;
	}
	private EhcacheFactory(){
		//禁止外部new
		manager = CacheManager.create();
//		System.out.println("初始化");
	}
	private static  CacheManager manager;
	private static Map<String,Cache> cacheMap = new HashMap<String,Cache>();
	//获取配置缓存 并存放cacheMap中
	public Cache getCache(String cacheConfigName){
		Cache cache = cacheMap.get(cacheConfigName);
		if(cache==null){
			cacheMap.put(cacheConfigName, manager.getCache(cacheConfigName));
		}
		return cacheMap.get(cacheConfigName);
	}
    public Cache getDefaultCache(){
        String cacheConfigName = EhcacheConstant.DEFAULT_CACHE;
        Cache cache = cacheMap.get(cacheConfigName);
        if(cache==null){
            cacheMap.put(cacheConfigName, manager.getCache(cacheConfigName));
        }
        return cacheMap.get(cacheConfigName);
    }
	public void shutDownEhcache(){
		manager.shutdown();
	}
/*	private Cache cache;
	public Cache getCache(){
		//CacheManager manager = CacheManager.create();
		// 使用默认配置文件创建CacheManager
		CacheManager manager = CacheManager.create();
		// 通过manager可以生成指定名称的Cache对象
		cache = manager.getCache("demoCache");
		return cache;
		// 使用manager移除指定名称的Cache对象
//		manager.removeCache("demoCache");
		//可以通过调用manager.removalAll()来移除所有的Cache。通过调用manager的shutdown()方法可以关闭CacheManager。
		//往cache中添加元素
//		Element element = new Element("key", "value");
//		cache.put(element);
		//从cache中取回元素
//		Element element = cache.get("key");
//		element.getObjectValue();
		//从Cache中移除一个元素
//		cache.remove("key");
	}*/
}
