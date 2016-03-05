package com.magicalcoder.youyamvc.app.school.dao;

import com.magicalcoder.youyamvc.app.model.School;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public interface SchoolDao{
    School getSchool(Map<String, Object> query);
    Long insertSchool(School entity);
    void batchInsertSchool(List<School> list);
    void batchUpdateSchool(List<School> list);
    void updateSchool(School entity);
    void updateSchoolByWhereSql(Map<String, Object> entity);
    List<School> getSchoolList(Map<String, Object> query);
    Integer getSchoolListCount(Map<String, Object> query);
    void truncateSchool();
    void deleteSchool(Map<String, Object> query);
    void deleteSchoolByWhereSql(Map<String, Object> query);
    //id list
    void batchDeleteSchool(List<Long> list);


}
