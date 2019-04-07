package com.flight.carryprice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author wanghaiyuan
 * Date 2019/2/26 15:23.
 */
@Controller
public class IndexController {

    @RequestMapping(value = "index")
    public String loginPage() {
        return "user/login";
    }
}
