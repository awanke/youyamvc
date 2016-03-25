package com.magicalcoder.youyamvc.app.adminuser.service.impl;

import com.magicalcoder.youyamvc.app.adminuser.dao.AdminUserDao;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminUserService;
import com.magicalcoder.youyamvc.app.model.AdminUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
