package com.zjugis.demo.controller;

import com.zjugis.demo.entity.User;
import com.zjugis.demo.service.UserService;
import com.zjugis.demo.support.EnvCtx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * @author wangwei
 * @version V1.0, 2017/10/21
 */
@Controller
@RequestMapping("/user")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    @ResponseBody
    public int login(@RequestParam(value="username",required = true)String userName,
                     @RequestParam(value="password",required = true)String password,
                     HttpServletRequest request, HttpSession session, Model model,
                     HttpServletResponse response){

        Map us = new HashMap();
        us.put("userName",userName);
        us.put("password", password);
        //us.put("password", CryptUtil.encryptToMD5(password));
        User user = userService.validUser(us);

        if(user!=null){
            EnvCtx.setUser(user);
            session = request.getSession();
            session.setAttribute("user",user);
            session.setAttribute("LOGIN_SUCCESS",true);
            session.setMaxInactiveInterval(30*60);
            return 1;
        }else {
            return 0;
        }
    }

    @RequestMapping("/success")
    public  String toRiverMap( HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        EnvCtx envCtx = new EnvCtx();
        envCtx.setUser(user);
        return "user/success";
    }
}
