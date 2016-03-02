package com.magicalcoder.youyamvc.web.controller.admin.userweb;
import com.magicalcoder.youyamvc.app.userweb.service.UserWebService;
import com.magicalcoder.youyamvc.app.userweb.constant.UserWebConstant;
import com.magicalcoder.youyamvc.app.model.UserWeb;
import com.magicalcoder.youyamvc.app.utils.ProjectUtil;
import com.magicalcoder.youyamvc.core.common.utils.ListUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.utils.copy.Copyer;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
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
* Created by hdy.
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/@RequestMapping({"/admin/user_web"})
@Controller
public class AdminUserWebListController extends AdminLoginController
{


    @Resource
    private UserWebService userWebService;

    @RequestMapping(value={"list"}, method={RequestMethod.GET})
    public String list(ModelMap model, HttpServletResponse response)
    {
        return "admin/userweb/userWebList";
    }

    @RequestMapping(value={"page/{pageIndex}/{pageCount}"}, method={RequestMethod.GET})
    public void page(@PathVariable Integer pageIndex, @PathVariable Integer pageCount,
        @RequestParam(required=false, value="orderBy") String orderBy,
                @RequestParam(required = false,value ="idFirst")                        Long idFirst ,
                @RequestParam(required = false,value ="userNameFirst")                        String userNameFirst ,
                @RequestParam(required = false,value ="mobileFirst")                        String mobileFirst ,
                @RequestParam(required = false,value ="realNameFirst")                        String realNameFirst ,
                @RequestParam(required = false,value ="accountLevelFirst")                        Integer accountLevelFirst ,
          HttpServletResponse response)
    {
        int idx = (pageIndex.intValue() - 1) * UserWebConstant.PAGE_SIZE;
        Map<String,Object> query = ProjectUtil.buildMap("orderBy", orderBy, new Object[] {
                "idFirst",idFirst ,
                "userNameFirst",userNameFirst ,
                "mobileFirst",mobileFirst ,
                "realNameFirst",realNameFirst ,
                "accountLevelFirst",accountLevelFirst ,
        "limitIndex",Integer.valueOf(idx),"limit", Integer.valueOf(UserWebConstant.PAGE_SIZE) });

        boolean useRelateQuery = false;
        List pageList;
        pageList = this.userWebService.getUserWebList(query);
        query.remove("orderBy");
        query.remove("limitIndex");
        query.remove("limit");
        if (pageCount == null || pageCount.intValue() == 0) {
                pageCount = this.userWebService.getUserWebListCount(query);
        }

        Map ajaxData = new HashMap();
        ajaxData.put("pageList", pageList);
        ajaxData.put("pageCount", pageCount);
        toJson(response, new AjaxData("ok", "success", ajaxData));
    }

    @RequestMapping({"/detail"})
    public String detail(ModelMap model) {
        detailDeal(null, model);
        return "admin/userweb/userWebDetail";
    }

    @RequestMapping({"/detail/{id}"})
    public String detailId(@PathVariable Long id, ModelMap model) {
        detailDeal(id, model);
        return "admin/userweb/userWebDetail";
    }
    private void detailDeal(Long id, ModelMap model) {
        UserWeb entity = new UserWeb();
        if (id != null) {
            entity = this.userWebService.getUserWeb(id);
        }
        model.addAttribute("userWeb", entity);
    }

    @RequestMapping(value={"save"}, method={RequestMethod.POST})
    public String save(@ModelAttribute UserWeb userWeb) {
        if (userWeb.getId() == null) {
            this.userWebService.insertUserWeb(userWeb);
        } else {
            UserWeb entity = this.userWebService.getUserWeb(userWeb.getId());
            Copyer.copy(userWeb, entity);
            this.userWebService.updateUserWeb(entity);
        }
        return "redirect:/admin/user_web/list";
    }

    @RequestMapping({"/delete/{id}"})
    public void delete(@PathVariable Long id, HttpServletResponse response) {
        this.userWebService.deleteUserWeb(id);
        toJson(response, new AjaxData("ok", "", ""));
    }
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
                this.userWebService.batchDeleteUserWeb(list);
                toJson(response, new AjaxData("ok", "", ""));
            }
        }else{
            toJson(response, new AjaxData("error", "没有要删除的主键", ""));
        }
    }
}
