package com.zjugis.demo.service.impl;


import com.zjugis.demo.dao.UserMapper;
import com.zjugis.demo.entity.User;
import com.zjugis.demo.service.UserService;
import com.zjugis.demo.utils.PasswordEncoder;
import com.zjugis.demo.utils.UUID;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wangwei
 * @version V1.0, 2017/10/21
 */
@Service
public class UserServiceImpl extends BaseLogger implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User validUser(Map mapUser) {
        User user = queryByUserName(mapUser.get("userName").toString());
        if (null != user){
            String jiaMiPassWord = user.getPassword();
            String jiaMiPassWord2 = passwordEncoder.encodePassword(mapUser.get("password").toString(),user.getUserName());
            if (!jiaMiPassWord.equalsIgnoreCase(jiaMiPassWord2)){
                return null;
            }else {
                return user;
            }
        }
        return null;
    }

    @Override
    public User queryByUserId(String userId){
        User user =new User();
        try {
            if(isNotNull(userId)){
                user = userMapper.selectByPrimaryKey(userId);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<User> getUsersByOrganizationId(String organizationId){
        return userMapper.queryByOrganizationId(organizationId);
    }

    @Override
    public User  queryByUserName(String var1){
        User user =new User();
        try {
            if(isNotNull(var1)){
                return userMapper.selectByUserName(var1);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<User> queryByRoleCode(String code){
        return userMapper.queryByRoleCode(code);
    }

    /**
     * 插入用户
     * @param user
     * @return
     */
    @Override
    public User insertUser(User user,String loginUserId){
        //TODO: 密码校验和加密
        if(!user.getPassword().equals(user.getRePassword())){
            throw new RuntimeException("两次密码不一致");
        }
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), user.getUserName()));
        User dbUser =  queryByUserName(user.getUserName());
        if (dbUser != null){
            throw new RuntimeException("用户名不能重复");
        }
        if (StringUtils.isBlank(user.getId())){
            user.setId(UUID.hex32());
        }
        user.setCreateAt(new Date());
        if (!"".equals(loginUserId)){
            user.setCreateUserId(loginUserId);
        }else {
            user.setCreateUserId("001");//超级管理员id
        }
        userMapper.insertSelective(user);
        return user;
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @Override
    public User updateUser(User user,String loginUserId){
        //TODO:密码加密
        User dbUser = queryByUserName(user.getUserName());
        if (dbUser != null && !dbUser.getId().equals(user.getId()))
            throw new RuntimeException("用户名不能重复");
        user.setUpdateAt(new Date());
        user.setUpdateUserId(loginUserId);
        userMapper.updateByPrimaryKeySelective(user);
        return user;
    }

    @Override
    public void delUser(String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (null!=user){
            userMapper.deleteByPrimaryKey(userId);
        }
    }

    /**
     *
     * @param id
     * @param oldPassword
     * @param newPassword
     * @param rePassword
     */
    @Override
    public void updatePassword(String  id, String oldPassword, String newPassword, String rePassword,String loginUserId){
        User user = userMapper.selectByPrimaryKey(id);
        if(user==null){
            throw new RuntimeException("用户不存在");
        }
        String dbOldPassword = user.getPassword();
        String enCodeOldPassword = passwordEncoder.encodePassword(oldPassword, user.getUserName());
        if(!dbOldPassword.equals(enCodeOldPassword)){
            throw new RuntimeException("旧密码不正确");
        }
        if(!newPassword.equals(rePassword)){
            throw new RuntimeException("两次密码不一样");
        }
        user.setPassword(passwordEncoder.encodePassword(newPassword, user.getUserName()));
        user.setUpdateAt(new Date());
        user.setUpdateUserId(loginUserId);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 获取 组织下的所有用户数量
     * @param orgId
     * @return
     */
    @Override
    public int usersUnderOrg(String orgId){
        return userMapper.usersUnderOrg(orgId);
    }

    /**
     * 管理员修改密码
     * @param id,newPassword,rePassword
     * @return
     */
    @Override
    public void updatePasswordAdmin(String id, String newPassword, String rePassword,String loginUserId){
        User user =  userMapper.selectByPrimaryKey(id);
        if(user==null){
            throw new RuntimeException("用户不存在");
        }
        if(!newPassword.equals(rePassword)){
            throw new RuntimeException("两次密码不一样");
        }
        user.setPassword(passwordEncoder.encodePassword(newPassword, user.getUserName()));
        user.setUpdateAt(new Date());

        user.setUpdateUserId(loginUserId);
        userMapper.updateByPrimaryKeySelective(user);
    }
}
