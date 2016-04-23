package com.magicalcoder.youyamvc.web.controller.web.student;
import com.magicalcoder.youyamvc.app.student.service.StudentService;
import com.magicalcoder.youyamvc.app.student.constant.StudentConstant;
import com.magicalcoder.youyamvc.app.student.util.StudentUtil;
import com.magicalcoder.youyamvc.app.model.Student;
import com.magicalcoder.youyamvc.core.common.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.utils.copy.CopyerSpringUtil;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import com.magicalcoder.youyamvc.core.spring.web.WebLoginController;
import com.magicalcoder.youyamvc.web.common.BaseController;
import com.magicalcoder.youyamvc.app.userweb.util.UserWebUtil;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.*;
import java.math.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@RequestMapping({"/web/student"})
@Controller
public class StudentListController extends BaseController
{
    @Resource
    private StudentService studentService;
/**  method:GET
     *   url:/user/student/list/{pageIndex}/{pageSize}?...
     *   demo:/user/student/list/1/20
     *   获取学生分页数据
     *   是否需要登录
     *是否必须   入参                          注释
     *  是   @pageIndex                     (Integer)当前页码 [1,n]
     *  是   @pageSize                      (Integer)每页条数 [1,n]
     *  否   @pageCount                     (Integer)页数 如果传进来将会优化性能 对于需要分页的数据 请求第二页的时候可以把上一页的值传进来
     *  否   @needPageCount                 (Boolean)是否需要返回pageCount
     *  否   @callback                      (String)回调方法
     *  否   @encode                        (String)编码 默认UTF-8
     *  否   @classId                     (Long)所属班级 
     *  否   @sex                     (Integer)性别 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
     *  否   @name                     (String)学生名称 
     *  否   @identyKey                     (Long)主键值 
     *  否   @classIdFirst                     (Long)所属班级 
     *  否   @sexFirst                     (Integer)性别 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
     *  否   @nameFirst                     (String)学生名称 
     *  否   @orderBySqlField                     (String)排序字段 class_id|sex|name|identy_key|
     *  否   @descAsc                             (String)desc|asc
     *   返回
     *   {
     *      code:0,message:"",jsonp:"",
     *      info:
                pageCount://总数目
                pageList:[{
     *              classId         (Long)所属班级 
     *              sex         (Integer)性别 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
     *              name         (String)学生名称 
     *              identyKey         (Long)主键值 
     *      }]
     *   }
     */
    @RequestMapping(value={"list/{pageIndex}/{pageSize}"}, method={RequestMethod.GET})
    public void list(@PathVariable Integer pageIndex,@PathVariable Integer pageSize,
        @RequestParam(required=false, value="orderBySqlField") String orderBySqlField,
        @RequestParam(required=false, value="descAsc") String descAsc,
        @RequestParam(required=false, value="pageCount") Integer pageCount,
        @RequestParam(required=false, value="needPageCount") Boolean needPageCount,
        @RequestParam(required=false, value="callback") String callback,
        @RequestParam(required=false, value="encode") String encode,
        @RequestParam(required = false,value ="classId")   Long classId,
        @RequestParam(required = false,value ="sex")   Integer sex,
        @RequestParam(required = false,value ="name")   String name,
        @RequestParam(required = false,value ="identyKey")   Long identyKey,
        @RequestParam(required = false,value ="classIdFirst")                        Long classIdFirst ,
        @RequestParam(required = false,value ="sexFirst")                        Integer sexFirst ,
        @RequestParam(required = false,value ="nameFirst")                        String nameFirst ,
        HttpServletRequest request,HttpServletResponse response)
    {
        String orderBy = StudentUtil.filterOrderBy(orderBySqlField,descAsc);
        pageSize = Math.min(StudentConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;
     Long userId = UserWebUtil.userId(request);


     Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
         "classId",classId,
         "sex",sex,
         "name",name,
         "identyKey",identyKey,
                "classIdFirst",classIdFirst ,
                "sexFirst",sexFirst ,
                "nameFirst",nameFirst ,
        "limitIndex",idx,"limit", pageSize ,"userId",userId});

        List<Student> pageList = this.studentService.getStudentList(query);
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        if(needPageCount!=null && needPageCount){
            if (pageCount == null || pageCount.intValue() == 0) {
                query.remove("orderBy");
                query.remove("limitIndex");
                query.remove("limit");
                pageCount = this.studentService.getStudentListCount(query);
            }
            ajaxData.put("pageCount", pageCount);
        }
        toWebSuccessJson(response, callback,encode,ajaxData);
    }
    private Student getEntity(Long userId,Long identyKey){
        return studentService.selectOneStudentWillThrowException(ProjectUtil.buildMap("identyKey",identyKey,"userId",userId));
    }
    private Map<String,Object> toMap(Student entity){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("classId",entity.getClassId());
        map.put("sex",entity.getSex());
        map.put("name",entity.getName());
        map.put("identyKey",entity.getIdentyKey());
        return map;
    }
    /** method:GET
    *   url:/user/student/get/{identyKey}
    *   demo:/user/student/get/1
    *   根据主键获取学生
    *是否必须         入参                              注释
    *是       @identyKey               (Long)主键
    *
    *   返回
    *   {
    *      code:0,message:"",jsonp:"",
    *      info:{
    *              classId         (Long)所属班级 
    *              sex         (Integer)性别 [{"":"全部"},{"0":"类型一"},{"1":"类型二"}]
    *              name         (String)学生名称 
    *              identyKey         (Long)主键值 
    *      }
    *   }
    */
    @RequestMapping(value = "/get/{identyKey}",method = RequestMethod.GET)
    public void get(@PathVariable Long identyKey,
        HttpServletRequest request,HttpServletResponse response){
        Long userId = UserWebUtil.userId(request);
        Student entity = studentService.selectOneStudentWillThrowException(ProjectUtil.buildMap("identyKey",identyKey,"userId",userId));
        toWebSuccessJson(response, toMap(entity));
    }

    @RequestMapping(value = "/testvm",method = RequestMethod.GET)
    public String testVm(ModelMap modelMap){
        modelMap.addAttribute("name","");
        modelMap.addAttribute("StringUtils", StringUtils.class);
        return "web/student.vm";
    }
    @RequestMapping(value = "/testjsp",method = RequestMethod.GET)
    public String testJsp(ModelMap modelMap){
        modelMap.addAttribute("name","");
        modelMap.addAttribute("StringUtils",StringUtils.class);
        return "student";
    }
}