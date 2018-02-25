package com.zjugis.demo.support.core;

import com.zjugis.demo.support.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:unclemiki@163.com">UncleMiki</a>
 * @verison 1.0
 * @date 16/10/9
 */
public class BaseController extends BaseLogger {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


//    @Autowired
//    protected HttpServletRequest request;
//
//    @Autowired
//    protected HttpServletResponse response;

    /**
     * 重定向
     * @param url
     * @return
     */
    protected String redirectTo(String url) {
        StringBuffer rto = new StringBuffer("redirect:");
        rto.append(url);
        return rto.toString();
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
     * send file
     *
     * @param file
     * @param response
     */
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
     * send stream
     *
     * @param inputStream
     * @param response
     * @param fileName
     * @throws IOException
     */
    protected void sendStream(InputStream inputStream, HttpServletResponse response, String fileName) throws IOException {
        if (inputStream == null || response == null) return;
        if (inputStream.available() > 0) {
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, Constant.DEFAULT_ENCODING));
            response.setDateHeader("Last-Modified", new Date().getTime());
            response.setContentLength(inputStream.available());
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "request file not found");
        }
    }

    /**
     * @param file
     * @param response
     * @throws IOException
     */
    protected void sendFileStream(File file, HttpServletResponse response) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        if (inputStream == null || response == null) return;
        if (inputStream.available() > 0) {
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), Constant.DEFAULT_ENCODING));
            response.setDateHeader("Last-Modified", new Date().getTime());
            response.setContentLength(inputStream.available());
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "request file not found");
        }
    }
}
