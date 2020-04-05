package com.bytescat.netdisk.controller;

import com.bytescat.netdisk.pojo.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

@Controller
public class IndexController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${file-dir}")
    String fileDir;

    @RequestMapping("/")
    public String home() {
        return "redirect:/home/";
    }

    @RequestMapping("/home/**")
    public ModelAndView homeController(HttpServletRequest req, HttpServletResponse res, @RequestParam(value = "get", required = false) String get) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("path", req.getServletPath());

        File file = new File(fileDir + req.getServletPath().substring(6));
        if (file.isDirectory()) {
            directory(modelAndView, file);
        } else if (file.isFile()) {
            if (get != null) {
                try {
                    res.setContentType("application/octet-stream");
                    res.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
                    OutputStream out = res.getOutputStream();
                    InputStream in = new FileInputStream(file);
                    byte[] buff = new byte[1024 * 10];// 指定缓冲区的大小
                    int len = 0;
                    while ((len = in.read(buff)) > -1) {
                        out.write(buff, 0, len);
                    }
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            fileDetail(modelAndView, file); // 文件详情页
            modelAndView.setViewName("fileDetail");
        } else {
            modelAndView.setViewName("error/404");
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
        }
        return modelAndView;
    }

    //    读取文件列表
    public void directory(ModelAndView modelAndView, File file) {
        modelAndView.setViewName("directory");

        File[] files = file.listFiles();
        List<FileInfo> fileList = new LinkedList<>();
        List<FileInfo> dirList = new LinkedList<>();
        for (File f : files) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setIsDirectory(f.isDirectory());
            fileInfo.setLastModified(f.lastModified());

            if (f.isFile()) {
                fileInfo.setSize(f.length());
                fileInfo.setName(f.getName());
                fileList.add(fileInfo);
            } else {
                fileInfo.setName(f.getName() + "/");
                dirList.add(fileInfo);
            }
        }
        modelAndView.addObject("fileList", fileList);
        modelAndView.addObject("dirList", dirList);
    }

    //    文件详情
    public void fileDetail(ModelAndView modelAndView, File file) {
        modelAndView.setViewName("fileDetail");
        modelAndView.addObject("fileName", file.getName());
        modelAndView.addObject("fileSize", file.length());
        modelAndView.addObject("lastModified", file.lastModified());
    }
}
