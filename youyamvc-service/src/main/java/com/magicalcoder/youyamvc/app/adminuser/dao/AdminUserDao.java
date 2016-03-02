package com.magicalcoder.youyamvc.app.adminuser.dao;
import java.util.List;
import java.util.Map;
import com.magicalcoder.youyamvc.app.model.AdminUser;
public interface AdminUserDao {
	AdminUser getAdminUser(Map<String, Object> query);
	Long insertAdminUser(AdminUser entity);
	void updateAdminUser(AdminUser entity);
	List<AdminUser> getAdminUserList(Map<String, Object> query);
	Integer getAdminUserListCount(Map<String, Object> query);
	public void truncateAdminUser();
	public void deleteAdminUser(Map<String, Object> query);
	void batchInsertAdminUser(List<AdminUser> entity);
}
