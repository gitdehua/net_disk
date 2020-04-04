package com.bytescat.netdisk.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

@Controller
public class HelloWorld {
    @Value("${file-dir}")
    private String fileDir;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello world!";
    }

    @RequestMapping("/getFiles")
    public String getFiles(Model model) {
        model.addAttribute("name","tom");
        return "hello";
    }
}
