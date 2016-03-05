package com.magicalcoder.youyamvc.web.controller.admin.classes;
import com.magicalcoder.youyamvc.app.classes.service.ClassesService;
import com.magicalcoder.youyamvc.app.classes.constant.ClassesConstant;
import com.magicalcoder.youyamvc.app.model.Classes;
import com.magicalcoder.youyamvc.app.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
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
                String orderBySqlFieldStr = ",class_name,student_count,";
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
        ajaxData.put("pageList", pageList);
        ajaxData.put("pageCount", pageCount);
        toJson(response, new AjaxData("ok", "success", ajaxData));
    }
    //新增
    @RequestMapping({"/detail"})
    public String detail(ModelMap model) {
        detailDeal(null, model);
        return "admin/classes/classesDetail";
    }
    //编辑
    @RequestMapping({"/detail/{id}"})
    public String detailId(@PathVariable Long id, ModelMap model) {
        detailDeal(id, model);
        return "admin/classes/classesDetail";
    }
    private void detailDeal(Long id, ModelMap model) {
        Classes entity = new Classes();
        if (id != null) {
            entity = this.classesService.getClasses(id);
        }
        model.addAttribute("classes", entity);
        List<School> schoolList =
            schoolService.getSchoolList(
        ProjectUtil.buildMap(
            "limitIndex",0,"limit",1000
        ));
        model.addAttribute("schoolList", schoolList);
    }
    //保存
    @RequestMapping(value={"save"}, method={RequestMethod.POST})
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

        String file = "admin_user";
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

}
