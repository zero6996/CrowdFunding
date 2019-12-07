package com.zero.mapper;

import com.zero.entity.Auth;
import com.zero.entity.AuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthMapper {
    int countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    /**
     * 根据角色id查询角色信息列表
     * @param roleId
     * @return
     */
    List<Integer> selectAssignedAuthIdList(Integer roleId);

    void deleteOldRelationship(Integer roleId);

    void insertNewRelationship(@Param("roleId") Integer roleId, @Param("authIdList") List<Integer> authIdList);

    /**
     * 根据用户id查询权限信息列表
     * @param adminId
     * @return
     */
    List<Auth> selectAuthListByAdminId(Integer adminId);
}