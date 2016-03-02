package com.magicalcoder.youyamvc.core.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.magicalcoder.youyamvc.core.common.dto.AjaxData;
import com.magicalcoder.youyamvc.core.common.dto.JsonData;
import com.magicalcoder.youyamvc.core.common.utils.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.sql.Date;

/**
 * Created by www.magicalcoder.com on 2015/6/23.
 * 799374340@qq.com
 */
public class JsonWrite {
    static final SerializerFeature[] features = new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue};
    static final SerializeConfig config = new SerializeConfig();
    static {
        config.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 正常情况时
     * 前端快速返回json
     * @param response
     */
    public void toWebSuccessJson(HttpServletResponse response){
        toJsonData(response,new JsonData.Builder(null).code(0).build());
    }

    /**
     * 正常情况时
     * 前端快速返回json
     * @param response
     */
    public void toWebSuccessJson(HttpServletResponse response,Object info){
        toJsonData(response,new JsonData.Builder(info).code(0).build());
    }
    /**
     * 错误情况时
     * 前端快速返回json
     * @param response
     */
    public void toWebFailureJson(HttpServletResponse response,String message){
        toJsonData(response,new JsonData.Builder(null).code(-1).message(message).build());
    }

    /**
     * 所有admin使用此方法
     * @param response
     * @param ajaxData
     */
    public void toJson(HttpServletResponse response, AjaxData ajaxData){
        try {
            byte[] bytes;
            if(StringUtils.isBlank(ajaxData.getJsonp())){
                bytes = JSON.toJSONString(ajaxData,config,features).getBytes("UTF-8");
            }else{
                bytes = (ajaxData.getJsonp()+"("+JSON.toJSONString(ajaxData,config,features)+")").getBytes("UTF-8");
            }
            response.setCharacterEncoding("UTF-8");
            /*使用 text/html 则页面就需要 使用JSON.parse(data) 来处理 这种方法所有浏览器都支持 下面的
            * ie不支持啊*/
            response.setContentType("text/json;charset=UTF-8");
            response.setContentLength(bytes.length);
            OutputStream writer = response.getOutputStream();
            writer.write(bytes);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*public void toSimpleJson(HttpServletResponse response,String callBack, Object ajaxData){
        try {
            byte[] bytes;
            bytes = (callBack+"("+JSON.toJSONString(ajaxData, config, new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat})+")").getBytes("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json;charset=UTF-8");
            response.setContentLength(bytes.length);
            OutputStream writer = response.getOutputStream();
            writer.write(bytes);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 自定义类型输出json
     * @param response
     * @param ajaxData
     */
    public void toSimpleJson(HttpServletResponse response, Object ajaxData){
        try {
            byte[] bytes;
            bytes = JSON.toJSONString(ajaxData, config, new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat}).getBytes("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json;charset=UTF-8");
            response.setContentLength(bytes.length);
            OutputStream writer = response.getOutputStream();
            writer.write(bytes);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*public void toSimpleWithNullJson(HttpServletResponse response,String callBack, Object ajaxData){
        try {
            byte[] bytes;
            bytes = (callBack+"("+ JSON.toJSONString(ajaxData, config, features)+")").getBytes("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json;charset=UTF-8");
            response.setContentLength(bytes.length);
            OutputStream writer = response.getOutputStream();
            writer.write(bytes);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void toSimpleWithNullJson(HttpServletResponse response, Object ajaxData){
        try {
            byte[] bytes;
            bytes = JSON.toJSONString(ajaxData, config, features).getBytes("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json;charset=UTF-8");
            response.setContentLength(bytes.length);
            OutputStream writer = response.getOutputStream();
            writer.write(bytes);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 所有web版使用此方法
     * @param response
     * @param jsonData
     */
    public void toJsonData(HttpServletResponse response, JsonData jsonData){
        try {
            byte[] bytes;
            bytes = JSON.toJSONString(jsonData, config, features).getBytes("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json;charset=UTF-8");
            response.setContentLength(bytes.length);
            OutputStream writer = response.getOutputStream();
            writer.write(bytes);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
