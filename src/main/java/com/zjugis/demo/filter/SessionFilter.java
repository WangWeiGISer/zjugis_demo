package com.zjugis.demo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionFilter implements Filter {
    public void destroy() {
// TODO Auto-generated method stub
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        // 登陆url
        String loginUrl = httpRequest.getContextPath() + "/login";
        String url = httpRequest.getRequestURI();
        String path = url.substring(url.lastIndexOf("/")+1);
        // 超时处理，ajax请求超时设置超时状态，页面请求超时则返回提示并重定向
        if (session.getAttribute("LOGIN_SUCCESS") == null) {
        // 判断是否为ajax请求
            if (httpRequest.getHeader("x-requested-with") != null
                    && httpRequest.getHeader("x-requested-with")
                    .equalsIgnoreCase("XMLHttpRequest")) {
                httpResponse.addHeader("sessionstatus", "timeOut");
                httpResponse.addHeader("loginPath", loginUrl);
                chain.doFilter(request, response);// 不可少，否则请求会出错
            } else {
                String str = "<script language='javascript'>"
                        + "window.top.location.href='"
                        + loginUrl
                        + "';</script>";
                response.setContentType("text/html;charset=UTF-8");// 解决中文乱码
                try {
                    PrintWriter writer = response.getWriter();
                    writer.write(str);
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}