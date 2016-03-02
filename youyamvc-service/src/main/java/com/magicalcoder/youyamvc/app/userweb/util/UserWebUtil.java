package com.magicalcoder.youyamvc.app.userweb.util;

import com.magicalcoder.youyamvc.app.model.UserWeb;
import com.magicalcoder.youyamvc.app.userweb.dto.UserWebDto;
import com.magicalcoder.youyamvc.core.cache.xmemcached.utils.MemcachedClientUtils;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
* Created by hdy.
* 799374340@qq.com
*/
public class UserWebUtil {
    
    private static final String cookieKey = "webLoginSession";//登录后设置的cookie key
    public static String getLoginCookieKey(){
        return cookieKey;
    }
    /**
     * 用户登录后的缓存key
     * @param sessionId
     * @return
     */
    public static String userCacheKey(String sessionId){
        return cookieKey + sessionId;
    }

    public static void setUserDtoToRequest(HttpServletRequest request,UserWebDto userDto){
        request.setAttribute(cookieKey,userDto);
    }

    /**
     * 获取登录用户id
     * @param request
     * @return
     */
    public static Long userId(HttpServletRequest request){
        UserWebDto userWebDto = getUserDtoFromRequest(request);
        if(userWebDto!=null){
            return userWebDto.getId();
        }
        return null;
    }

    public static UserWebDto getUserDtoFromRequest(HttpServletRequest request){
        return (UserWebDto)request.getAttribute(cookieKey);
    }

    public static UserWebDto getUserDtoFromCache(HttpServletRequest request){
        String sessionId = getLoginCookieValue(request);
        if(StringUtils.isBlank(sessionId)){
            return null;
        }
        try {
            String cacheKey = userCacheKey(sessionId);
            Object obj = MemcachedClientUtils.get().get(cacheKey);
            return (UserWebDto)obj;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String getLoginCookieValue(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie cookie : cookies){
                if(cookieKey.equals(cookie.getName().trim())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
