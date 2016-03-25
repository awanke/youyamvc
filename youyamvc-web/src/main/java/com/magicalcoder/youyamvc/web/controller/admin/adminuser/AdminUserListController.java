package com.magicalcoder.youyamvc.web.controller.admin.adminuser;

import com.alibaba.fastjson.JSON;
import com.magicalcoder.youyamvc.app.adminuser.AdminUserConstant;
import com.magicalcoder.youyamvc.app.adminuser.dto.AdminUserDto;
import com.magicalcoder.youyamvc.app.adminuser.service.AdminUserService;
import com.magicalcoder.youyamvc.app.adminuser.utils.AdminUserContextUtil;
import com.magicalcoder.youyamvc.app.model.AdminUser;
import com.magicalcoder.youyamvc.app.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.file.FileHelper;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.common.utils.serialize.SerializerFastJsonUtil;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by www.magicalcoder.com on 2015/4/29.
 * 799374340@qq.com
 */
@RequestMapping(value = "/admin/adminUser")
@Controller
public class AdminUserListController extends AdminLoginController {
    @Resource(name="adminUserService")
    private AdminUserService adminUserService;
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String list(ModelMap model,HttpServletResponse response){
        return "admin/adminUser/adminUserList";
    }
    @RequestMapping(value = "page/{pageIndex}/{pageCount}",method = RequestMethod.GET)
    public void page(@PathVariable Integer pageIndex,@PathVariable Integer pageCount ,
                      @RequestParam(required = false,value = "orderBy") String orderBy ,
                      @RequestParam(required = false,value = "userName") String userName ,
                      @RequestParam(required = false,value = "realName") String realName ,
                      @RequestParam(required = false,value = "email") String email ,
                      HttpServletResponse response){
        int idx = (pageIndex-1)* AdminUserConstant.PAGE_SIZE;
        List<AdminUser> pageList = adminUserService.getAdminUserList(ProjectUtil.buildMap(
"userName" , userName,"realName" , realName,"email" , email,                "orderBy",orderBy,"limitIndex", idx,"limit", AdminUserConstant.PAGE_SIZE        ));
        if(pageCount==null || pageCount==0){
            pageCount = adminUserService.getAdminUserListCount(ProjectUtil.buildMap(
"userName" , userName,"realName" , realName,"email" , email            ));
        }
        Map<String,Object> ajaxData = new HashMap<String, Object>();
        ajaxData.put("pageList",pageList);
        ajaxData.put("pageCount",pageCount);
        toJson(response, new AjaxData("ok", "success", ajaxData));
    }
    //新增
    @RequestMapping(value = "/detail")
    public String detail(ModelMap model){
        detailDeal(null, model);
        return "admin/adminUser/adminUserDetail";
    }
    //详情
    @RequestMapping(value = "/detail/{id}")
    public String detailId(@PathVariable Long id,ModelMap model){
        detailDeal(id, model);
        return "admin/adminUser/adminUserDetail";
    }
    private void detailDeal(Long id,ModelMap model){
        AdminUser entity = new AdminUser();
        if(id!=null){
            entity = adminUserService.getAdminUser(id);
        }
        model.addAttribute("adminUser", entity);
    }
    //保存
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public String save(@ModelAttribute AdminUser adminUser,HttpServletRequest request){
        AdminUserDto adminUserDto = AdminUserContextUtil.getLoginUserDto(request);
        if(adminUserDto==null){
            return "redirect:/admin/adminUser/list";
        }
        Integer superAdmin = adminUserDto.getSuperAdmin();
        if(superAdmin!=1 || StringUtils.isBlank(adminUser.getPassword())
                ||StringUtils.isBlank(adminUser.getUserName())){
            return "redirect:/admin/adminUser/list";
        }
        if(adminUser.getPassword().trim().length()<6){
            return "redirect:/admin/adminUser/list";
        }

        if(adminUser.getId()==null){
            adminUserService.insertAdminUser(adminUser);
        }else{
            AdminUser entity = adminUserService.getAdminUser(adminUser.getId());
            Copyer.copy(adminUser,entity);
            adminUserService.updateAdminUser(entity);
        }
        return "redirect:/admin/adminUser/list";
    }
    //删除
    @RequestMapping(value = "/delete/{id}")
    public void delete(@PathVariable Long id,HttpServletRequest request,HttpServletResponse response){
        AdminUserDto adminUserDto = AdminUserContextUtil.getLoginUserDto(request);
        if(adminUserDto==null){
            toJson(response,new AjaxData("error","",""));
            return ;
        }
        Integer superAdmin = adminUserDto.getSuperAdmin();
        if(superAdmin!=1){
            toJson(response,new AjaxData("error","",""));
            return ;
        }
        adminUserService.deleteAdminUser(id);
        toJson(response,new AjaxData("ok","",""));
    }

    @RequestMapping(value = "truncate")
    public void truncate(HttpServletResponse response) {
//        adminUserService.truncateAdminUser();
        toWebSuccessJson(response);
    }

    @RequestMapping(value = "import/json")
    public void importJson(@RequestParam MultipartFile myfiles,HttpServletResponse response) throws IOException {
        if(myfiles.getOriginalFilename().endsWith(".txt")){
            String fileContent = FileHelper.fastReadFileUTF8(myfiles.getInputStream());
            List<AdminUser> list = SerializerFastJsonUtil.get().readJsonList(fileContent,AdminUser.class);
            toWebSuccessJson(response);
        }else {
            toWebFailureJson(response, "文件后缀名必须为.txt");
        }

    }

    @RequestMapping(value = "export/json/{start}/{limit}",method = RequestMethod.GET)
    public void exportJson(
                     @PathVariable Integer start,@PathVariable Integer limit,
                     @RequestParam(required = false,value = "orderBy") String orderBy ,
                     @RequestParam(required = false,value = "userName") String userName ,
                     @RequestParam(required = false,value = "realName") String realName ,
                     @RequestParam(required = false,value = "email") String email ,
                     HttpServletResponse response){
        List<AdminUser> pageList = adminUserService.getAdminUserList(ProjectUtil.buildMap(
                "userName" , userName,"realName" , realName,"email" , email,
                "orderBy",orderBy,"limitIndex", start,"limit", limit ));
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
