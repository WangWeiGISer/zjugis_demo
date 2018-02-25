package com.zjugis.demo.support;


import com.zjugis.demo.entity.*;
import com.zjugis.demo.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class EnvCtx {


    @Autowired
    private UserService userService;




    private static User currentUser;

    public User getCurrentUser(){
        User user= currentUser;
        if(currentUser==null){
            user = currentUser;
        }
        return user;
    }

    public static void setUser(User user){
        currentUser = user;
    }


    /**
     * 判断当前登录用户是否为管理员
     * @return
     */
    public boolean isAdmin(){
        boolean isAdmin = false;
        if(null!=getCurrentUser()){
            User user = getCurrentUser();
           String roleCodes= user.getRoles();
            if (null!=roleCodes){
                return roleCodes.contains("admin");
            }
        }
        return isAdmin;
    }

    public String getAliName(){
        String aliName = "";
        if(null!=getCurrentUser()){
            User user = getCurrentUser();
            aliName = user.getAliasName();
        }
        return aliName;
    }





   /* *//**
     *
     * @param key
     * @return
     *//*
    public String getPro(String key){
        return ApplicationConfig.getProperty(key);
    }


    *//**
     *
     * @return
     *//*
    public List<String> getCurrentUserPowers(){
        return userService.getCurrentUserPowers();
    }

    *//**
     *
     * @return
     *//*
    public boolean hasDataView(){
        return userService.hasDataView();
    }

    *//**
     *
     * @return
     *//*
    public boolean hasDataEdit(){
        return userService.hasDataEdit();
    }*/
}
