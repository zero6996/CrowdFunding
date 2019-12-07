package com.zero.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: zero
 * @Description: Date:Create: in 2019/11/11 20:37
 * Modified By:
 */

@Controller
public class PortalHandler {

    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }
}
