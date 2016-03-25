package com.magicalcoder.youyamvc.web.controller.admin.classteacher;
import com.magicalcoder.youyamvc.app.classteacher.service.ClassTeacherService;
import com.magicalcoder.youyamvc.app.classteacher.constant.ClassTeacherConstant;
import com.magicalcoder.youyamvc.app.model.ClassTeacher;
import com.magicalcoder.youyamvc.core.common.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import com.magicalcoder.youyamvc.app.dto.InputSelectShowDto;
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
import javax.servlet.http.HttpServletRequest;
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
        ajaxData.put("pageList", dealForeignField(pageList));
        ajaxData.put("pageCount", pageCount);
        toJson(response, new AjaxData("ok", "success", ajaxData));
    }

//处理外键显示字段 而不是难读懂的关联字段
    private List<Map<String,Object>> dealForeignField(List<ClassTeacher> pageList){
        List<Map<String,Object>> newPageList = new ArrayList<Map<String, Object>>(pageList.size());
        if(ListUtils.isNotBlank(pageList)){
        //step1 转化map快速索引
            //处理classId外键
            StringBuffer classIds = new StringBuffer();
            for(ClassTeacher item : pageList){
                if(!classIds.toString().contains(","+item.getClassId()+",")){
                         classIds.append(item.getClassId()).append(",");
                }
            }

            List<Classes> classesList = classesService.getClassesList(
                ProjectUtil.buildMap("whereSql","and id in ("+StringUtils.deleteLastChar(classIds.toString())+")"));
            Map<Long,Classes> classesMap = new HashMap<Long, Classes>();
            if(ListUtils.isNotBlank(classesList)){
                for(Classes entity:classesList){
                    classesMap.put(entity.getId(),entity);
                }
            }
            //处理teacherId外键
            StringBuffer teacherIds = new StringBuffer();
            for(ClassTeacher item : pageList){
                if(!teacherIds.toString().contains(","+item.getTeacherId()+",")){
                         teacherIds.append(item.getTeacherId()).append(",");
                }
            }

            List<Teacher> teacherList = teacherService.getTeacherList(
                ProjectUtil.buildMap("whereSql","and id in ("+StringUtils.deleteLastChar(teacherIds.toString())+")"));
            Map<Long,Teacher> teacherMap = new HashMap<Long, Teacher>();
            if(ListUtils.isNotBlank(teacherList)){
                for(Teacher entity:teacherList){
                    teacherMap.put(entity.getId(),entity);
                }
            }

            //使用索引替换外键展示值
            for(ClassTeacher item:pageList){
                String json = JSON.toJSONString(item);
                Map<String,Object> obj = (Map<String,Object>)JSON.parse(json);
                Long classId = item.getClassId();
                Classes classes = classesMap.get(classId);
                String classIdForeignShowValue = classes==null?"":""+classes.getClassName();
                obj.put("classIdForeignShowValue",classIdForeignShowValue);
                Long teacherId = item.getTeacherId();
                Teacher teacher = teacherMap.get(teacherId);
                String teacherIdForeignShowValue = teacher==null?"":""+teacher.getTeacherName()+"-"+teacher.getAge();
                obj.put("teacherIdForeignShowValue",teacherIdForeignShowValue);
                newPageList.add(obj);
            }
        }
        return newPageList;
    }

    //新增
    @RequestMapping({"/detail"})
    public String detail(ModelMap model) {
        model.addAttribute("classTeacher", new ClassTeacher());
        return "admin/classteacher/classTeacherDetail";
    }
    //根据主键到编辑
    @RequestMapping({"/detail/{id}"})
    public String detailId(@PathVariable Long id, ModelMap model) {
        ClassTeacher entity = this.classTeacherService.getClassTeacher(id);
        model.addAttribute("classTeacher", entity);
        foreignModel(entity,model);
        return "admin/classteacher/classTeacherDetail";
    }
    //根据自定义查询条件到编辑
    @RequestMapping({"/detail_param"})
    public String detailId(HttpServletRequest request,ModelMap model) {
        Map<String,Object> reqMap = ProjectUtil.getParams(request);
        ClassTeacher entity = this.classTeacherService.selectOneClassTeacherWillThrowException(reqMap);
        model.addAttribute("classTeacher", entity);
        foreignModel(entity,model);
        return "admin/classteacher/classTeacherDetail";
    }
    private void foreignModel(ClassTeacher entity,ModelMap model){
        Map<String,Object> map = null;
            map = ProjectUtil.buildMap("id",entity.getClassId());
                Classes classes= classesService.selectOneClassesWillThrowException(map);
            model.addAttribute("classes",classes);
            map = ProjectUtil.buildMap("id",entity.getTeacherId());
                Teacher teacher= teacherService.selectOneTeacherWillThrowException(map);
            model.addAttribute("teacher",teacher);
    }

    //保存
    @RequestMapping(value="save", method={RequestMethod.POST})
    public String save(@ModelAttribute ClassTeacher classTeacher,ModelMap model) {
        try{
            model.addAttribute("classTeacher",classTeacher);
            foreignModel(classTeacher,model);
            saveEntity(classTeacher);
        }catch (Exception e){
            String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
            model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
            return "admin/classteacher/classTeacherDetail";
        }
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

//===================搜索下拉框 外键查询使用begin=================================
    @RequestMapping(value = "type_ahead_search",method = RequestMethod.GET)
    public void typeAheadSearch(@RequestParam(value = "keyword",required = false) String keyword,
        @RequestParam(value = "foreignJavaField",required = false) String foreignJavaField,
        @RequestParam(value = "selectValue",required = false) String selectValue,
        HttpServletResponse response){

        if(StringUtils.isBlank(selectValue)){
            StringBuffer sb = new StringBuffer();
            selectValue = StringUtils.deleteLastChar(sb.toString());
        }
        List<ClassTeacher> list = new ArrayList<ClassTeacher>();
        Map<String,Object> query = null;
        if(StringUtils.isBlank(keyword)){
            query = ProjectUtil.buildMap("limitIndex",0,"limit", 20);
            list = this.classTeacherService.getClassTeacherList(query);
            toSimpleJson(response,showList(list,selectValue,foreignJavaField));

        }else{
            boolean stopSearch = false;//逐一尝试关键词匹配
            boolean toSimpleJson = false;//如果最终没查询到数据 则输出默认数据
            if(ProjectUtil.isNum(keyword)){
            if(!stopSearch){
                list = searchList("classIdFirst",keyword);
                if(ListUtils.isNotBlank(list)){
                    stopSearch = true;
                }
            }
            if(stopSearch){
                toSimpleJson(response,showList(list,selectValue,foreignJavaField));
                toSimpleJson = true;
            }
            }

            if(ProjectUtil.isNum(keyword)){
            if(!stopSearch){
                list = searchList("teacherIdFirst",keyword);
                if(ListUtils.isNotBlank(list)){
                    stopSearch = true;
                }
            }
            if(stopSearch){
                toSimpleJson(response,showList(list,selectValue,foreignJavaField));
                toSimpleJson = true;
            }
            }

            if(!toSimpleJson){
                toSimpleJson(response,showList(list,selectValue,foreignJavaField));
            }
        }
    }
    private List<ClassTeacher> searchList(String field,String keyword){
        List<ClassTeacher> list = this.classTeacherService.getClassTeacherList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = this.classTeacherService.getClassTeacherList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }

    private List<InputSelectShowDto> showList(List<ClassTeacher> list,String selectValue,String foreignJavaField){
        if(ListUtils.isNotBlank(list)){
            List<InputSelectShowDto> showList = new ArrayList<InputSelectShowDto>();
            showList.add(new InputSelectShowDto("全部",null));
            for(ClassTeacher entity:list){
                String showValue = ProjectUtil.reflectShowValue(selectValue,entity);
                Object hiddenId = ProjectUtil.reflectValue(foreignJavaField,entity);
                InputSelectShowDto dto = new InputSelectShowDto(showValue,hiddenId);
                showList.add(dto);
            }
            return showList;
        }
        return new ArrayList<InputSelectShowDto>();
    }
    //===================end=================================

}
