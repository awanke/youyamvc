package com.magicalcoder.youyamvc.app.adminuser.dao.impl;

import com.magicalcoder.youyamvc.app.adminuser.dao.AdminUserDao;
import com.magicalcoder.youyamvc.app.model.AdminUser;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Component("adminUserDao")
public class AdminUserDaoImpl implements AdminUserDao {
	
	@Resource(name="sqlSessionTemplate")
	private  SqlSessionTemplate  sqlSessionTemplate;
	@Override
	public AdminUser getAdminUser(Map<String, Object> query) {
		return sqlSessionTemplate.selectOne("AdminUserMapper.getAdminUser",query);
	}
	@Override
	public Long insertAdminUser(AdminUser entity) {
		sqlSessionTemplate.insert("AdminUserMapper.insertAdminUser",entity);
		return entity.getId();
	}
	@Override
	public void updateAdminUser(AdminUser entity) {
		sqlSessionTemplate.update("AdminUserMapper.updateAdminUser", entity);
	}
	@Override
	public List<AdminUser> getAdminUserList(Map<String, Object> query) {
		return sqlSessionTemplate.selectList("AdminUserMapper.getAdminUserList", query);
	}
	@Override
	public Integer getAdminUserListCount(Map<String, Object> query) {
		return sqlSessionTemplate.selectOne("AdminUserMapper.getAdminUserListCount", query);
	}
	@Override
	public void truncateAdminUser() {
		sqlSessionTemplate.delete("AdminUserMapper.truncateAdminUser");
	}
	@Override
	public void deleteAdminUser(Map<String, Object> query) {
		sqlSessionTemplate.delete("AdminUserMapper.deleteAdminUser",query);
	}
    @Override
    public void batchInsertAdminUser(List<AdminUser> list) {
        sqlSessionTemplate.insert("AdminUserMapper.batchInsertAdminUser",list);
    }
}
