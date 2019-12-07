package com.zero.config;

import com.zero.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Author: zero
 * @Description:
 * Date: Create in 2019/12/2 21:31
 * Modified By: 扩展User类
 *      创建SecurityAdmin对象时调用构造器，传入originalAdmin和authorities
 *      可通过getOriginalAdmin方法获取原始admin对象
 */

public class SecurityAdmin extends User {

    private static final long serialVersionUID = 1L;

    private Admin originalAdmin;

    public SecurityAdmin(Admin admin, Collection<? extends GrantedAuthority> authorities) {
        // 调用父类构造器
        super(admin.getLoginAcct(),admin.getUserPwd(),authorities);
        this.originalAdmin = admin;
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }

}
