package com.magicalcoder.youyamvc.web.controller.admin.student;
import com.magicalcoder.youyamvc.app.student.service.StudentService;
import com.magicalcoder.youyamvc.app.student.constant.StudentConstant;
import com.magicalcoder.youyamvc.app.student.dto.StudentDto;
import com.magicalcoder.youyamvc.app.model.Student;
import com.magicalcoder.youyamvc.core.common.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import com.magicalcoder.youyamvc.app.dto.InputSelectShowDto;
import com.magicalcoder.youyamvc.app.model.Classes;
import com.magicalcoder.youyamvc.app.classes.service.ClassesService;
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
@RequestMapping({"/admin/student"})
@Controller
public class AdminStudentListController extends AdminLoginController
{

    @Resource
    private ClassesService classesService;

    @Resource
    private StudentService studentService;

    //列表页
    @RequestMapping(value={"list"}, method={RequestMethod.GET})
    public String list(ModelMap model, HttpServletResponse response)
    {
        List<Classes> classesList =
                    classesService.getClassesList(
                ProjectUtil.buildMap(
                    "limitIndex",0,"limit",1000
                ));
        model.addAttribute("classesList", classesList);
        return "admin/student/studentList";
    }

    private String filterOrderBy(String orderBySqlField,String descAsc){
        String orderBy = null;
        //排序部分防sql注入安全过滤
        if(StringUtils.isNotBlank(descAsc)){
            orderBySqlField = orderBySqlField.toLowerCase().trim();
            descAsc=descAsc.toLowerCase().trim();
            if("asc".equals(descAsc) || "desc".equals(descAsc)){
                String orderBySqlFieldStr = ",class_id,sex,name,";
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
                @RequestParam(required = false,value ="sexFirst")                        Integer sexFirst ,
                @RequestParam(required = false,value ="nameFirst")                        String nameFirst ,
          HttpServletResponse response)
    {
        String orderBy = filterOrderBy(orderBySqlField,descAsc);
        pageSize = Math.min(StudentConstant.PAGE_MAX_SIZE,pageSize);
        int idx = (pageIndex.intValue() - 1) * pageSize;

        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "classIdFirst",classIdFirst ,
                "sexFirst",sexFirst ,
                "nameFirst",nameFirst ,
        "limitIndex",idx,"limit", pageSize });

        boolean useRelateQuery = false;
        List pageList;
        pageList = this.studentService.getStudentList(query);
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
                pageCount = this.studentService.getStudentListCount(query);
        }

        Map ajaxData = new HashMap();
        ajaxData.put("pageList", dealForeignField(pageList));
        ajaxData.put("pageCount", pageCount);
        toJson(response, new AjaxData("ok", "success", ajaxData));
    }

//处理外键显示字段 而不是难读懂的关联字段
    private List<Map<String,Object>> dealForeignField(List<Student> pageList){
        List<Map<String,Object>> newPageList = new ArrayList<Map<String, Object>>(pageList.size());
        if(ListUtils.isNotBlank(pageList)){
        //step1 转化map快速索引
            //处理classId外键
            StringBuffer classIds = new StringBuffer();
            for(Student item : pageList){
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

            //使用索引替换外键展示值
            for(Student item:pageList){
                String json = JSON.toJSONString(item);
                Map<String,Object> obj = (Map<String,Object>)JSON.parse(json);
                Long classId = item.getClassId();
                Classes classes = classesMap.get(classId);
                String classIdForeignShowValue = classes==null?"":""+classes.getClassName();
                obj.put("classIdForeignShowValue",classIdForeignShowValue);
                newPageList.add(obj);
            }
        }
        return newPageList;
    }

    //新增
    @RequestMapping({"/detail"})
    public String detail(ModelMap model) {
        model.addAttribute("student", new Student());
        return "admin/student/studentDetail";
    }

    //根据自定义查询条件到编辑
    @RequestMapping({"/detail_param"})
    public String detailId(HttpServletRequest request,ModelMap model) {
        Map<String,Object> reqMap = ProjectUtil.getParams(request);
        Student entity = this.studentService.selectOneStudentWillThrowException(reqMap);
        model.addAttribute("student", entity);
        foreignModel(entity,model);
        return "admin/student/studentDetail";
    }
    private void foreignModel(Student entity,ModelMap model){
        Map<String,Object> map = null;
            map = ProjectUtil.buildMap("id",entity.getClassId());
                Classes classes= classesService.selectOneClassesWillThrowException(map);
            model.addAttribute("classes",classes);
    }


    //根据唯一键到编辑
    @RequestMapping({"/detailUpdate"})
    public String detailId(
        @RequestParam(required=true) String name,
        ModelMap model) {
            Student entity = this.studentService.getStudent(name);
            model.addAttribute("student", entity);
            foreignModel(entity,model);
            return "admin/student/studentDetail";
    }


    //保存
    @RequestMapping(value="save", method={RequestMethod.POST})
    public String save(@ModelAttribute StudentDto studentDto,
        HttpServletRequest request,ModelMap model) {
            try{
                model.addAttribute("student",studentDto);
                foreignModel(studentDto,model);
                saveEntity(studentDto);
            }catch (Exception e){
                String exceptionMsg = ProjectUtil.buildExceptionMsg(e.getMessage());
                model.addAttribute("exceptionMsg","保存失败："+exceptionMsg);
                return "admin/student/studentDetail";
            }
            return "redirect:/admin/student/list";
    }

    private void saveEntity(StudentDto studentDto){
        this.studentService.transactionSaveEntity(studentDto,studentDto.getNameOldValue() );
    }


    //删除
    @RequestMapping({"/delete/{name}"})
    public void delete(
        @PathVariable String name,
        HttpServletResponse response) {

        this.studentService.deleteStudent(name );
        toJson(response, new AjaxData("ok", "", ""));
    }
    //清空表结构
    @RequestMapping(value = "truncate")
    public void truncate(HttpServletResponse response) {
        this.studentService.truncateStudent();
        toWebSuccessJson(response);
    }
    //json文件导入数据
    @RequestMapping(value = "import/json")
    public void importJson(@RequestParam MultipartFile myfiles,HttpServletResponse response) throws IOException {
        if(myfiles.getOriginalFilename().endsWith(".txt") ||
            myfiles.getOriginalFilename().endsWith(".js")||
            myfiles.getOriginalFilename().endsWith(".json")){
            String fileContent = FileHelper.fastReadFileUTF8(myfiles.getInputStream());
            List<Student> list = SerializerFastJsonUtil.get().readJsonList(fileContent,Student.class);
            if(ListUtils.isNotBlank(list)){
                this.studentService.transactionImportJsonList(list);
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
                @RequestParam(required = false,value ="sexFirst")                        Integer sexFirst ,
                @RequestParam(required = false,value ="nameFirst")                        String nameFirst ,
        HttpServletResponse response){
        String orderBy = filterOrderBy(orderBySqlField,descAsc);
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "classIdFirst",classIdFirst ,
                "sexFirst",sexFirst ,
                "nameFirst",nameFirst ,
        "limitIndex",start,"limit", limit });

        boolean useRelateQuery = false;
        List pageList;
            pageList = this.studentService.getStudentList(query);

        String file = "student";
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
            selectValue = StringUtils.deleteLastChar(sb.toString());
        }
        List<Student> list = new ArrayList<Student>();
        Map<String,Object> query = null;
        if(StringUtils.isBlank(keyword)){
            query = ProjectUtil.buildMap("limitIndex",0,"limit", 20);
            list = this.studentService.getStudentList(query);
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
                list = searchList("sexFirst",keyword);
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
                list = searchList("nameFirst",keyword);
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
    private List<Student> searchList(String field,String keyword){
        List<Student> list = this.studentService.getStudentList(ProjectUtil.buildMap(field,keyword,"limitIndex",0,"limit", 20));
        if(ListUtils.isNotBlank(list)){
            return list;
        }
        String[] keys = keyword.split("-");
        for(String key:keys){
            list = this.studentService.getStudentList(ProjectUtil.buildMap(field,key,"limitIndex",0,"limit", 20));
            if(ListUtils.isNotBlank(list)){
                return list;
            }
        }
        return null;
    }

    private List<InputSelectShowDto> showList(List<Student> list,String selectValue,String foreignJavaField){
        if(ListUtils.isNotBlank(list)){
            List<InputSelectShowDto> showList = new ArrayList<InputSelectShowDto>();
            showList.add(new InputSelectShowDto("全部",null));
            for(Student entity:list){
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
