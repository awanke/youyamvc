package com.magicalcoder.youyamvc.app.adminuser.utils;

import com.magicalcoder.youyamvc.app.adminuser.AdminUserConstant;
import com.magicalcoder.youyamvc.app.adminuser.dto.AdminUserDto;
import com.magicalcoder.youyamvc.core.cache.common.CacheUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by hzwww.magicalcoder.com on 2016/1/11.
 */
public class AdminUserContextUtil {
    // 获取当前登录用户sessionKey
    public static String getSessionKey(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String sessionId = "";
        if(cookies==null) return null;
        for(Cookie cookie:cookies){
            String name = cookie.getName();
            if(name.equals(AdminUserConstant.ADMIN_LOGIN_COOKIE_KEY)){
                sessionId = cookie.getValue();
                break;
            }
        }
        return sessionId;
    }

    public static AdminUserDto getLoginUserDto(HttpServletRequest request){
        String sessionId = getSessionKey(request);
        AdminUserDto adminUserDto = CacheUtil.get(sessionId);
        return adminUserDto;
    }
}
