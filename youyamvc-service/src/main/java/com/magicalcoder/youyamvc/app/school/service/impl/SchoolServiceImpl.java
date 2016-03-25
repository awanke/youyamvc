
package com.magicalcoder.youyamvc.app.school.service.impl;

import com.magicalcoder.youyamvc.app.school.dao.SchoolDao;
import com.magicalcoder.youyamvc.app.school.service.SchoolService;
import com.magicalcoder.youyamvc.app.model.School;
import org.springframework.stereotype.Component;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.magicalcoder.youyamvc.core.common.utils.copy.CopyerSpringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@Component("schoolService")
public class SchoolServiceImpl implements SchoolService{
    @Resource(name="schoolDao")
    private SchoolDao schoolDao;

    @Override
    public School getSchool(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        return schoolDao.getSchool(query);
    }

    @Override
    public School selectOneSchoolWillThrowException(Map<String, Object> query){
        return schoolDao.getSchool(query);
    }

    @Override
    public List<School> getSchoolList(Map<String, Object> query) {
        return schoolDao.getSchoolList(query);
    }

    @Override
    public Integer getSchoolListCount(Map<String, Object> query) {
        return schoolDao.getSchoolListCount(query);
    }

    @Override
    public Long insertSchool(School entity) {
        //校验
        return schoolDao.insertSchool(entity);
    }

    @Override
    public void updateSchool(School entity) {
        //校验
        schoolDao.updateSchool(entity);
    }

    @Override
    public void updateSchoolByWhereSql(Map<String,Object> entity,String whereSql) {
        if(entity==null ||entity.isEmpty()){
            throw new RuntimeException("entity不能为空");
        }
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        entity.put("whereSql",whereSql);
        //校验
        schoolDao.updateSchoolByWhereSql(entity);
    }

    @Override
    public void deleteSchool(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        schoolDao.deleteSchool(query);
    }

    @Override
    public void deleteSchoolByWhereSql(String whereSql) {
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("whereSql", whereSql);
        schoolDao.deleteSchoolByWhereSql(query);
    }


    @Override
    public void truncateSchool() {
        schoolDao.truncateSchool();
    }

    @Override
    public void batchInsertSchool(List<School> list) {
        //校验
        schoolDao.batchInsertSchool(list);
    }

    @Override
    public void batchUpdateSchool(List<School> list) {
        //校验
        schoolDao.batchUpdateSchool(list);
    }

    @Override
    public void batchDeleteSchool(List<Long> idList) {
        schoolDao.batchDeleteSchool(idList);
    }


    @Transactional
    @Override
    public void transactionImportJsonList(List<School> list) {
        if(list!=null && list.size()>0){
            for(School school : list){
                if (school.getId() == null) {
                    insertSchool(school);
                } else {
                    School entity = getSchool(school.getId());
                    if(entity==null){
                        insertSchool(school);
                    }else {
                        CopyerSpringUtil.copyProperties(school, entity);
                        updateSchool(entity);
                    }
                }
            }
        }
    }

}
