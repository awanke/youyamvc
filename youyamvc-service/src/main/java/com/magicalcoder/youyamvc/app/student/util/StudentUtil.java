package com.magicalcoder.youyamvc.app.student.util;
/**
* Created by www.magicalcoder.com
* 799374340@qq.com
*/
public class StudentUtil {

    //排序部分防sql注入安全过滤
    public static String filterOrderBy(String orderBySqlField,String descAsc){
        String orderBy = null;
        if(descAsc!=null && !"".equals(descAsc.trim())){
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

}
