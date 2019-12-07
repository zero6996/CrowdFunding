package com.zero.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zero.entity.Admin;
import com.zero.entity.AdminExample;
import com.zero.mapper.AdminMapper;
import com.zero.service.api.AdminService;
import com.zero.util.CrowdFundingConstant;
import com.zero.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zero
 * @Description: Date:Create: in 2019/9/22 14:01
 * Modified By:
 */

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Admin> findAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public void updateAdmin(Admin admin) {
        try {
            admin.setUserPwd(CrowdFundingUtils.md5(admin.getUserPwd()));
            adminMapper.updateByPrimaryKey(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof RuntimeException){
                throw new RuntimeException(CrowdFundingConstant.MESSAGE_CODE_INVALID);
            }
        }
    }

    /**
     * 管理员登录方法
     * @param loginAcct 管理员账号
     * @param userPwd 管理员密码
     * @return 查询到的管理员对象
     */
    @Override
    public Admin login(String loginAcct, String userPwd) {
        AdminExample adminExample = new AdminExample();
        /**
         * 封装查询条件：根据用户名去查询数据库
         */
        adminExample.createCriteria().andLoginAcctEqualTo(loginAcct);
        List<Admin> list = adminMapper.selectByExample(adminExample);
        /**
         * 判断该集合是否无效，无效直接返回null
         */
        if (!CrowdFundingUtils.collectionEffective(list)){ return null; }
        /**
         * 获取查询到的集合对象
         */
        Admin admin = list.get(0);
        /**
         * 确认该对象不为null
         */
        if (admin == null){ return null; }
        /**
         * 获取数据库中的密文密码，然后对传入的用户密码进行加密后对比数据库中密文
         */
        String userPwdDataBase = admin.getUserPwd();
        String userPwdBrowser = passwordEncoder.encode(userPwd);
        /**
         * 如果两个密码相等则说明可以登录成功，返回Admin对象
         */
        if (Objects.equals(userPwdBrowser,userPwdDataBase)){ return admin; }
        return null;
    }

    /**
     * 根据关键词查询Admin信息
     * @param pageNum 当前页
     * @param pageSize 每页显示数
     * @param keyword 查询关键词
     * @return 封装了页面信息的对象
     */
    @Override
    public PageInfo<Admin> queryForKeyWordSearch(Integer pageNum, Integer pageSize, String keyword) {
        /**
         * 1. 调用PageHelper的工具方法，开启分页功能
         */
        PageHelper.startPage(pageNum,pageSize);
        /**
         * 2. 执行分页查询
         */
        List<Admin> admins = adminMapper.selectAdminListByKeyWord(keyword);
        /**
         * 3. 将list封装到PageInfo对象中
         */
        return new PageInfo<>(admins);
    }


    /**
     * 批量删除
     * QBC：Query By Criteria
     * @param adminIdList
     */
    @Override
    public void batchRemove(List<Integer> adminIdList) {
        AdminExample adminExample = new AdminExample();
        /**
         * 针对要查询的字段封装具体的查询条件：判断ID在adminIdList中的
         */
        adminExample.createCriteria().andIdIn(adminIdList);
        /**
         * 执行操作时把封装了查询条件的对象传入
         */
        adminMapper.deleteByExample(adminExample);
    }


    @Override
    public void saveAdmin(Admin admin) {
        admin.setUserPwd(passwordEncoder.encode(admin.getUserPwd()));
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        admin.setCreateTime(s.format(new Date()));
        adminMapper.insert(admin);
    }

    /**
     * 根据ID获取用户
     * @param adminID
     * @return
     */
    @Override
    public Admin getAdminByID(Integer adminID) {
        return adminMapper.selectByPrimaryKey(adminID);
    }

}
