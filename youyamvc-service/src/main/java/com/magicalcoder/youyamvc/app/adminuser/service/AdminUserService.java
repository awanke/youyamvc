package com.magicalcoder.youyamvc.app.adminuser.service;
import java.util.List;
import java.util.Map;
import com.magicalcoder.youyamvc.app.model.AdminUser;
public interface AdminUserService {
	AdminUser getAdminUser(Long id);
	Long insertAdminUser(AdminUser entity);
	void updateAdminUser(AdminUser entity);
	List<AdminUser> getAdminUserList(Map<String, Object> query);
	Integer getAdminUserListCount(Map<String, Object> query);
	public void truncateAdminUser();
	public void deleteAdminUser(Long id);
	void batchInsertAdminUser(List<AdminUser> entity);
}
