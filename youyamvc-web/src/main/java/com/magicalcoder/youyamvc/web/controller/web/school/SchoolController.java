package com.magicalcoder.youyamvc.web.controller.web.school;

import com.magicalcoder.youyamvc.app.model.School;
import com.magicalcoder.youyamvc.app.school.constant.SchoolConstant;
import com.magicalcoder.youyamvc.app.school.service.SchoolService;
import com.magicalcoder.youyamvc.app.userweb.util.UserWebUtil;
import com.magicalcoder.youyamvc.app.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.copy.CopyerSpringUtil;
import com.magicalcoder.youyamvc.core.spring.web.WebLoginController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@RequestMapping({"/user/school"})
@Controller
public class SchoolController extends WebLoginController
{

    @Resource
    private SchoolService schoolService;

    /**  method:GET
     *   url:/user/school/list/page/{pageIndex}/{pageSize}
     *   demo:/user/school/list/page/1/20
     *   获取学校分页数据
     *   入参
     *   @pageIndex 当前页码 [1,n]
     *   @pageSize  每页条数 [1,n]
     *
     *   返回
     *   {
     *      code:0,message:"",jsonp:"",
     *      info:[{
     *              id         学校主键(Long) 
     *              headImg         学校头像(String) 
     *              adress         学校地址(String) 
     *              schoolDesc         学校描述(String) 
     *              schoolName         学校名称(String) 
     *              schoolType         学校类型(Integer) [{"":"全部"},{"0":"普通"},{"1":"重点"}]
     *              open         是否开学(Boolean) [{"":"全部"},{"true":"是"},{"false":"否"}]
     *              classCount         班级个数(Integer) 
     *              createTime         创建时间(Date) 
     *      }]
     *   }
     */
    @RequestMapping(value = "/list/page/{pageIndex}/{pageSize}",method = RequestMethod.GET)
    public void page(@PathVariable Integer pageIndex,@PathVariable Integer pageSize ,
    HttpServletRequest request,HttpServletResponse response){
        Long userId = UserWebUtil.userId(request);
        int limit = Math.min(pageSize, SchoolConstant.PAGE_MAX_SIZE);
        int idx = (pageIndex-1)* SchoolConstant.PAGE_MAX_SIZE;
        List<School> pageList = schoolService.getSchoolList(ProjectUtil.buildMap(
        "userId", userId,
        "orderBy", " id desc ", "limitIndex", idx, "limit", limit));
        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
        if(ListUtils.isNotBlank(pageList)){
            for(School entity : pageList){
                mapList.add(toMap(entity));
            }
        }
        toWebSuccessJson(response,mapList);
    }

    /** method:GET
    *   url:/user/school/item/get/{id}
    *   demo:/user/school/item/get/1
    *   根据主键获取学校
    *   入参
    *   @id 主键 Long
    *
    *   返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:{
    *              id         学校主键(Long) 
    *              headImg         学校头像(String) 
    *              adress         学校地址(String) 
    *              schoolDesc         学校描述(String) 
    *              schoolName         学校名称(String) 
    *              schoolType         学校类型(Integer) [{"":"全部"},{"0":"普通"},{"1":"重点"}]
    *              open         是否开学(Boolean) [{"":"全部"},{"true":"是"},{"false":"否"}]
    *              classCount         班级个数(Integer) 
    *              createTime         创建时间(Date) 
    *      }
    *   }
    */
    @RequestMapping(value = "/item/get/{id}",method = RequestMethod.GET)
    public void get(@PathVariable Long id,
        HttpServletRequest request,HttpServletResponse response){
        Long userId = UserWebUtil.userId(request);
        School entity = schoolService.selectOneSchoolWillThrowException(ProjectUtil.buildMap("id",id,"userId",userId));
        toWebSuccessJson(response,toMap(entity));
    }

    private Map<String,Object> toMap(School entity){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",entity.getId());
        map.put("headImg",entity.getHeadImg());
        map.put("adress",entity.getAdress());
        map.put("schoolDesc",entity.getSchoolDesc());
        map.put("schoolName",entity.getSchoolName());
        map.put("schoolType",entity.getSchoolType());
        map.put("open",entity.getOpen());
        map.put("classCount",entity.getClassCount());
        map.put("createTime",entity.getCreateTime());
        return map;
    }

    /** method:GET
    *   url:/user/school/item/delete/{id}
    *   demo:/user/school/item/delete/1
    *   根据主键删除学校
    *   入参
    *   @id 主键 Long
    *
    *   返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:true||false
    *   }
    */
    @RequestMapping(value = "/item/delete/{id}",method = RequestMethod.GET)
    public void delete(@PathVariable Long id,
        HttpServletRequest request,HttpServletResponse response) {
        Long userId = UserWebUtil.userId(request);
        School entity = getEntity(userId,id);
        if(entity!=null){
            this.schoolService.deleteSchool(id);
        }
        toWebSuccessJson(response,true);
    }

    private School getEntity(Long userId,Long id){
        return schoolService.selectOneSchoolWillThrowException(ProjectUtil.buildMap("id",id,"userId",userId));
    }


    /** method:POST
    *   url:/user/school/item/save
    *   保存学校
    *   入参
    *   id         学校主键(Long)  为空表示插入否则表示更新    
    *   headImg         学校头像(String)      
    *   adress         学校地址(String)      
    *   schoolDesc         学校描述(String)      
    *   schoolName         学校名称(String)      
    *   schoolType         学校类型(Integer) [{"":"全部"},{"0":"普通"},{"1":"重点"}]     
    *   open         是否开学(Boolean) [{"":"全部"},{"true":"是"},{"false":"否"}]     
    *   classCount         班级个数(Integer)      
    *   createTime         创建时间(Date)      
    *
    *   返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:id 主键 Long
    *   }
    */
    @RequestMapping(value="save", method={RequestMethod.POST})
    public void save(@ModelAttribute School school,
        HttpServletRequest request,HttpServletResponse response) {
        Long userId = UserWebUtil.userId(request);
        Long id = saveEntity(userId,school);
        toWebSuccessJson(response,id);
    }

    private Long saveEntity(Long userId,School school){
        //school.setUserId(userId);
        if (school.getId() == null) {
            return this.schoolService.insertSchool(school);
        } else {
            School entity = getEntity(userId,school.getId());
            if(entity!=null){
                CopyerSpringUtil.copyProperties(school, entity);
                this.schoolService.updateSchool(entity);
            }
        }
        return school.getId();
    }
}
