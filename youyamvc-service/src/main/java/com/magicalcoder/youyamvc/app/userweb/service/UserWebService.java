package com.magicalcoder.youyamvc.app.userweb.service;

import com.magicalcoder.youyamvc.app.model.UserWeb;
import java.util.List;
import java.util.Map;

/**
* Created by hdy.
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface UserWebService{
    /**
    * 根据主键获取实体
    * @param id 主键
    * @return
    */
    UserWeb getUserWeb(Long id);

    /**
    * 调用mybatis selectOne 如果查询返回超过1条 就会发生异常 请自行处理
    * @param query 入参 调用MapUtil构造
    * @return
    */
    UserWeb selectOneUserWebWillThrowException(Map<String, Object> query);

    /**
    * 查询实体集合
    * @param query
    * @return
    */
    List<UserWeb> getUserWebList(Map<String, Object> query);

    /**
    * 查询实体集合的个数
    * @param query
    * @return
    */
    Integer getUserWebListCount(Map<String, Object> query);

    /**
    * 保存实体 保证entity 主键为空
    * @param entity
    * @return
    */
    Long insertUserWeb(UserWeb entity);

    /**
    * 更新实体 保证实体中的主键不为空
    * @param entity
    */
    void updateUserWeb(UserWeb entity);

    /**
    * 更新实体 自定义条件
    * @param entity   not empty
    * @param whereSql not blank
    */
    void updateUserWebByWhereSql(Map<String,Object> entity,String whereSql);

    /**
    * 根据主键删除实体
    * @param id
    */
    void deleteUserWeb(Long id);

    /**
    * 根据自定义条件删除实体
    * @param whereSql not blank
    */
    void deleteUserWebByWhereSql(String whereSql);


    /**
    * 清空实体表
    */
    void truncateUserWeb();

    /**
    *  批量保存实体，保证list中的实体 主键为空
    * @param list
    */
    void batchInsertUserWeb(List<UserWeb> list);

    /**
    * 批量更新实体 保证list中的实体 主键不为空
    * @param list
    */
    void batchUpdateUserWeb(List<UserWeb> list);

    /**
    * 根据主键list 批量删除实体
    * @param idList
    */
    void batchDeleteUserWeb(List<Long> idList);


}
