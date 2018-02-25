package com.zjugis.demo.support;

import java.nio.charset.Charset;

/**
 * 系统常量
 *
 * @author <a href="mailto:unclemiki@163.com">UncleMiki</a>
 * @verison 1.0
 * @date 16/5/24
 */
public final class Constant {

    public final static String UTF_8_ENCODING = "UTF-8";

    public final static String DEFAULT_ENCODING = UTF_8_ENCODING;

    public final static Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_ENCODING);

    public final static String IMAGE_THUMB_NAME = "thumb.jpg";

    public final static String ROOT_NODE = "/root";

    public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String DATE_FORMAT = "yyyy-MM-dd";

    public final static String TIME_FORMAT = "HH:mm:ss";

    /**
     * errorcode
     */
    public final static String SDE_SELFINTERSECT = "2001";


    public static final String SYSTEM_ERROR_MSG = "系统错误";

    public static final String REQUEST_PARAMS_NULL = "请求参数为空";

    public static final String SERVICE_RESPONSE_NULL = "服务端返回结果为空";

    // 服务端返回成功的标志
    public static final String SERVICE_RESPONSE_SUCCESS_CODE = "AMS00000";

    // 服务端返回结果的标志
    public static final String SERVICE_RESPONSE_RESULT_FLAG = "returnCode";

    // 服务端返回结果失败的标志
    public static final String SERVICE_RESPONSE_RESULT_MSG = "errorMsg";

    // 返回给前段页面成功或失败的标志
    public static final String RESPONSE_RESULT_FLAG_ISERROR = "isError";

    // 执行删除操作
    public static final String OPERATION_TYPE_DELETE = "D";

    public static final String ENUM_PATH = "com.mucfc.msm.enumeration.";

    //标记删除
    public static final int IS_DATA_DEL = 1;

    /**
     * shape字段常量
     */
    public final static String SE_SHAPE_FIELD = "SHAPE";

    /**
     * 已核查企业图层名
     */
    public final static String YHCQY_LAYER_NAME = "YHCQY";

}
