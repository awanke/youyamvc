package com.magicalcoder.youyamvc.app.utils;

import com.magicalcoder.youyamvc.core.common.utils.MapUtil;

import java.util.Map;

/**
 * Created by www.magicalcoder.com on 2015/5/22.
 * 799374340@qq.com
 */
public class ProjectUtil {
    public static Map<String,Object> buildMap(String key,Object value ,Object... args){
        return MapUtil.buildMap(key, value, args);
    }

    //验证是否合法字符串 逗号分隔 数字中间
    public static boolean validIds(String ids){
        String reg = "[\\d,]+";
        return ids.matches(reg);
    }
}
