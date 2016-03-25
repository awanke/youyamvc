package com.magicalcoder.youyamvc.web.controller.admin.classes;
import com.magicalcoder.youyamvc.app.classes.service.ClassesService;
import com.magicalcoder.youyamvc.app.classes.constant.ClassesConstant;
import com.magicalcoder.youyamvc.app.model.Classes;
import com.magicalcoder.youyamvc.core.common.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import com.magicalcoder.youyamvc.app.dto.InputSelectShowDto;
import com.magicalcoder.youyamvc.app.model.School;
import com.magicalcoder.youyamvc.app.school.service.SchoolService;
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
@RequestMapping({"/admin/classes"})
@Controller
public class AdminClassesListController extends AdminLoginController
{

    @Resource
    private SchoolService schoolService;

    @Resource
    private ClassesService classesService;

    //列表页
    @RequestMapping(value={"list"}, method={RequestMethod.GET})
    public String list(ModelMap model, HttpServletResponse response)
    {
        List<School> schoolList =
                    schoolService.getSchoolList(
                ProjectUtil.buildMap(
                    "limitIndex",0,"limit",1000
                ));
        model.addAttribute("schoolList", schoolList);
        return "admin/classes/classesList";
    }

    private String filterOrderBy(String orderBySqlField,String descAsc){
        String orderBy = null;
        //排序部分防sql注入安全过滤
        if(StringUtils.isNotBlank(descAsc)){
            orderBySqlField = orderBySqlField.toLowerCase().trim();
            descAsc=descAsc.toLowerCase().trim();
            if("asc".equals(descAsc) || "desc".equals(descAsc)){
                String orderBySqlFieldStr = ",class_name,student_count,school_id,";
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
                @RequestParam(required = false,value ="classNameFirst")                        String classNameFirst ,
                @RequestParam(required = false,value ="schoolIdFirst")                        Long schoolIdFirst ,
                @RequestParam(required = false,value ="schoolNameSchoolFirst")                        String schoolNameSchoolFirst ,
          HttpServletResponse response)
    {
        String orderBy = filterOrderBy(orderBySqlField,descAsc);
        pageSize = Math.min(ClassesConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;

        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "classNameFirst",classNameFirst ,
                "schoolIdFirst",schoolIdFirst ,
                    "schoolNameSchoolFirst" ,schoolNameSchoolFirst ,
        "limitIndex",idx,"limit", pageSize });

        boolean useRelateQuery = false;
        if(schoolNameSchoolFirst != null){
            useRelateQuery = true;
        }
        List pageList;
        if(useRelateQuery){
            pageList =this.classesService.getClassesOneToOneRelateList(query);
        }else{
            pageList = this.classesService.getClassesList(query);
        }
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
            if(useRelateQuery){
                pageCount = this.classesService.getClassesOneToOneRelateListCount(query);
            }else{
                pageCount = this.classesService.getClassesListCount(query);
            }
        }

        Map ajaxData = new HashMap();
        ajaxData.put("pageList", dealForeignField(pageList));
        ajaxData.put("pageCount", pageCount);
        toJson(response, new AjaxData("ok", "success", ajaxData));
    }

//处理外键显示字段 而不是难读懂的关联字段
    private List<Map<String,Object>> dealForeignField(List<Classes> pageList){
        List<Map<String,Object>> newPageList = new ArrayList<Map<String, Object>>(pageList.size());
        if(ListUtils.isNotBlank(pageList)){
        //step1 转化map快速索引
            //处理schoolId外键
            StringBuffer schoolIds = new StringBuffer();
            for(Classes item : pageList){
                if(!schoolIds.toString().contains(","+item.getSchoolId()+",")){
                         schoolIds.append(item.getSchoolId()).append(",");
                }
            }

            List<School> schoolList = schoolService.getSchoolList(
                ProjectUtil.buildMap("whereSql","and id in ("+StringUtils.deleteLastChar(schoolIds.toString())+")"));
            Map<Long,School> schoolMap = new HashMap<Long, School>();
            if(ListUtils.isNotBlank(schoolList)){
                for(School entity:schoolList){
                    schoolMap.put(entity.getId(),entity);
                }
            }

            //使用索引替换外键展示值
            for(Classes item:pageList){
                String json = JSON.toJSONString(item);
                Map<String,Object> obj = (Map<String,Object>)JSON.parse(json);
                Long schoolId = item.getSchoolId();
                School school = schoolMap.get(schoolId);
                String schoolIdForeignShowValue = school==null?"":""+school.getSchoolName()+"-"+school.getClassCount();
                obj.put("schoolIdForeignShowValue",schoolIdForeignShowValue);
                newPageList.add(obj);
            }
        }
        return newPageList;
    }

    //新增
    @RequestMapping({"/detail"})
    public String detail(ModelMap model) {
        model.addAttribute("classes", new Classes());
        return "admin/classes/classesDetail";
    }
    //根据主键到编辑
    @RequestMapping({"/detail/{id}"})
    public String detailId(@PathVariable Long id, ModelMap model) {
        Classes entity = this.classesService.getClasses(id);
        model.addAttribute("classes", entity);
        foreignModel(entity,model);
        return "admin/classes/classesDetail";
    }
    //根据自定义查询条件到编辑
    @RequestMapping({"/detail_param"})
    public String detailId(HttpServletRequest request,ModelMap model) {
        Map<String,Object> reqMap = ProjectUtil.getParams(request);
        Classes entity = this.classesService.selectOneClassesWillThrowException(reqMap);
        model.addAttribute("classes", entity);
        foreignModel(entity,model);
        return "admin/classes/classesDetail";
    }
    private void foreignModel(Classes entity,ModelMap model){
        Map<String,Object> map = null;
            map = ProjectUtil.buildMap("id",entity.getSchoolId());
                School school= schoolService.selectOneSchoolWillThrowException(map);
            model.addAttribute("school",school);
    }

    //保存
    @RequestMapping(value="save", method={RequestMethod.POST})
    public String save(@ModelAttribute Classes classes) {
        saveEntity(classes);
        return "redirect:/admin/classes/list";
    }

    private void saveEntity(Classes classes){
        if (classes.getId() == null) {
            this.classesService.insertClasses(classes);
        } else {
            Classes entity = this.classesService.getClasses(classes.getId());
            Copyer.copy(classes, entity);
            this.classesService.updateClasses(entity);
        }
    }

    //删除
    @RequestMapping({"/delete/{id}"})
    public void delete(@PathVariable Long id, HttpServletResponse response) {
        this.classesService.deleteClasses(id);
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
                this.classesService.batchDeleteClasses(list);
                toJson(response, new AjaxData("ok", "", ""));
            }
        }else{
            toJson(response, new AjaxData("error", "没有要删除的主键", ""));
        }
    }
    //清空表结构
    @RequestMapping(value = "truncate")
    public void truncate(HttpServletResponse response) {
        this.classesService.truncateClasses();
        toWebSuccessJson(response);
    }
    //json文件导入数据
    @RequestMapping(value = "import/json")
    public void importJson(@RequestParam MultipartFile myfiles,HttpServletResponse response) throws IOException {
        if(myfiles.getOriginalFilename().endsWith(".txt") ||
            myfiles.getOriginalFilename().endsWith(".js")||
            myfiles.getOriginalFilename().endsWith(".json")){
            String fileContent = FileHelper.fastReadFileUTF8(myfiles.getInputStream());
            List<Classes> list = SerializerFastJsonUtil.get().readJsonList(fileContent,Classes.class);
            if(ListUtils.isNotBlank(list)){
                this.classesService.transactionImportJsonList(list);
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
                @RequestParam(required = false,value ="classNameFirst")                        String classNameFirst ,
                @RequestParam(required = false,value ="schoolIdFirst")                        Long schoolIdFirst ,
                @RequestParam(required = false,value ="schoolNameSchoolFirst")                        String schoolNameSchoolFirst ,
        HttpServletResponse response){
        String orderBy = filterOrderBy(orderBySqlField,descAsc);
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "classNameFirst",classNameFirst ,
                "schoolIdFirst",schoolIdFirst ,
                    "schoolNameSchoolFirst" ,schoolNameSchoolFirst ,
        "limitIndex",start,"limit", limit });

        boolean useRelateQuery = false;
        if(schoolNameSchoolFirst != null){
            useRelateQuery = true;
        }
        List pageList;
        if(useRelateQuery){
            pageList =this.classesService.getClassesOneToOneRelateList(query);
        }else{
            pageList = this.classesService.getClassesList(query);
        }

        String file = "classes";
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


//===================搜索下拉框 外键查询使用begin=================================
    @RequestMapping(value = "type_ahead_search",method = RequestMethod.GET)
    public void typeAheadSearch(@RequestParam(value = "keyword",required = false) String keyword,
        @RequestParam(value = "foreignJavaField",required = false) String foreignJavaField,
        @RequestParam(value = "selectValue",required = false) String selectValue,
        HttpServletResponse response){

        if(StringUtils.isBlank(selectValue)){
            StringBuffer sb = new StringBuffer();
            sb.append("className").append(",");
            selectValue = StringUtils.deleteLastChar(sb.toString());
        }
        List<Classes> list = new ArrayList<Classes>();
        Map<String,Object> query = null;
        if(StringUtils.isBlank(keyword)){
            query = ProjectUtil.buildMap("limitIndex",0,"limit", 20);
            list = this.classesService.getClassesList(query);
            toSimpleJson(response,showList(list,selectValue,foreignJavaField));

        }else{
            boolean stopSearch = false;//逐一尝试关键词匹配
            boolean toSimpleJson = false;//如果最终没查询到数据 则输出默认数据
            if(!stopSearch){
                list = searchList("classNameFirst",keyword);
                if(ListUtils.isNotBlank(list)){
                    stopSearch = true;
                }
            }
            if(stopSearch){
                toSimpleJson(response,showList(list,selectValue,foreignJavaField));
                toSimpleJson = true;
            }

            if(ProjectUtil.isNum(keyword)){
            if(!stopSearch){
                list = searchList("schoolIdFirst",keyword);
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
    private List<Classes> searchList(String field,String keyword){
        List<Classes> list = this.classesService.getClassesList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = this.classesService.getClassesList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }

    private List<InputSelectShowDto> showList(List<Classes> list,String selectValue,String foreignJavaField){
        if(ListUtils.isNotBlank(list)){
            List<InputSelectShowDto> showList = new ArrayList<InputSelectShowDto>();
            showList.add(new InputSelectShowDto("全部",null));
            for(Classes entity:list){
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
