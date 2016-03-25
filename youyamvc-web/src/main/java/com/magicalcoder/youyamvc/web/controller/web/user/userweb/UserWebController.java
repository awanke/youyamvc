package com.magicalcoder.youyamvc.web.controller.web.user.userweb;

import com.magicalcoder.youyamvc.app.model.UserWeb;
import com.magicalcoder.youyamvc.app.userweb.constant.UserWebConstant;
import com.magicalcoder.youyamvc.app.userweb.dto.UserWebDto;
import com.magicalcoder.youyamvc.app.userweb.service.UserWebService;
import com.magicalcoder.youyamvc.app.userweb.util.UserWebUtil;
import com.magicalcoder.youyamvc.app.userweb.util.ValidCodeUtil;
import com.magicalcoder.youyamvc.core.cache.common.CacheUtil;
import com.magicalcoder.youyamvc.core.common.dto.JsonData;
import com.magicalcoder.youyamvc.core.common.utils.MapUtil;
import com.magicalcoder.youyamvc.core.common.utils.SysPropertiesUtil;
import com.magicalcoder.youyamvc.core.common.utils.copy.CopyerSpringUtil;
import com.magicalcoder.youyamvc.core.common.utils.safe.AesUtil;
import com.magicalcoder.youyamvc.web.common.BaseController;
import com.magicalcoder.youyamvc.web.common.StatusConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hzwww.magicalcoder.com on 2016/1/18.
 * 用户 登录 注册 相关接口
 */
@Controller
@RequestMapping("/user/user_web/")
public class UserWebController extends BaseController{

    @Resource
    private UserWebService userWebService;

    //检查用户名是否被注册
    @RequestMapping(value = "check_user_name/{userName}",method = RequestMethod.GET)
    public void checkUserName(@PathVariable String userName,HttpServletResponse response){
        Integer count = userWebService.getUserWebListCount(MapUtil.buildMap("userName", userName));
        if(count>0){
            toJsonData(response, new JsonData.Builder(null).code(StatusConstant.VALID_PARAM).message("此用户名已被注册").build());
        }else {
            toJsonData(response, new JsonData.Builder(null).code(StatusConstant.SUCCESS).message("此用户名可以使用").build());
        }
    }

    //用户注册接口
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public void register(@RequestParam(required = true,value = "userName") String userName,
                         @RequestParam(required = true,value = "userPassword") String userPassword,
                         @RequestParam(required = true,value = "validCode") String validCode,
                         HttpServletRequest request,HttpServletResponse response){
        if(StringUtils.isBlank(validCode)){
            toWebFailureJson(response,"入参为空");
            return;
        }
        String sessionId = request.getSession().getId();
        String storedValidCode = ValidCodeUtil.getValidCodeFromCache(UserWebConstant.VALID_CODE_USE_REGISTER, sessionId);
        if(!validCode.equals(storedValidCode)){
            toJsonData(response, new JsonData.Builder(null).code(StatusConstant.VALID_PARAM).message("验证码不正确").build());
        }else {
            Integer count = userWebService.getUserWebListCount(MapUtil.buildMap("userName", userName));
            if(count>0){
                toJsonData(response, new JsonData.Builder(null).code(StatusConstant.VALID_PARAM).message("此用户名已被注册").build());
            }else {
                UserWeb userWeb = new UserWeb();
                userWeb.setUserName(userName);
                //密码
                String encryptPassword = encryptPassword(userPassword);
                userWeb.setUserPassword(encryptPassword);
                userWebService.insertUserWeb(userWeb);
                toJsonData(response, new JsonData.Builder(null).code(StatusConstant.SUCCESS).message("注册成功").build());
            }
        }
    }

    private String encryptPassword(String userPassword){
        String aesPass = SysPropertiesUtil.getProperty("AES_PASSWORD");
        String iv = SysPropertiesUtil.getProperty("AES_IV");
        return AesUtil.encrypt(userPassword, aesPass, iv);
    }

    //用户登录
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public void login(@RequestParam(required = true,value = "userName") String userName,
                      @RequestParam(required = true,value = "userPassword") String userPassword,
                      HttpServletRequest request,HttpServletResponse response){
        String encryptPassword = encryptPassword(userPassword);
        UserWeb userWeb = userWebService.selectOneUserWebWillThrowException(
                MapUtil.buildMap("userName", userName, "userPassword", encryptPassword));
        if(userWeb!=null){
            String sessionId = request.getSession().getId();
            UserWebDto dto = new UserWebDto();
            CopyerSpringUtil.copyProperties(userWeb, dto, UserWebDto.class);
            dto.setUserPassword(null);
            String cacheKey = UserWebUtil.userCacheKey(sessionId);
            CacheUtil.resetCache(cacheKey,3600*30,dto);
/*            Object o = MemcachedClientUtils.get(cacheKey);
            System.out.println(o==null);*/
            //设置cookie
            Cookie sessionCookie = new Cookie(UserWebUtil.getLoginCookieKey(),sessionId);
            sessionCookie.setPath("/");
            response.addCookie(sessionCookie);
            toJsonData(response, new JsonData.Builder(dto).code(StatusConstant.SUCCESS).message("登录成功").build());
        }else {
            toJsonData(response, new JsonData.Builder(null).code(StatusConstant.LOGIN_FAIL).message("登录失败").build());
        }
    }


    //用户注册接口
    @RequestMapping(value = "find_password",method = RequestMethod.POST)
    public void findPassword(@RequestParam(required = true,value = "userName") String userName,
                         @RequestParam(required = true,value = "userPassword") String userPassword,
                         @RequestParam(required = true,value = "validCode") String validCode,
                         HttpServletRequest request,HttpServletResponse response){
        if(StringUtils.isBlank(validCode)||StringUtils.isBlank(userPassword)||StringUtils.isBlank(userName)){
            toWebFailureJson(response,"入参为空");
            return;
        }
        String sessionId = request.getSession().getId();
        String storedValidCode = ValidCodeUtil.getValidCodeFromCache(UserWebConstant.VALID_CODE_USE_FIND_PASSWORD, sessionId);
        if(!validCode.equals(storedValidCode)){
            toJsonData(response, new JsonData.Builder(null).code(StatusConstant.VALID_PARAM).message("验证码不正确").build());
        }else {
            UserWeb userWeb = userWebService.selectOneUserWebWillThrowException(MapUtil.buildMap("userName", userName));
            if(userWeb!=null){
                String encryptPassword = encryptPassword(userPassword);
                userWeb.setUserPassword(encryptPassword);
                userWebService.updateUserWeb(userWeb);
                toWebSuccessJson(response);
            }else {
                toWebFailureJson(response,"找不到此用户");
            }
        }
    }

}
