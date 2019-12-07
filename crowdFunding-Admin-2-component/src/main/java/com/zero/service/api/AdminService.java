package com.zero.service.api;

import com.github.pagehelper.PageInfo;
import com.zero.entity.Admin;

import java.util.List;

/**
 * @Author: zero
 * @Description: Date:Create: in 2019/9/22 14:00
 * Modified By:
 */



public interface AdminService {

    List<Admin> findAll();

    void updateAdmin(Admin admin);

    Admin login(String loginAcct, String userPwd);

    /**
     * 根据关键词查询数据
     * @param keyword
     * @return
     */
    PageInfo<Admin> queryForKeyWordSearch(Integer pageNum,Integer pageSize,String keyword);

    void batchRemove(List<Integer> adminIdList);

    void saveAdmin(Admin admin);

    Admin getAdminByID(Integer adminID);
}
