package com.magicalcoder.youyamvc.app.adminuser.service.impl;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;

import com.magicalcoder.youyamvc.core.cache.xmemcached.anotation.XMemcachedParam;
import org.springframework.stereotype.Component;
import com.magicalcoder.youyamvc.app.adminuser.dao.AdminUserDao;
import com.magicalcoder.youyamvc.app.model.AdminUser;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminUserService;
@Component("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {
	@Resource(name="adminUserDao")
	private AdminUserDao adminUserDao;
	@Override
	public AdminUser getAdminUser(Long id) {
		Map<String,Object> query = new HashMap<String,Object>();
		query.put("id", id);
		return adminUserDao.getAdminUser(query);
	}
	@Override
	public Long insertAdminUser(AdminUser entity) {
		return adminUserDao.insertAdminUser(entity);
	}
	@Override
	public void updateAdminUser(AdminUser entity) {
		adminUserDao.updateAdminUser(entity);
	}
	@Override
	public List<AdminUser> getAdminUserList(Map<String, Object> query) {
		return adminUserDao.getAdminUserList(query);
	}
	@Override
	public Integer getAdminUserListCount(Map<String, Object> query) {
		return adminUserDao.getAdminUserListCount(query);
	}
	@Override
	public void truncateAdminUser() {
		adminUserDao.truncateAdminUser();
	}
	@Override
//	@XMemcachedParam(expire = 1000)
	public void deleteAdminUser(Long id) {
		Map<String,Object> query = new HashMap<String,Object>();
		query.put("id", id);
		adminUserDao.deleteAdminUser(query);
	}
	@Override
	public void batchInsertAdminUser(List<AdminUser> list) {
		adminUserDao.batchInsertAdminUser(list);
	}
}
