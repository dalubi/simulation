package com.ices.simulation.controller.util;

import com.ices.simulation.service.XMLDealService;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Value("${file.filepath}")
    private String filepath;

    @Autowired
    XMLDealService xmlservice;

    @RequestMapping(value = "/upload")
    //接收bpmn文件，并将其转化为多个状态图文件
    public String upload(@RequestParam("file")MultipartFile file) throws DocumentException {
        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为：" + suffixName);

            // 设置文件存储路径，上传到bpmnGraph下面
            String path = filepath + fileName;
            File dest = new File(path);

            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入

            //解析状态图
            xmlservice.parse(path);

            //转化为状态图之后，转到显示有多少个状态图的controller下去
            return "redirect:/states";

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //上传失败，重新上传
        return "redirect:/upload";
    }
}
