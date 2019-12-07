package com.zero.interceptor;

import com.google.gson.Gson;
import com.zero.entity.Admin;
import com.zero.entity.ResultEntity;
import com.zero.util.CrowdFundingConstant;
import com.zero.util.CrowdFundingUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: zero
 * @Description: 登录拦截器(临时使用
 * Date: Create in 2019/11/13 16:21
 * Modified By:
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 在处理器方法执行前执行
     * @param request
     * @param response
     * @param handler
     * @return 是否放行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        /**
         * 尝试从Session域中获取已经登录的用户对象
         */
        Admin admin = (Admin) session.getAttribute(CrowdFundingConstant.ATTR_NAME_LOGIN_ADMIN);
        /**
         * 如果没有获取已登录Admin对象
         */
        if (admin == null){
            // 进一步判断当前请求是否是异步请求
            boolean checkAsyncRequest = CrowdFundingUtils.checkAsyncRequest(request);
            if (checkAsyncRequest){
                ResultEntity<String> failedResult = ResultEntity.failed(ResultEntity.NO_DATA, CrowdFundingConstant.MESSAGE_ACCESS_DENIED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(new Gson().toJson(failedResult));
                return false;
            }

            /**
             * 将提示消息存入request域中
             */
            request.setAttribute(CrowdFundingConstant.ATTR_NAME_MESSAGE,CrowdFundingConstant.MESSAGE_ACCESS_DENIED);
            /**
             * 转发到登录页面
             */
            request.getRequestDispatcher("/WEB-INF/pages/admin/admin-login.jsp").forward(request,response);
            return false;
        }
        /**
         * 如果有admin对象，则放行继续执行后续操作
         */
        return true;
    }
}
