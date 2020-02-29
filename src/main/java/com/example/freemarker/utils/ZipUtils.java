package com.example.freemarker.utils;

import ch.qos.logback.core.util.FileUtil;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author admin
 */
public class ZipUtils {

    /**
     * 解压zip文件
     * @param filePath
     * @param decompressionPath
     */
    public static void decompressionFile(String filePath,String decompressionPath){
        File timeStampFile = new File(decompressionPath);
        if (!timeStampFile.exists()){
            timeStampFile.mkdirs();
        }
        ZipFile zipFile = new ZipFile(filePath);
        try {
            zipFile.extractAll(decompressionPath);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }


    /**
     * 压缩文件夹
     * @param compressRootPath
     * @param filePath
     * @param compressPath
     */
    public static void compressFile(String compressRootPath,String filePath,String compressPath){
        try {
            File file = new File(compressRootPath);
            if (!file.exists()){
                file.mkdirs();
            }
            ZipFile zipFile = new ZipFile(filePath);
            zipFile.addFolder(new File(compressPath));
            File delFile = new File(compressPath);
            FileUtils.deleteDirectory(delFile);
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }

}
