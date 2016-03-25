package com.magicalcoder.youyamvc.app.classes.service;

import com.magicalcoder.youyamvc.app.model.Classes;

import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface ClassesService{
    /**
    * 根据主键获取实体
    * @param id 主键
    * @return
    */
    Classes getClasses(Long id);

    /**
    * 调用mybatis selectOne 如果查询返回超过1条 就会发生异常 请自行处理
    * @param query 入参 调用MapUtil构造
    * @return
    */
    Classes selectOneClassesWillThrowException(Map<String, Object> query);

    /**
    * 查询实体集合
    * @param query
    * @return
    */
    List<Classes> getClassesList(Map<String, Object> query);

    /**
    * 查询实体集合的个数
    * @param query
    * @return
    */
    Integer getClassesListCount(Map<String, Object> query);

    /**
    * 保存实体 保证entity 主键为空
    * @param entity
    * @return
    */
    Long insertClasses(Classes entity);

    /**
    * 更新实体 保证实体中的主键不为空
    * @param entity
    */
    void updateClasses(Classes entity);

    /**
    * 更新实体 自定义条件
    * @param entity   not empty
    * @param whereSql not blank 有sql注入风险 请开发人员自行保证安全性
    */
    void updateClassesByWhereSql(Map<String,Object> entity,String whereSql);

    /**
    * 根据主键删除实体
    * @param id
    */
    void deleteClasses(Long id);

    /**
    * 根据自定义条件删除实体
    * @param whereSql not blank  有sql注入风险 请开发人员自行保证安全性
*/
    void deleteClassesByWhereSql(String whereSql);

    /**
    * 根据外键表的条件查询本实体
    * @query
    */
    List<Classes> getClassesOneToOneRelateList(Map<String, Object> query);

    /**
    * 根据外键表的条件查询本实体个数
    * @query
    */
    Integer getClassesOneToOneRelateListCount(Map<String, Object> query);

    /**
    * 清空实体表
    */
    void truncateClasses();

    /**
    *  批量保存实体，保证list中的实体 主键为空
    * @param list
    */
    void batchInsertClasses(List<Classes> list);

    /**
    * 批量更新实体 保证list中的实体 主键不为空
    * @param list
    */
    void batchUpdateClasses(List<Classes> list);

    /**
    * 根据主键list 批量删除实体
    * @param idList
    */
    void batchDeleteClasses(List<Long> idList);


    /**
    * 事务保证 导入json文件成功
    * @param list json反序列化后的文件
    * @return
    */
    void transactionImportJsonList(List<Classes> list);
}
