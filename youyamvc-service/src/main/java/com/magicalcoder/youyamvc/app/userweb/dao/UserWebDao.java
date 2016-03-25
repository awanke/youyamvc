package com.magicalcoder.youyamvc.app.userweb.dao;

import com.magicalcoder.youyamvc.app.model.UserWeb;

import java.util.List;
import java.util.Map;

/**
* Created by hdy.
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface UserWebDao{
    UserWeb getUserWeb(Map<String, Object> query);
    Long insertUserWeb(UserWeb entity);
    void batchInsertUserWeb(List<UserWeb> list);
    void batchUpdateUserWeb(List<UserWeb> list);
    void updateUserWeb(UserWeb entity);
    void updateUserWebByWhereSql(Map<String,Object> entity);
    List<UserWeb> getUserWebList(Map<String, Object> query);
    Integer getUserWebListCount(Map<String, Object> query);
    void truncateUserWeb();
    void deleteUserWeb(Map<String, Object> query);
    void deleteUserWebByWhereSql(Map<String, Object> query);
    //id list
    void batchDeleteUserWeb(List<Long> list);


}
