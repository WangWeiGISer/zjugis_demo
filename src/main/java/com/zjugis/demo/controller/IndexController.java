package com.zjugis.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wangwei
 * @version V1.0, 2017/10/21
 */
@Controller
public class IndexController extends BaseController
{
    @RequestMapping(value="/login")
    public String load(HttpServletRequest request, HttpSession session, @RequestParam(value="error", required=false) String error, Model model)
    {
        return "login";
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpSession session){
        session.removeAttribute("user");
        session.removeAttribute("LOGIN_SUCCESS");
        return "login";
    }

}