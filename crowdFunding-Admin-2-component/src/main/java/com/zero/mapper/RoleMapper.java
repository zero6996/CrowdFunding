package com.zero.mapper;


import com.zero.entity.Role;
import com.zero.entity.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 根据关键词模糊查询
     * @param keyword 关键词
     * @return 查询结果集合
     */
    List<Role> selectForKeywordSearch(String keyword);

    /**
     * 查询已分配的角色
     * @param adminId
     * @return
     */
    List<Role> selectAssignedRoleList(Integer adminId);

    List<Role> selectUnAssignedRoleList(Integer adminId);

    void deleteOldAdminRelationship(Integer adminId);

    void insertNewAdminRelationship(@Param("adminId") Integer adminId, @Param("roleIdList") List<Integer> roleIdList);

}