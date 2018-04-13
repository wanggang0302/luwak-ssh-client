package com.luwak.sshclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ChenXc
 * @version V1.0
 * @Date 2018/4/13  17:48
 * @Description TODO(页面跳转controller)
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String toLogin() {
        return "login";
    }
}
