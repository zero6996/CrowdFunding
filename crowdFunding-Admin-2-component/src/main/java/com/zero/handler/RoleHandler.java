package com.zero.handler;

import com.github.pagehelper.PageInfo;
import com.zero.entity.ResultEntity;
import com.zero.entity.Role;
import com.zero.service.api.RoleService;
import com.zero.util.CrowdFundingConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zero
 * @Description:
 * Date: Create in 2019/11/20 20:28
 * Modified By:
 */

@RestController
@RequestMapping("/role")
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    /**
     * 关键词搜索+分页功能
     * @param pageNum 当前页码
     * @param pageSize 每页显示数据量
     * @param keyword 关键词
     * @param model 模型，用以返回数据
     * @return 返回成功ajax响应结果
     */
    @PreAuthorize("hasAuthority('role:get')")
    @RequestMapping("/search/by/keyword")
    public ResultEntity<PageInfo<Role>> byKeywordSearch(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            Model model){
        PageInfo<Role> info = roleService.queryForKeywordWithPage(pageNum,pageSize,keyword);
        return ResultEntity.successWithoutData(info);
    }


    /**
     * 根据id查询数据
     * @param roleIdlist
     * @return
     */
    @RequestMapping("/get/list/by/id/list")
    public ResultEntity<List<Role>> getRoleListByIdList(@RequestBody List<Integer> roleIdlist){
        List<Role> roleList = roleService.getRoleListByIdList(roleIdlist);
        return ResultEntity.successWithoutData(roleList);
    }

    /**
     * 根据id数组批量删除数据
     * @param roleIdList 需要删除的id数组
     * @return 返回删除结果
     */
    @RequestMapping("/batch/remove")
    public ResultEntity<String> batchRemove(@RequestBody List<Integer> roleIdList){
        try {
            roleService.batchRemove(roleIdList);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(null,e.getMessage());
        }
    }

    @RequestMapping("/save/role")
    public ResultEntity<String> saveRole(@RequestParam("roleName") String roleName){
        try {
            roleService.saveRole(roleName);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                return ResultEntity.failed(null,CrowdFundingConstant.MESSAGE_ROLE_NAME_ALREADY_IN_USE);
            }
            return ResultEntity.failed(null,e.getMessage());
        }
    }

    @RequestMapping("/update/role")
    public ResultEntity<String> updateRole(Role role){
        try {
            roleService.updateRole(role);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                return ResultEntity.failed(null,CrowdFundingConstant.MESSAGE_ROLE_NAME_ALREADY_IN_USE);
            }
            return ResultEntity.failed(null,e.getMessage());
        }
    }
}