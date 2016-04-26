package com.magicalcoder.youyamvc.app.student.util;
/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
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
                    String orderBySqlFieldStr = ",class_id,sex,name,identy_key,";
                if(orderBySqlFieldStr.contains("" + orderBySqlField+"")){//精确匹配可排序字段
                    orderBy = orderBySqlField+" "+descAsc;
                }
            }
        }
        return orderBy;
    }

    //排序部分防sql注入安全过滤
    public static String filterOrderBy(String orderByStr){
        StringBuilder sb = new StringBuilder();
        if(orderByStr!=null && !"".equals(orderByStr.trim())){
            orderByStr = orderByStr.toLowerCase().trim();
            String[] arr = orderByStr.split(",");
            for(String sqlOrderField:arr){
                String orderBySqlFieldStr = ",class_id,sex,name,identy_key,";
                String sqlField = sqlOrderField.replaceAll("\\s+asc|\\s+desc","").trim();
                if(!"".equals(sqlField)&&orderBySqlFieldStr.contains(sqlField)){//精确匹配可排序字段
                    sb.append(sqlOrderField).append(",");
                }
            }
        }
        if("".equals(sb.toString())){
            return null;
        }
        if(sb.toString().endsWith(",")){
            return sb.substring(0,sb.length()-1);
        }
        return null;
    }

}
