package com.zero.service.api;

import com.github.pagehelper.PageInfo;
import com.zero.entity.Role;

import java.util.List;

/**
 * @Author: zero
 * @Description:
 * Date: Create in 2019/11/20 20:26
 * Modified By:
 */
public interface RoleService {
    /**
     * 根据关键词模糊查询
     * @param pageNum 当前页码
     * @param pageSize 分页数量
     * @param keyword 关键词
     * @return 封装成PageInfo的查询结果集合
     */
    PageInfo<Role> queryForKeywordWithPage(Integer pageNum,Integer pageSize,String keyword);

    /**
     * 根据id数组获取对应数据
     * @param roleIdlist
     * @return
     */
    List<Role> getRoleListByIdList(List<Integer> roleIdlist);

    /**
     * 根据id数组删除对应数据
     * @param roleIdList
     */
    void batchRemove(List<Integer> roleIdList);

    void saveRole(String roleName);

    void updateRole(Role role);

    List<Role> getAssignedRoleList(Integer adminId);

    List<Role> getUnAssignedRoleList(Integer adminId);

    void updateRelationship(Integer adminId, List<Integer> roleIdList);
}
