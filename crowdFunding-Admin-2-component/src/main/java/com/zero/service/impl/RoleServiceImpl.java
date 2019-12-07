package com.zero.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zero.entity.Role;
import com.zero.entity.RoleExample;
import com.zero.mapper.RoleMapper;
import com.zero.service.api.RoleService;
import com.zero.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zero
 * @Description:
 * Date: Create in 2019/11/20 20:26
 * Modified By:
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 根据关键词模糊查询
     * @param pageNum 当前页码
     * @param pageSize 每页显示数
     * @param keyword 关键词
     * @return 封装成PageInfo的查询结果集合
     */
    @Override
    public PageInfo<Role> queryForKeywordWithPage(Integer pageNum, Integer pageSize, String keyword) {
        /**
         * 开启分页功能
         */
        PageHelper.startPage(pageNum,pageSize);
        /**
         *  执行查询
         */
        List<Role> list = roleMapper.selectForKeywordSearch(keyword);
        /**
         * 封装为PageInfo对象返回
         */
        return new PageInfo<>(list);
    }


    /**
     * 根据id数组去查询数据
     * @param roleIdlist
     * @return
     */
    @Override
    public List<Role> getRoleListByIdList(List<Integer> roleIdlist) {
        // 预期的SQL语句：select * from t_role where id in (1,2,3,4);
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andIdIn(roleIdlist);
        return roleMapper.selectByExample(roleExample);
    }

    @Override
    public void batchRemove(List<Integer> roleIdList) {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andIdIn(roleIdList);
        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public void saveRole(String roleName) {
        roleMapper.insert(new Role(null,roleName));
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public List<Role> getAssignedRoleList(Integer adminId) {
        return roleMapper.selectAssignedRoleList(adminId);
    }

    @Override
    public List<Role> getUnAssignedRoleList(Integer adminId) {
        return roleMapper.selectUnAssignedRoleList(adminId);
    }


    /**
     * 更新管理员和角色的关系
     * @param adminId
     * @param roleIdList
     */
    @Override
    public void updateRelationship(Integer adminId, List<Integer> roleIdList) {
        // 1. 删除全部旧数据
        roleMapper.deleteOldAdminRelationship(adminId);
        // 2. 保存全部新数据
        if (CrowdFundingUtils.collectionEffective(roleIdList))
            roleMapper.insertNewAdminRelationship(adminId,roleIdList);
    }

}
