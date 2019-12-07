package com.zero.java.test;

import com.zero.entity.Admin;
import com.zero.entity.Role;
import com.zero.mapper.AdminMapper;
import com.zero.mapper.RoleMapper;
import com.zero.service.api.AdminService;
import com.zero.service.api.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * @Author: zero
 * @Description: Date:Create: in 2019/11/9 16:20
 * Modified By:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CrowdFundingTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 测试数据库连接
     * @throws SQLException
     */
    @Test
    public void testConnection() throws SQLException{
        try (Connection connection = dataSource.getConnection()) {
            System.out.println(connection);
        }
    }

    /**
     * 测试Mybatis整合
     */
    @Test
    public void testMyBatis(){
        for (Admin admin : adminService.findAll()) {
            System.out.println(admin);
        }
    }

    /**
     * 测试事务
     */
//    @Test
//    public void testTransaction(){
//        adminService.updateAdmin();
//    }

    @Test
    public void testSearch(){
        for (Admin admin:adminMapper.selectAdminListByKeyWord("哈")){
            System.out.println(admin);
        }
    }

    @Test
    public void batchSaveAdmin(){
        for (int i = 0;i<30;i++){
            adminMapper.insert(new Admin(null,"loginAcct"+i,"pwd"+i,"userName"+i,"email"+i+"@qq.com","null"));
        }
    }

    @Test
    public void testSaveRole(){
        for (int i = 0;i < 50;i++){
            roleMapper.insert(new Role(null,"testRole"+i));
        }
    }

    @Test
    public void test(){
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(s.format(date));
        // System.out.println(new Date().toString()); // Sun Nov 17 10:53:15 CST 2019
    }

    @Test
    public void  test1(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("jk123");
        System.out.println(encode);
    }

}
