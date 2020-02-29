package com.example.freemarker.controller;

import com.example.freemarker.utils.DownLoadUtils;
import com.example.freemarker.utils.FreeMarkerUtils;
import com.example.freemarker.utils.StringUtils;
import com.example.freemarker.utils.ZipUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;


/**
 * @author admin
 */
@RestController
@RequestMapping("springboot")
public class DownloadController {

    private static final Logger log = LoggerFactory.getLogger(DownloadController.class);

    @Value("${download.url}")
    public String url;
    @Value("${download.rootpath}")
    public String rootPath;
    @Value("${download.savepath}")
    public String savePath;

    @RequestMapping("download")
    public String downloadSpringBootFile(@RequestParam("name") String name,@RequestParam("packageName") String packageName) throws Exception {
        if (Strings.isBlank(name) || Strings.isBlank(packageName)){
            return "500";
        }
        long l = System.currentTimeMillis();
        log.info("开始执行下载操作！！！");
        //下载springboot项目
        String file = DownLoadUtils.downLoadSpringBoot(url, name, packageName,rootPath);
        String[] split = file.split("/");
        String projectName = split[split.length-1].substring(0,split[split.length-1].lastIndexOf("."));
        String timeStamp = split[split.length-2];
        log.info("解压文件夹！！！");
        //解压文件夹
        String decompressionPath = rootPath+"decompression/";
        ZipUtils.decompressionFile(file,decompressionPath+timeStamp+"/");
        File delFile = new File(file);
        delFile.delete();
        log.info("生成覆盖文件！！！");
        //生成覆盖文件
        FreeMarkerUtils.createFileProcess(decompressionPath,projectName,timeStamp,packageName,rootPath,name);
        log.info("压缩文件夹！！！");
        //压缩文件夹
        String compressRootPath = savePath +timeStamp;
        String compressZipPath =  savePath +timeStamp+ "/"+name+".zip";
        String compressPath = decompressionPath + timeStamp + "/" +name + "/";
        ZipUtils.compressFile(compressRootPath,compressZipPath,compressPath);
        long time = System.currentTimeMillis() - l;
        log.info("花费时间："+time);
        return "download/"+timeStamp+"/"+name+".zip";
    }
}
