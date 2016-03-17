package com.magicalcoder.youyamvc.web.controller.admin.teacher;
import com.magicalcoder.youyamvc.app.teacher.service.TeacherService;
import com.magicalcoder.youyamvc.app.teacher.constant.TeacherConstant;
import com.magicalcoder.youyamvc.app.model.Teacher;
import com.magicalcoder.youyamvc.app.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import com.magicalcoder.youyamvc.app.dto.InputSelectShowDto;
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
@RequestMapping({"/admin/teacher"})
@Controller
public class AdminTeacherListController extends AdminLoginController
{


    @Resource
    private TeacherService teacherService;

    //列表页
    @RequestMapping(value={"list"}, method={RequestMethod.GET})
    public String list(ModelMap model, HttpServletResponse response)
    {
        return "admin/teacher/teacherList";
    }

    private String filterOrderBy(String orderBySqlField,String descAsc){
        String orderBy = null;
        //排序部分防sql注入安全过滤
        if(StringUtils.isNotBlank(descAsc)){
            orderBySqlField = orderBySqlField.toLowerCase().trim();
            descAsc=descAsc.toLowerCase().trim();
            if("asc".equals(descAsc) || "desc".equals(descAsc)){
                String orderBySqlFieldStr = ",teacher_name,age,";
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
                @RequestParam(required = false,value ="teacherNameFirst")                        String teacherNameFirst ,
                @RequestParam(required = false,value ="ageFirst")                        Integer ageFirst ,
                @RequestParam(required = false,value ="ageSecond")                        Integer ageSecond ,
          HttpServletResponse response)
    {
        String orderBy = filterOrderBy(orderBySqlField,descAsc);
        pageSize = Math.min(TeacherConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;

        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "teacherNameFirst",teacherNameFirst ,
                "ageFirst",ageFirst ,
                "ageSecond",ageSecond ,
        "limitIndex",idx,"limit", pageSize });

        boolean useRelateQuery = false;
        List pageList;
        pageList = this.teacherService.getTeacherList(query);
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
                pageCount = this.teacherService.getTeacherListCount(query);
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
        return "admin/teacher/teacherDetail";
    }
    //编辑
    @RequestMapping({"/detail/{id}"})
    public String detailId(@PathVariable Long id, ModelMap model) {
        detailDeal(id, model);
        return "admin/teacher/teacherDetail";
    }
    private void detailDeal(Long id, ModelMap model) {
        Teacher entity = new Teacher();
        if (id != null) {
            entity = this.teacherService.getTeacher(id);
        }
        model.addAttribute("teacher", entity);
    }
    //保存
    @RequestMapping(value={"save"}, method={RequestMethod.POST})
    public String save(@ModelAttribute Teacher teacher) {
        saveEntity(teacher);
        return "redirect:/admin/teacher/list";
    }

    private void saveEntity(Teacher teacher){
        if (teacher.getId() == null) {
            this.teacherService.insertTeacher(teacher);
        } else {
            Teacher entity = this.teacherService.getTeacher(teacher.getId());
            Copyer.copy(teacher, entity);
            this.teacherService.updateTeacher(entity);
        }
    }

    //删除
    @RequestMapping({"/delete/{id}"})
    public void delete(@PathVariable Long id, HttpServletResponse response) {
        this.teacherService.deleteTeacher(id);
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
                this.teacherService.batchDeleteTeacher(list);
                toJson(response, new AjaxData("ok", "", ""));
            }
        }else{
            toJson(response, new AjaxData("error", "没有要删除的主键", ""));
        }
    }
    //清空表结构
    @RequestMapping(value = "truncate")
    public void truncate(HttpServletResponse response) {
        this.teacherService.truncateTeacher();
        toWebSuccessJson(response);
    }
    //json文件导入数据
    @RequestMapping(value = "import/json")
    public void importJson(@RequestParam MultipartFile myfiles,HttpServletResponse response) throws IOException {
        if(myfiles.getOriginalFilename().endsWith(".txt") ||
            myfiles.getOriginalFilename().endsWith(".js")||
            myfiles.getOriginalFilename().endsWith(".json")){
            String fileContent = FileHelper.fastReadFileUTF8(myfiles.getInputStream());
            List<Teacher> list = SerializerFastJsonUtil.get().readJsonList(fileContent,Teacher.class);
            if(ListUtils.isNotBlank(list)){
                this.teacherService.transactionImportJsonList(list);
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
                @RequestParam(required = false,value ="teacherNameFirst")                        String teacherNameFirst ,
                @RequestParam(required = false,value ="ageFirst")                        Integer ageFirst ,
                @RequestParam(required = false,value ="ageSecond")                        Integer ageSecond ,
        HttpServletResponse response){
        String orderBy = filterOrderBy(orderBySqlField,descAsc);
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "teacherNameFirst",teacherNameFirst ,
                "ageFirst",ageFirst ,
                "ageSecond",ageSecond ,
        "limitIndex",start,"limit", limit });

        boolean useRelateQuery = false;
        List pageList;
            pageList = this.teacherService.getTeacherList(query);

        String file = "teacher";
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
        @RequestParam(value = "selectValue",required = false) String selectValue,
        @RequestParam(value = "foreignJavaField",required = false) String foreignJavaField,
        HttpServletResponse response){
        List<Teacher> list = new ArrayList<Teacher>();
        Map<String,Object> query = null;
        if(StringUtils.isBlank(keyword)){
            query = ProjectUtil.buildMap("limitIndex",0,"limit", 20);
            list = this.teacherService.getTeacherList(query);
            toSimpleJson(response,showList(list,selectValue,foreignJavaField));

        }else{
            boolean stopSearch = false;//逐一尝试关键词匹配
            boolean toSimpleJson = false;//如果最终没查询到数据 则输出默认数据
            if(!stopSearch){
                query = ProjectUtil.buildMap(
                    "teacherNameFirst",keyword,"limitIndex",0,"limit", 20
                );
                list = this.teacherService.getTeacherList(query);
                if(ListUtils.isNotBlank(list)){
                    stopSearch = true;
                }
            }
            if(stopSearch){
                toSimpleJson(response,showList(list,selectValue,foreignJavaField));
                toSimpleJson = true;
            }
            if(!toSimpleJson){
                toSimpleJson(response,showList(list,selectValue,foreignJavaField));
            }
        }
    }

    private List<InputSelectShowDto> showList(List<Teacher> list,String selectValue,String foreignJavaField){
        if(ListUtils.isNotBlank(list)){
            List<InputSelectShowDto> showList = new ArrayList<InputSelectShowDto>();
            showList.add(new InputSelectShowDto("全部",null));
            for(Teacher entity:list){
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
