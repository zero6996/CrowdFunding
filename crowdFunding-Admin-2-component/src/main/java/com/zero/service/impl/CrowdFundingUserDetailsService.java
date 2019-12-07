package com.zero.service.impl;

import com.zero.config.SecurityAdmin;
import com.zero.entity.Admin;
import com.zero.entity.AdminExample;
import com.zero.entity.Auth;
import com.zero.entity.Role;
import com.zero.mapper.AdminMapper;
import com.zero.mapper.AuthMapper;
import com.zero.mapper.RoleMapper;
import com.zero.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zero
 * @Description:
 * Date: Create in 2019/12/2 15:36
 * Modified By: 用户身份验证
 */

@Service
public class CrowdFundingUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AuthMapper authMapper;

    /**
     * 查询数据库返回用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 创建查询对象，去数据库查询数据
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andLoginAcctEqualTo(username);
        List<Admin> list = adminMapper.selectByExample(adminExample);
        // 2. 无效则返回null
        if (!CrowdFundingUtils.collectionEffective(list)){
            return null;
        }
        // 3. 获取admin对象
        Admin admin = list.get(0);
        String userPwd = admin.getUserPwd();
        // 4. 从数据库中查询用户对应 角色和权限信息并封装
        // 4.1 创建存储角色、权限信息的集合
        List<GrantedAuthority> authorityList = new ArrayList<>();
        Integer adminId = admin.getId();
        // 4.2 查询用户对应角色信息列表
        List<Role> roleList = roleMapper.selectAssignedRoleList(adminId);
        // 4.3 遍历角色列表并封装
        for (Role role:roleList) {
            String roleName = "ROLE_" + role.getName();
            authorityList.add(new SimpleGrantedAuthority(roleName));
        }
        // 4.4 查询用户对应权限信息列表
        List<Auth> authList = authMapper.selectAuthListByAdminId(adminId);
        // 4.5 遍历并封装
        for (Auth auth :authList) {
            String authName = auth.getName();
            /*
                特殊处理：解决>A granted authority textual representation is required错误
                问题原因：数据库中权限表有一行查询出来authName为空，导致报错
                解决：进行判断，authName不是有效字符串就不跳过
             */
            if (!CrowdFundingUtils.stringEffective(authName)){
                continue;
            }
            authorityList.add(new SimpleGrantedAuthority(authName));
        }
        // 5. 对Security框架User类进行扩展得到SecurityAdmin类，返回该类
        return new SecurityAdmin(admin,authorityList);
    }
}
