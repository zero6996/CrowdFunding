package com.zero.handler;

import com.github.pagehelper.PageInfo;
import com.zero.entity.Admin;
import com.zero.entity.ResultEntity;
import com.zero.service.api.AdminService;
import com.zero.util.CrowdFundingConstant;
import com.zero.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: zero
 * @Description:
 * Date:Create: in 2019/11/10 19:00
 * Modified By:
 */

@Controller
@RequestMapping("/admin")
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    /**
     * 查询所有的admin信息
     * @param model
     * @return
     */
    @RequestMapping("/get/All")
    public String getAll(Model model){
        List<Admin> list = adminService.findAll();
        for (Admin admin:list){
            System.out.println(admin);
        }
        model.addAttribute("list",list);
        return "admin/admin-target";
    }

    /**
     * 做登录操作，现已转交给security框架处理
     * @param loginAcct
     * @param userPwd
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/do/login")
    public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPwd") String userPwd, Model model, HttpSession session){
        /**
         * 调用Service的login方法执行登录业务逻辑，返回查询到的Admin对象
         */
        Admin admin = adminService.login(loginAcct,userPwd);
        if (admin == null){
            model.addAttribute(CrowdFundingConstant.ATTR_NAME_MESSAGE, CrowdFundingConstant.MESSAGE_LOGIN_FAILED);
            return "admin/admin-login";
        }
        session.setAttribute(CrowdFundingConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        return "redirect:/admin/to/main/page.html";
    }

    /**
     * 退出登录方法
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logOut(HttpSession session){
        session.invalidate();
        return "redirect:/index.html";
    }


    /**
     * 查询分页数据
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param keyword 查询关键词
     * @param model
     * @return
     */
    @RequestMapping("/query/for/search")
    public String queryForSearch(
            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
            @RequestParam(value = "keyword",defaultValue = "")String keyword,
            Model model){
        PageInfo<Admin> adminPageInfo = adminService.queryForKeyWordSearch(pageNum,pageSize,keyword);
        model.addAttribute(CrowdFundingConstant.ATTR_NAME_PAGE_INFO,adminPageInfo);
        return "admin/admin-page";
    }

    /**
     * 使用@ResponseBody注解返回json数据，使用此注解后将不经过视图解析器
     * @return
     */
    @RequestMapping("/batch/remove")
    public @ResponseBody ResultEntity<String> batchRemove(@RequestBody List<Integer> adminIdList){
        try {
            adminService.batchRemove(adminIdList);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(null,e.getMessage());
        }
    }


    /**
     * 保存用户方法
     * @param admin
     * @return
     */
    @RequestMapping("/save")
    public String saveAdmin(Admin admin){
        try {
            /**
             * 调用保存用户方法
             */
            adminService.saveAdmin(admin);
        } catch (Exception e) {
            /**
             * 提交表单时，如果用户名重复会抛异常，在这里进行捕获后设置异常原因
             */
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                throw new RuntimeException(CrowdFundingConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
        /**
         * 操作完毕后跳转到分页页面的最后一页
         */
        return "redirect:/admin/query/for/search.html";
    }

    /**
     * 跳转到修改用户信息页面
     * @param AdminId 需要修改的用户ID
     * @param model 使用model回显数据
     * @return 视图名称
     */
    @RequestMapping("/to/edit/page")
    public String toEditPage(@RequestParam("adminID") Integer AdminId, Model model){
        Admin admin = adminService.getAdminByID(AdminId);
        model.addAttribute("admin",admin);
        return "admin/admin-edit";
    }

    /**
     * 更新用户信息
     * @param admin 需要更新的用户
     * @param pageNum 更新用户所在的页码
     * @return 更新后返回用户列表
     */
    @RequestMapping("update")
    public String updateAdmin(Admin admin,@RequestParam("pageNum") String pageNum){
        try {
            adminService.updateAdmin(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                throw new RuntimeException(CrowdFundingConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }else if (e instanceof RuntimeException){
                throw new RuntimeException(CrowdFundingConstant.MESSAGE_CODE_INVALID);
            }
        }
        return "redirect:/admin/query/for/search.html?pageNum="+pageNum;
    }
}
