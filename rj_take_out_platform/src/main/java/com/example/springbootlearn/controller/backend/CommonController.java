package com.example.springbootlearn.controller.backend;

import com.example.springbootlearn.model.Result;
import com.example.springbootlearn.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

/**
 * @description:
 * @author: 王海虹
 * @create: 2022-11-16 11:15
 */
@RestController
@RequestMapping("/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    @Value("${myfile.path}")
    private String uploadPath;

    private String filePath;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Result upload(@RequestParam MultipartFile file) {
        String filename = null;
        if (!file.isEmpty()) {

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            // 获取原文件名
            String OriginalFilename = file.getOriginalFilename();
            String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));
            String nowDate = DateUtil.format(new Date(), DateUtil.FORMAT_DEFAULT_SMALL);
            // 生成新的uuid文件名，防止文件名重复
            filename = nowDate + "\\" + UUID.randomUUID() + suffixName;
            filePath = uploadPath + filename;

            try {
                // 创建上传文件存放的 本地文件夹
                Path path = Paths.get(filePath);
                Path pathCreate = Files.createDirectories(path);
                File localFile = new File(filePath);
                // 将上传文件存放到本地文件夹
                file.transferTo(localFile);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.fail("上传失败");
            }
        }
        return Result.success(file.getOriginalFilename() + "上传成功", filename);
    }

    @GetMapping(value = "/download")
    @ApiOperation("下载文件")
    public Result download(@RequestParam("name") String fileName, HttpServletResponse httpServletResponse) {
        httpServletResponse.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        byte[] array;
        String pathName = uploadPath  + fileName;
        try {
            // 将文件通过 输入流读取
            File file = new File(pathName);
            // 通过 输出流将文件 回传给 浏览器
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            array = FileUtils.readFileToByteArray(file);
            outputStream.write(array);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("下载请求失败: " + pathName);
            return Result.fail("下载失败：" + fileName);
        }
        return Result.success("下载成功：" + fileName);
    }
}
