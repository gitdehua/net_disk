package com.bytescat.netdisk.controller;

import com.bytescat.netdisk.pojo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${file-dir}")
    String fileDir;

    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) {
        // 获取新的文件路径
        StringBuilder newPath = new StringBuilder(fileDir);
        if (path.startsWith("/home/")) {
            newPath.append(path.replace("/home/", ""));
        }
        newPath.append(file.getOriginalFilename());

        try {
            file.transferTo(new File(newPath.toString()));
        } catch (java.io.FileNotFoundException e) { // 系统找不到指定的路径
            return new Result(Result.ResStatus.FILE_NOT_FOUND);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return new Result(Result.ResStatus.SERVER_ERROR);
        }
        return new Result(Result.ResStatus.OK).setData(newPath.toString());  //返回新文件路径
    }
}
