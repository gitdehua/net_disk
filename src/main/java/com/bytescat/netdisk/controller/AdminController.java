package com.bytescat.netdisk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
public class AdminController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/login")
    public String login(HttpSession session) {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(HttpSession session, @Value("${login-password}") String pwd, @RequestParam("password") String password, @RequestParam(value = "callback", required = false) String callback, HttpServletResponse res) {
        if (pwd.equals(password)) {
            session.setAttribute("isLoged", true);

            try {
                res.sendRedirect(callback == null ? "/home/" : callback);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        return "login";
    }

    @RequestMapping("/:admin")
    public String home() {
        return "admin/admin";
    }
}
