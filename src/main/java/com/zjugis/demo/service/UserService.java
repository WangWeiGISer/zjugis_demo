package com.zjugis.demo.service;

import com.zjugis.demo.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author wangwei
 * @version V1.0, 2017/10/21
 */
public interface UserService {

    /**
     * 获取当前登录用户
     * @return
     */
 //   User getCurrentUser();


    /**
     * 校验用户
     * @param user
     * @return
     */
    public User validUser(Map user);

    User queryByUserName(String var1);

    User queryByUserId(String var1);

    /**
     * 根据机构查询用户
     * @param organizationId
     * @return
     */
    List<User> getUsersByOrganizationId(String organizationId);

    /**
     * 插入用户
     * @param user
     * @return
     */
    User insertUser(User user,String loginUserId);

    /**
     * 更新用户
     * @param user
     * @return
     */
    User updateUser(User user,String loginUserId);

    /**
     * 删除用户
     * @param userId
     */
    void delUser(String userId);

    /**
     *
     * @param id
     * @param oldPassword
     * @param newPassword
     * @param rePassword
     */
    void updatePassword(String  id, String oldPassword, String newPassword, String rePassword,String loginUserId);

    /**
     * 获取 组织下的所有用户
     * @param orgId
     * @return
     */
    int usersUnderOrg(String orgId);

    /**
     * 管理员修改密码
     * @param id,newPassword,rePassword
     * @return
     */
    void updatePasswordAdmin(String id, String newPassword, String rePassword,String loginUserId);
    /**
     * 根据 角色代码  查用户集合
     * @param code
     * @return
     */
    List<User> queryByRoleCode(String code);



}
