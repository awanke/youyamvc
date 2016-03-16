package com.magicalcoder.youyamvc.web.controller.admin.classteacher;
import com.magicalcoder.youyamvc.app.classteacher.service.ClassTeacherService;
import com.magicalcoder.youyamvc.app.classteacher.constant.ClassTeacherConstant;
import com.magicalcoder.youyamvc.app.model.ClassTeacher;
import com.magicalcoder.youyamvc.app.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import com.magicalcoder.youyamvc.app.model.Classes;
import com.magicalcoder.youyamvc.app.classes.service.ClassesService;
import com.magicalcoder.youyamvc.app.model.Teacher;
import com.magicalcoder.youyamvc.app.teacher.service.TeacherService;
import java.io.File;
import java.io.IOException;
import com.alibaba.fastjson.JSON;
import com.magicalcoder.youyamvc.core.common.file.FileHelper;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.common.utils.serialize.SerializerFastJsonUtil;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.*;
import java.math.*;
import javax.annotation.Resource;
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
@RequestMapping({"/admin/class_teacher"})
@Controller
public class AdminClassTeacherListController extends AdminLoginController
{

    @Resource
    private ClassesService classesService;
    @Resource
    private TeacherService teacherService;

    @Resource
    private ClassTeacherService classTeacherService;

    //列表页
    @RequestMapping(value={"list"}, method={RequestMethod.GET})
    public String list(ModelMap model, HttpServletResponse response)
    {
        return "admin/classteacher/classTeacherList";
    }

    private String filterOrderBy(String orderBySqlField,String descAsc){
        String orderBy = null;
        //排序部分防sql注入安全过滤
        if(StringUtils.isNotBlank(descAsc)){
            orderBySqlField = orderBySqlField.toLowerCase().trim();
            descAsc=descAsc.toLowerCase().trim();
            if("asc".equals(descAsc) || "desc".equals(descAsc)){
                String orderBySqlFieldStr = ",class_id,teacher_id,";
                if(orderBySqlFieldStr.contains("" + orderBySqlField+"")){//精确匹配可排序字段
                    orderBy = orderBySqlField+" "+descAsc;
                }
            }
        }
        return orderBy;
    }
    //分页查询
    @RequestMapping(value={"page/{pageIndex}/{pageSize}/{pageCount}"}, method={RequestMethod.GET})
    public void page(@PathVariable Integer pageIndex,@PathVariable Integer pageSize, @PathVariable Integer pageCount,
        @RequestParam(required=false, value="orderBySqlField") String orderBySqlField,
        @RequestParam(required=false, value="descAsc") String descAsc,
                @RequestParam(required = false,value ="classIdFirst")                        Long classIdFirst ,
                @RequestParam(required = false,value ="teacherIdFirst")                        Long teacherIdFirst ,
          HttpServletResponse response)
    {
        String orderBy = filterOrderBy(orderBySqlField,descAsc);
        pageSize = Math.min(ClassTeacherConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;

        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "classIdFirst",classIdFirst ,
                "teacherIdFirst",teacherIdFirst ,
        "limitIndex",idx,"limit", pageSize });

        boolean useRelateQuery = false;
        List pageList;
        pageList = this.classTeacherService.getClassTeacherList(query);
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
                pageCount = this.classTeacherService.getClassTeacherListCount(query);
        }

        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        ajaxData.put("pageCount", pageCount);
        toJson(response, new AjaxData("ok", "success", ajaxData));
    }
    //新增
    @RequestMapping({"/detail"})
    public String detail(ModelMap model) {
        detailDeal(null, model);
        return "admin/classteacher/classTeacherDetail";
    }
    //编辑
    @RequestMapping({"/detail/{id}"})
    public String detailId(@PathVariable Long id, ModelMap model) {
        detailDeal(id, model);
        return "admin/classteacher/classTeacherDetail";
    }
    private void detailDeal(Long id, ModelMap model) {
        ClassTeacher entity = new ClassTeacher();
        if (id != null) {
            entity = this.classTeacherService.getClassTeacher(id);
            Classes classes= classesService.getClasses(entity.getClassId());
            model.addAttribute("classes",classes);
            Teacher teacher= teacherService.getTeacher(entity.getTeacherId());
            model.addAttribute("teacher",teacher);
        }
        model.addAttribute("classTeacher", entity);

    }
    //保存
    @RequestMapping(value={"save"}, method={RequestMethod.POST})
    public String save(@ModelAttribute ClassTeacher classTeacher) {
        saveEntity(classTeacher);
        return "redirect:/admin/class_teacher/list";
    }

    private void saveEntity(ClassTeacher classTeacher){
        if (classTeacher.getId() == null) {
            this.classTeacherService.insertClassTeacher(classTeacher);
        } else {
            ClassTeacher entity = this.classTeacherService.getClassTeacher(classTeacher.getId());
            Copyer.copy(classTeacher, entity);
            this.classTeacherService.updateClassTeacher(entity);
        }
    }

    //删除
    @RequestMapping({"/delete/{id}"})
    public void delete(@PathVariable Long id, HttpServletResponse response) {
        this.classTeacherService.deleteClassTeacher(id);
        toJson(response, new AjaxData("ok", "", ""));
    }
    //批量删除
    @RequestMapping({"/batchdelete/{ids}"})
    public void batchDelete(@PathVariable String ids, HttpServletResponse response) {
        if(StringUtils.isNotBlank(ids)){
            String[] idArr = ids.split(",");
            List<Long> list = new ArrayList<Long>();
            for(String id:idArr){
                if(StringUtils.isNotBlank(id)){
                    list.add(Long.valueOf(id));
                }
            }
            if(ListUtils.isNotBlank(list)){
                this.classTeacherService.batchDeleteClassTeacher(list);
                toJson(response, new AjaxData("ok", "", ""));
            }
        }else{
            toJson(response, new AjaxData("error", "没有要删除的主键", ""));
        }
    }
    //清空表结构
    @RequestMapping(value = "truncate")
    public void truncate(HttpServletResponse response) {
        this.classTeacherService.truncateClassTeacher();
        toWebSuccessJson(response);
    }
    //json文件导入数据
    @RequestMapping(value = "import/json")
    public void importJson(@RequestParam MultipartFile myfiles,HttpServletResponse response) throws IOException {
        if(myfiles.getOriginalFilename().endsWith(".txt") ||
            myfiles.getOriginalFilename().endsWith(".js")||
            myfiles.getOriginalFilename().endsWith(".json")){
            String fileContent = FileHelper.fastReadFileUTF8(myfiles.getInputStream());
            List<ClassTeacher> list = SerializerFastJsonUtil.get().readJsonList(fileContent,ClassTeacher.class);
            if(ListUtils.isNotBlank(list)){
                this.classTeacherService.transactionImportJsonList(list);
            }
            toWebSuccessJson(response);
        }else {
            toWebFailureJson(response, "不支持的文件后缀");
        }
    }
    //json文件导出
    @RequestMapping(value = "export/json/{start}/{limit}",method = RequestMethod.GET)
    public void exportJson(@PathVariable Integer start,@PathVariable Integer limit,
        @RequestParam(required=false, value="orderBySqlField") String orderBySqlField,
        @RequestParam(required=false, value="descAsc") String descAsc,
                @RequestParam(required = false,value ="classIdFirst")                        Long classIdFirst ,
                @RequestParam(required = false,value ="teacherIdFirst")                        Long teacherIdFirst ,
        HttpServletResponse response){
        String orderBy = filterOrderBy(orderBySqlField,descAsc);
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "classIdFirst",classIdFirst ,
                "teacherIdFirst",teacherIdFirst ,
        "limitIndex",start,"limit", limit });

        boolean useRelateQuery = false;
        List pageList;
            pageList = this.classTeacherService.getClassTeacherList(query);

        String file = "class_teacher";
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile(file,".txt");
            String json = JSON.toJSONString(pageList, true);
            FileHelper.fastWriteFileUTF8(tmpFile, json);
            toFile(response, tmpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(tmpFile!=null){
                tmpFile.delete();
            }
        }
    }

    @RequestMapping(value = "relate/{classId}")
    public String relate(@PathVariable Long classId,ModelMap modelMap){
        modelMap.addAttribute("classId",classId);
        return "admin/classteacher/relate";
    }

    //详情页的关联分页功能
    @RequestMapping(value={"page_relate/{pageIndex}/{pageCount}"}, method={RequestMethod.GET})
    public void page(@PathVariable Integer pageIndex, @PathVariable Integer pageCount,
    @RequestParam(required=false, value="orderBy") String orderBy,
                @RequestParam(required = false,value ="teacherNameFirst")
                        String teacherNameFirst ,
                @RequestParam(required = false,value ="ageFirst")
                        Integer ageFirst ,
                @RequestParam(required = false,value ="ageSecond")
                        Integer ageSecond ,
    @RequestParam(required = false,value ="classId") Long classId ,
    HttpServletResponse response)
    {
        if(StringUtils.isBlank(orderBy)){
            orderBy = "classTeacher.teacher_id desc";
        }else{
            orderBy += ",classTeacher.teacher_id desc";
        }
        int idx = (pageIndex.intValue() - 1) * 20;
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "teacherNameFirst",teacherNameFirst ,
                "ageFirst",ageFirst ,
                "ageSecond",ageSecond ,
        "limitIndex",Integer.valueOf(idx),"limit", Integer.valueOf(20) });
        query.put("classId",classId );

        List    pageList = this.classTeacherService.getManyList(query);
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
            pageCount = this.classTeacherService.getManyListCount(query);
        }
        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        ajaxData.put("pageCount", pageCount);
        toJson(response, new AjaxData("ok", "success", ajaxData));
    }

    //添加关联
    @RequestMapping(value={"/add_relate/{classId}/{teacherId}"}, method={RequestMethod.GET})
    public void addRelate(@PathVariable Long classId,
    @PathVariable Long teacherId,
    HttpServletResponse response) {
        ClassTeacher entity = new ClassTeacher();
        entity.setClassId(classId);
        entity.setTeacherId(teacherId);
        classTeacherService.insertClassTeacher(entity);
        toJson(response, new AjaxData("ok", "", ""));
    }

    //删除关联
    @RequestMapping(value={"/delete_relate/{classId}/{teacherId}"}, method={RequestMethod.GET})
    public void deleteRelate(@PathVariable Long classId,
    @PathVariable Long teacherId,
    HttpServletResponse response) {
        StringBuffer whereSql = new StringBuffer();
        whereSql.append("class_id=").append(classId).append(" and teacher_id=").append(teacherId);
        this.classTeacherService.deleteClassTeacherByWhereSql(whereSql.toString());
        toJson(response, new AjaxData("ok", "", ""));
    }

    //搜索下拉框 外键查询使用
    @RequestMapping(value = "type_ahead_search",method = RequestMethod.GET)
    public void typeAheadSearch(@RequestParam(value = "keyword",required = false) String keyword,HttpServletResponse response){
        if(StringUtils.isBlank(keyword)){
            keyword=null;
        }
        List<ClassTeacher> list = new ArrayList<ClassTeacher>();
        Map<String,Object> query = null;
        boolean stopSearch = false;
        boolean toSimpleJson = false;
        if(!toSimpleJson){
            toSimpleJson(response,list);
        }
    }

}
