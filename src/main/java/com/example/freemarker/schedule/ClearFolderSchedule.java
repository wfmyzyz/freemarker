package com.example.freemarker.schedule;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class ClearFolderSchedule {

    private static final Logger log = LoggerFactory.getLogger(ClearFolderSchedule.class);

    @Value("${download.rootpath}")
    public String rootPath;
    @Value("${download.savepath}")
    public String savePath;

    @Scheduled(cron = "0 1 0 * * ?")
    public void clearOldFolder(){
        log.info("---        开始删除昨日生成文件夹           ---");
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = dateFormatter.format(localDate);
        LocalDateTime localDateTime = LocalDateTime.parse(format+" 00:00:00",dateTimeFormatter);
        long time = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String rootPath = "src/main/resources/static/";
        String zipStrPath = rootPath + "zip/";
        String decompressionStrPath = rootPath + "decompression/";
        File zipFolder = new File(zipStrPath);
        File decompressionFolder = new File(decompressionStrPath);
        File saveFolder = new File(savePath);
        deleteFolder(zipFolder,time);
        deleteFolder(decompressionFolder,time);
        deleteFolder(saveFolder,time);
        log.info("---        删除昨日生成文件夹结束           ---");
    }


    /**
     * 删除文件夹
     * @param fileList
     * @param time
     */
    private void deleteFolder(File fileList,long time){
        if (fileList.listFiles().length > 0){
            for (File file:fileList.listFiles()){
                long i = Long.parseLong(file.getName());
                if (i < time){
                    try {
                        FileUtils.deleteDirectory(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
