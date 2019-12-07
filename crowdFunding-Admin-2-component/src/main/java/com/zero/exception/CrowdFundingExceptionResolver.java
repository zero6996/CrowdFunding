package com.zero.exception;

import com.google.gson.Gson;
import com.zero.entity.ResultEntity;
import com.zero.util.CrowdFundingConstant;
import com.zero.util.CrowdFundingUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zero
 * @Description:
 * Date: Create in 2019/11/13 15:51
 * Modified By:
 */


@ControllerAdvice
public class CrowdFundingExceptionResolver {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView catchException(Exception exception,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        // 1. 对当前请求进行检查
        boolean requestResult = CrowdFundingUtils.checkAsyncRequest(request);
        // 2. 如果是异步请求
        if (requestResult){
            // 根据异常类型在常量中的映射，使用比较友好的文字显示错误提示消息
            String exceptionClassName = exception.getClass().getName();
            String message = CrowdFundingConstant.EXCEPTION_MESSAGE_MAP.get(exceptionClassName);
            if (message == null){
                message = "系统未知错误！";
            }
            // 3. 创建ResultEntity对象
            ResultEntity<String> failedResult = ResultEntity.failed(ResultEntity.NO_DATA, "请求异常:"+message);
            // 4. 转换为JSON格式并响应数据返回给浏览器
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new Gson().toJson(failedResult));
            return null;
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception",exception);
        mav.setViewName("system-error");
        return mav;
    }
}
