package com.zero.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zero
 * @Description: 拒绝访问后的操作
 * Date: Create in 2019/12/3 10:51
 * Modified By:
 */


public class CrowdFundingAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        request.setAttribute("exception",exception);
        request.getRequestDispatcher("/WEB-INF/pages/system-error.jsp").forward(request,response);
    }
}
