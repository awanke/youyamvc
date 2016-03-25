
package com.magicalcoder.youyamvc.app.classteacher.service.impl;

import com.magicalcoder.youyamvc.app.classteacher.dao.ClassTeacherDao;
import com.magicalcoder.youyamvc.app.classteacher.service.ClassTeacherService;
import com.magicalcoder.youyamvc.app.model.ClassTeacher;
import com.magicalcoder.youyamvc.app.model.defined.ClassTeacherMany;
import com.magicalcoder.youyamvc.core.common.utils.copy.CopyerSpringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@Component("classTeacherService")
public class ClassTeacherServiceImpl implements ClassTeacherService{
    @Resource(name="classTeacherDao")
    private ClassTeacherDao classTeacherDao;

    @Override
    public ClassTeacher getClassTeacher(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        return classTeacherDao.getClassTeacher(query);
    }

    @Override
    public ClassTeacher selectOneClassTeacherWillThrowException(Map<String, Object> query){
        return classTeacherDao.getClassTeacher(query);
    }

    @Override
    public List<ClassTeacher> getClassTeacherList(Map<String, Object> query) {
        return classTeacherDao.getClassTeacherList(query);
    }

    @Override
    public Integer getClassTeacherListCount(Map<String, Object> query) {
        return classTeacherDao.getClassTeacherListCount(query);
    }

    @Override
    public Long insertClassTeacher(ClassTeacher entity) {
        //校验
        return classTeacherDao.insertClassTeacher(entity);
    }

    @Override
    public void updateClassTeacher(ClassTeacher entity) {
        //校验
        classTeacherDao.updateClassTeacher(entity);
    }

    @Override
    public void updateClassTeacherByWhereSql(Map<String,Object> entity,String whereSql) {
        if(entity==null ||entity.isEmpty()){
            throw new RuntimeException("entity不能为空");
        }
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        entity.put("whereSql",whereSql);
        //校验
        classTeacherDao.updateClassTeacherByWhereSql(entity);
    }

    @Override
    public void deleteClassTeacher(Long id) {
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("id", id);
        classTeacherDao.deleteClassTeacher(query);
    }

    @Override
    public void deleteClassTeacherByWhereSql(String whereSql) {
        if(StringUtils.isBlank(whereSql)){
            throw new RuntimeException("whereSql不能为空");
        }
        Map<String,Object> query = new HashMap<String,Object>();
        query.put("whereSql", whereSql);
        classTeacherDao.deleteClassTeacherByWhereSql(query);
    }


    @Override
    public void truncateClassTeacher() {
        classTeacherDao.truncateClassTeacher();
    }

    @Override
    public void batchInsertClassTeacher(List<ClassTeacher> list) {
        //校验
        classTeacherDao.batchInsertClassTeacher(list);
    }

    @Override
    public void batchUpdateClassTeacher(List<ClassTeacher> list) {
        //校验
        classTeacherDao.batchUpdateClassTeacher(list);
    }

    @Override
    public void batchDeleteClassTeacher(List<Long> idList) {
        classTeacherDao.batchDeleteClassTeacher(idList);
    }

    @Override
    public List<ClassTeacherMany> getManyList(Map<String, Object> query) {
        return classTeacherDao.getManyList(query);
    }

    @Override
    public Integer getManyListCount(Map<String, Object> query) {
        return classTeacherDao.getManyListCount(query);
    }

    @Transactional
    @Override
    public void transactionImportJsonList(List<ClassTeacher> list) {
        if(list!=null && list.size()>0){
            for(ClassTeacher classTeacher : list){
                if (classTeacher.getId() == null) {
                    insertClassTeacher(classTeacher);
                } else {
                    ClassTeacher entity = getClassTeacher(classTeacher.getId());
                    if(entity==null){
                        insertClassTeacher(classTeacher);
                    }else {
                        CopyerSpringUtil.copyProperties(classTeacher, entity);
                        updateClassTeacher(entity);
                    }
                }
            }
        }
    }

}
