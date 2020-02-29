package com.example.freemarker.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 */
public class DownLoadUtils {

    public static String suffix = ".zip";

    /**
     * 下载springboot项目
     * @param url
     * @param name
     * @param packageName
     * @throws Exception
     */
    public static String downLoadSpringBoot(String url,String name,String packageName,String rootPath) throws Exception {
        CloseableHttpClient httpClient = PersonHttpClientUtils.createAcceptSelfSignedCertificateClient();
        URIBuilder builder = new URIBuilder(url);
        List<NameValuePair> pairs = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        int lastIndexOf = packageName.lastIndexOf(".");
        String groupId = packageName.substring(0,lastIndexOf);
        String artifactId = packageName.substring(lastIndexOf+1);
        map.put("baseDir",name);
        map.put("groupId",groupId);
        map.put("artifactId",artifactId);
        map.put("packageName",packageName);
        for(Map.Entry<String,String> entry : map.entrySet()){
            pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        builder.setParameters(pairs);
        HttpGet get = new HttpGet(builder.build());
        CloseableHttpResponse response = httpClient.execute(get);
        long timeMillis = System.currentTimeMillis();
        String timeStampFile = rootPath + "zip/"+ timeMillis;
        File timeFile = new File(timeStampFile);
        if (!timeFile.exists()){
            timeFile.mkdirs();
        }
        String createPath = rootPath + "zip/"+ timeMillis + "/" + name + suffix;
        try {
            StreamDownLoad(response,createPath);
        }catch (IOException e){
            return null;
        }
        return createPath;
    }

    /**
     * 流下载
     * @param response
     * @param createPath
     */
    private static void StreamDownLoad(CloseableHttpResponse response,String createPath) throws IOException {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            inputStream = response.getEntity().getContent();
            File file = new File(createPath);
            outputStream = new FileOutputStream(file);
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(bytes))!= -1){
                bufferedOutputStream.write(bytes,0,len);
            }
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedInputStream != null){
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null){
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
