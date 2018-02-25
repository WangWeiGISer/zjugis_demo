package com.zjugis.demo.controller;

import com.alibaba.fastjson.JSON;
import com.zjugis.demo.utils.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import com.zjugis.demo.support.Constant;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangwei
 * @version V1.0, 2017/10/21
 */
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final static String DATE_FORMATE = "yyyy-MM-dd";
    public void print(String o){
        System.out.println(o);
    }

    public boolean isNotNull(Object value){
        if (value != null) return true;
        if (value instanceof String) return StringUtils.isNotBlank((String) value);
        return false;
    }

    /**
     * 结果集合utf编码
     * @param value
     * @param response
     */
    protected void result(Object value, HttpServletResponse response) {
        Map result = new HashMap();
        result.put("result", value);
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected void sendFile(File file, HttpServletResponse response) throws IOException {
        if (file == null || response == null) return;
        if (file.exists()) {
            response.setDateHeader("Last-Modified", file.lastModified());
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), Constant.DEFAULT_ENCODING));
            response.setContentLength((int) file.length());
            FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "request file not found");
        }

    }

    /**
     * result
     */
    private enum Result {
        success, result, error
    }

    /**
     * result ok
     *
     * @param value
     * @return
     */
    protected Map result(Object value) {
        Map<String, Object> result = new HashMap();
        result.put(Result.success.name(), true);
        result.put(Result.result.name(), value);
        return result;
    }

    /**
     * error result
     *
     * @param value
     * @return
     */
    protected Map error(String value) {
        logger.error(value);
        Map<String, Object> result = new HashMap();
        result.put(Result.success.name(), false);
        result.put(Result.error.name(), value);
        return result;
    }

    /**
     * 返回服务端处理结果
     * @param obj 服务端输出对象
     * @return 输出处理结果给前段JSON格式数据
     * @author YANGHONGXIA
     * @since 2015-01-06
     */
    public String responseResult(Object obj){
        JSONObject jsonObj = null;
        if(obj != null){
            logger.info("后端返回对象：{}", obj);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            jsonObj = JSONObject.fromObject(obj, jsonConfig);
            logger.info("后端返回数据：" + jsonObj);
            if(Constant.SERVICE_RESPONSE_SUCCESS_CODE.equals(jsonObj.getString(Constant.SERVICE_RESPONSE_RESULT_FLAG))){
                jsonObj.element(Constant.RESPONSE_RESULT_FLAG_ISERROR, false);
                jsonObj.element(Constant.SERVICE_RESPONSE_RESULT_MSG, "");
            }else{
                jsonObj.element(Constant.RESPONSE_RESULT_FLAG_ISERROR, true);
                String errMsg = jsonObj.getString(Constant.SERVICE_RESPONSE_RESULT_MSG);
                jsonObj.element(Constant.SERVICE_RESPONSE_RESULT_MSG, errMsg==null?Constant.SERVICE_RESPONSE_NULL:errMsg);
            }
        }
        logger.info("输出结果：{}", jsonObj.toString());
        return jsonObj.toString();
    }

    /**
     * 返回成功
     * @param obj 输出对象
     * @return 输出成功的JSON格式数据
     */
    public String responseSuccess(Object obj){
        JSONObject jsonObj = null;
        if(obj != null){
            logger.info("后端返回对象：{}", obj);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            jsonObj = JSONObject.fromObject(obj, jsonConfig);
            logger.info("后端返回数据：" + jsonObj);
            jsonObj.element(Constant.RESPONSE_RESULT_FLAG_ISERROR, false);
            jsonObj.element(Constant.SERVICE_RESPONSE_RESULT_MSG, "");
        }
        logger.info("输出结果：{}", jsonObj.toString());
        return jsonObj.toString();
    }

    /**
     * 返回成功
     * @param obj 输出对象
     * @return 输出成功的JSON格式数据
     */
    public String responseArraySuccess(Object obj){
        JSONArray jsonObj = null;
        if(obj != null){
            logger.info("后端返回对象：{}", obj);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            jsonObj = JSONArray.fromObject(obj, jsonConfig);
            logger.info("后端返回数据：" + jsonObj);
        }
        logger.info("输出结果：{}", jsonObj.toString());
        return jsonObj.toString();
    }

    /**
     * 返回成功
     * @param obj 输出对象
     * @return 输出成功的JSON格式数据
     */
    public String responseSuccess(Object obj, String msg){
        JSONObject jsonObj = null;
        if(obj != null){
            logger.info("后端返回对象：{}", obj);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            jsonObj = JSONObject.fromObject(obj, jsonConfig);
            logger.info("后端返回数据：" + jsonObj);
            jsonObj.element(Constant.RESPONSE_RESULT_FLAG_ISERROR, false);
            jsonObj.element(Constant.SERVICE_RESPONSE_RESULT_MSG, msg);
        }
        logger.info("输出结果：{}", jsonObj.toString());
        return jsonObj.toString();
    }

    /**
     * 返回失败
     * @param errorMsg 错误信息
     * @return 输出失败的JSON格式数据
     */
    public String responseFail(String errorMsg){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(Constant.RESPONSE_RESULT_FLAG_ISERROR, true);
        jsonObj.put(Constant.SERVICE_RESPONSE_RESULT_MSG, errorMsg);
        logger.info("输出结果：{}", jsonObj.toString());
        return jsonObj.toString();
    }
}
