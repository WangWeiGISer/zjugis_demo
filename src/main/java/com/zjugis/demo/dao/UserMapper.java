package com.zjugis.demo.dao;

import com.zjugis.demo.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectUserList(Map user);

    User selectByUserName(String userName);

    List<User> queryByRoleCode(String code);

    /**
     * 获取 组织下的所有用户
     * @param orgId
     * @return
     */
    int usersUnderOrg(String orgId);

    /**
     * 根据机构id查询
     * @param organizationId
     * @return
     */
    List<User> queryByOrganizationId(String organizationId);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}