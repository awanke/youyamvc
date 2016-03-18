package com.magicalcoder.youyamvc.web.controller.admin.school;
import com.magicalcoder.youyamvc.app.school.service.SchoolService;
import com.magicalcoder.youyamvc.app.school.constant.SchoolConstant;
import com.magicalcoder.youyamvc.app.model.School;
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
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
@RequestMapping({"/admin/school"})
@Controller
public class AdminSchoolListController extends AdminLoginController
{


    @Resource
    private SchoolService schoolService;

    //列表页
    @RequestMapping(value={"list"}, method={RequestMethod.GET})
    public String list(ModelMap model, HttpServletResponse response)
    {
        return "admin/school/schoolList";
    }

    private String filterOrderBy(String orderBySqlField,String descAsc){
        String orderBy = null;
        //排序部分防sql注入安全过滤
        if(StringUtils.isNotBlank(descAsc)){
            orderBySqlField = orderBySqlField.toLowerCase().trim();
            descAsc=descAsc.toLowerCase().trim();
            if("asc".equals(descAsc) || "desc".equals(descAsc)){
                String orderBySqlFieldStr = ",school_name,class_count,school_type,open,create_time,";
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
                @RequestParam(required = false,value ="schoolNameFirst")                        String schoolNameFirst ,
                @RequestParam(required = false,value ="schoolTypeFirst")                        Integer schoolTypeFirst ,
                @RequestParam(required = false,value ="openFirst")                        Boolean openFirst ,
                @RequestParam(required = false,value ="createTimeFirst")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTimeFirst ,
                @RequestParam(required = false,value ="createTimeSecond")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTimeSecond ,
          HttpServletResponse response)
    {
        String orderBy = filterOrderBy(orderBySqlField,descAsc);
        pageSize = Math.min(SchoolConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;

        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "schoolNameFirst",schoolNameFirst ,
                "schoolTypeFirst",schoolTypeFirst ,
                "openFirst",openFirst ,
                "createTimeFirst",createTimeFirst ,
                "createTimeSecond",createTimeSecond ,
        "limitIndex",idx,"limit", pageSize });

        boolean useRelateQuery = false;
        List pageList;
        pageList = this.schoolService.getSchoolList(query);
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
                pageCount = this.schoolService.getSchoolListCount(query);
        }

        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        ajaxData.put("pageCount", pageCount);
        toJson(response, new AjaxData("ok", "success", ajaxData));
    }
    //新增
    @RequestMapping({"/detail"})
    public String detail(ModelMap model) {
        model.addAttribute("school", new School());
        return "admin/school/schoolDetail";
    }
    //根据主键到编辑
    @RequestMapping({"/detail/{id}"})
    public String detailId(@PathVariable Long id, ModelMap model) {
        School entity = this.schoolService.getSchool(id);
        model.addAttribute("school", entity);
        foreignModel(entity,model);
        return "admin/school/schoolDetail";
    }
    //根据自定义查询条件到编辑
    @RequestMapping({"/detail_param"})
    public String detailId(HttpServletRequest request,ModelMap model) {
        Map<String,Object> reqMap = ProjectUtil.getParams(request);
        School entity = this.schoolService.selectOneSchoolWillThrowException(reqMap);
        model.addAttribute("school", entity);
        foreignModel(entity,model);
        return "admin/school/schoolDetail";
    }
    private void foreignModel(School entity,ModelMap model){
        Map<String,Object> map = null;
    }

    //保存
    @RequestMapping(value="save", method={RequestMethod.POST})
    public String save(@ModelAttribute School school) {
        saveEntity(school);
        return "redirect:/admin/school/list";
    }

    private void saveEntity(School school){
        if (school.getId() == null) {
            this.schoolService.insertSchool(school);
        } else {
            School entity = this.schoolService.getSchool(school.getId());
            Copyer.copy(school, entity);
            this.schoolService.updateSchool(entity);
        }
    }

    //删除
    @RequestMapping({"/delete/{id}"})
    public void delete(@PathVariable Long id, HttpServletResponse response) {
        this.schoolService.deleteSchool(id);
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
                this.schoolService.batchDeleteSchool(list);
                toJson(response, new AjaxData("ok", "", ""));
            }
        }else{
            toJson(response, new AjaxData("error", "没有要删除的主键", ""));
        }
    }
    //清空表结构
    @RequestMapping(value = "truncate")
    public void truncate(HttpServletResponse response) {
        this.schoolService.truncateSchool();
        toWebSuccessJson(response);
    }
    //json文件导入数据
    @RequestMapping(value = "import/json")
    public void importJson(@RequestParam MultipartFile myfiles,HttpServletResponse response) throws IOException {
        if(myfiles.getOriginalFilename().endsWith(".txt") ||
            myfiles.getOriginalFilename().endsWith(".js")||
            myfiles.getOriginalFilename().endsWith(".json")){
            String fileContent = FileHelper.fastReadFileUTF8(myfiles.getInputStream());
            List<School> list = SerializerFastJsonUtil.get().readJsonList(fileContent,School.class);
            if(ListUtils.isNotBlank(list)){
                this.schoolService.transactionImportJsonList(list);
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
                @RequestParam(required = false,value ="schoolNameFirst")                        String schoolNameFirst ,
                @RequestParam(required = false,value ="schoolTypeFirst")                        Integer schoolTypeFirst ,
                @RequestParam(required = false,value ="openFirst")                        Boolean openFirst ,
                @RequestParam(required = false,value ="createTimeFirst")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTimeFirst ,
                @RequestParam(required = false,value ="createTimeSecond")                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date createTimeSecond ,
        HttpServletResponse response){
        String orderBy = filterOrderBy(orderBySqlField,descAsc);
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "schoolNameFirst",schoolNameFirst ,
                "schoolTypeFirst",schoolTypeFirst ,
                "openFirst",openFirst ,
                "createTimeFirst",createTimeFirst ,
                "createTimeSecond",createTimeSecond ,
        "limitIndex",start,"limit", limit });

        boolean useRelateQuery = false;
        List pageList;
            pageList = this.schoolService.getSchoolList(query);

        String file = "school";
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
        List<School> list = new ArrayList<School>();
        Map<String,Object> query = null;
        if(StringUtils.isBlank(keyword)){
            query = ProjectUtil.buildMap("limitIndex",0,"limit", 20);
            list = this.schoolService.getSchoolList(query);
            toSimpleJson(response,showList(list,selectValue,foreignJavaField));

        }else{
            boolean stopSearch = false;//逐一尝试关键词匹配
            boolean toSimpleJson = false;//如果最终没查询到数据 则输出默认数据
            if(!stopSearch){
                list = searchList("schoolNameFirst",keyword);
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
                list = searchList("schoolTypeFirst",keyword);
                if(ListUtils.isNotBlank(list)){
                    stopSearch = true;
                }
            }
            if(stopSearch){
                toSimpleJson(response,showList(list,selectValue,foreignJavaField));
                toSimpleJson = true;
            }
            }

            if(!stopSearch){
                list = searchList("openFirst",keyword);
                if(ListUtils.isNotBlank(list)){
                    stopSearch = true;
                }
            }
            if(stopSearch){
                toSimpleJson(response,showList(list,selectValue,foreignJavaField));
                toSimpleJson = true;
            }

            if(!stopSearch){
                list = searchList("createTimeFirst",keyword);
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
    private List<School> searchList(String field,String keyword){
        List<School> list = this.schoolService.getSchoolList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = this.schoolService.getSchoolList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }

    private List<InputSelectShowDto> showList(List<School> list,String selectValue,String foreignJavaField){
        if(ListUtils.isNotBlank(list)){
            List<InputSelectShowDto> showList = new ArrayList<InputSelectShowDto>();
            showList.add(new InputSelectShowDto("全部",null));
            for(School entity:list){
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
