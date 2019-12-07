package com.zero.handler;

import com.zero.entity.Auth;
import com.zero.entity.ResultEntity;
import com.zero.entity.Role;
import com.zero.service.api.AuthService;
import com.zero.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author: zero
 * @Description:
 * Date: Create in 2019/11/27 10:07
 * Modified By:
 */

@Controller
@RequestMapping("/assign")
public class AssignHandler {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;


    /**
     * 给角色分配权限
     * @param assignDataMap
     * @return
     */
    @RequestMapping("/do/assign")
    public @ResponseBody ResultEntity<String> doAssignAuth(@RequestBody Map<String,List<Integer>> assignDataMap){
        System.out.println(assignDataMap.toString());
        authService.updateRelationShipBetweenRoleAndAuth(assignDataMap);
        return ResultEntity.successWithoutData();
    }

    /**
     * 根据角色id查询权限信息
     * @param roleId
     * @return
     */
    @RequestMapping("/get/assigned/auth/id/list")
    public @ResponseBody ResultEntity<List<Integer>> getAssignAuthIdList(@RequestParam("roleId") Integer roleId){
        List<Integer> authIdList = authService.getAssignedAuthIdList(roleId);
        return ResultEntity.successWithoutData(authIdList);
    }

    /**
     * 获取权限信息，用于支持zTree
     * @return
     */
    @RequestMapping("/get/all/auth")
    public @ResponseBody ResultEntity<List<Auth>> getAllAuth(){
        List<Auth> authList = authService.getAllAuth();
        return ResultEntity.successWithoutData(authList);
    }

    /**
     * 分配管理员的角色
     * @param roleIdList 要分配的角色列表
     * @param adminId 管理官id
     * @param pageNum 页面
     * @return
     */
    @RequestMapping("/do/assign/role")
    public String doAssignRole(@RequestParam(value = "roleIdList",required = false) List<Integer> roleIdList,
                               @RequestParam("adminId") Integer adminId,
                               @RequestParam("pageNum") String pageNum){
        roleService.updateRelationship(adminId,roleIdList);
        return "redirect:/admin/query/for/search.html?pageNum="+pageNum;
    }

    /**
     * 跳转到分配角色页面
     * @param adminId
     * @param model
     * @return
     */
    @RequestMapping("/to/assign/role/page")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId, Model model){
        // 查询已分配角色
        List<Role> assignedRoleList = roleService.getAssignedRoleList(adminId);
        // 查询未分配角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRoleList(adminId);
        // 存入模型中
        model.addAttribute("assignedRoleList",assignedRoleList);
        model.addAttribute("unAssignedRoleList",unAssignedRoleList);

        return "assign/assign-role";
    }
}
