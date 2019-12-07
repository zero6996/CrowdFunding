package com.zero.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zero
 * @Description: 声明一些常量
 * Date: Create in 2019/11/13 15:06
 * Modified By:
 */

public class CrowdFundingConstant {

    public static final String ATTR_NAME_MESSAGE = "MESSAGE";
    public static final String ATTR_NAME_LOGIN_ADMIN = "LOGIN-ADMIN";
    public static final String ATTR_NAME_PAGE_INFO = "PAGE-INFO";

    public static final String MESSAGE_LOGIN_FAILED = "登录账号或密码不正确！请核对后再登录！";
    public static final String MESSAGE_CODE_INVALID = "非有效字符串,请核对后再操作！";
    public static final String MESSAGE_ACCESS_DENIED = "用户未登录！请登录后在操作";
    public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "登录账号被占用,请重新设定！";
    public static final String MESSAGE_ROLE_NAME_ALREADY_IN_USE = "角色名已经被使用,请重新设定！";

    public static final Map<String,String> EXCEPTION_MESSAGE_MAP = new HashMap<>();

    static {
        EXCEPTION_MESSAGE_MAP.put("java.lang.ArithmeticException","系统在进行数学运算时发生错误");
        EXCEPTION_MESSAGE_MAP.put("java.lang.ArithmeticException","系统在运行时发生错误");
        EXCEPTION_MESSAGE_MAP.put("com.zero.exception.LoginException","登录过程中发生错误");
        EXCEPTION_MESSAGE_MAP.put("org.springframework.security.access.AccessDeniedException","您现在不具备访问当前功能的权限！请联系超级管理员。");
    }

}
