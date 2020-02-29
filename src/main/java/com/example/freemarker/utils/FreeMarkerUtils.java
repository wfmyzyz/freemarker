package com.example.freemarker.utils;

import com.example.freemarker.config.AutoConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 */
public class FreeMarkerUtils {

    public static void createFile(String ftlPath, String createPath, String ftlName, Map map){
        Configuration configuration = new Configuration(Configuration.getVersion());
        Writer out = null;
        try {
            configuration.setDirectoryForTemplateLoading(new File(ftlPath));
            configuration.setDefaultEncoding("utf-8");
            Template template = configuration.getTemplate(ftlName);
            out = new FileWriter(new File(createPath));
            template.process(map,out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据模板文件覆盖原文件
     * @param decompressionPath
     */
    public static void createFileProcess(String decompressionPath,String projectName,String timeStamp,String packageName,String rootPath,String name) throws IOException {
        int lastIndexOf = packageName.lastIndexOf(".");
        String groupId = packageName.substring(0,lastIndexOf);
        String artifactId = packageName.substring(lastIndexOf+1);
        String javaFtlPath = rootPath + "fil/java";
        String htmlFtlPath = rootPath + "fil/html";
        String javaCreatePath = decompressionPath+"/"+timeStamp+"/"+projectName+"/";
        //生成pom文件
        String pomPath = javaCreatePath + "pom.xml";
        deleteFile(pomPath);
        Map<String,Object> pomMap = new HashMap<>();
        pomMap.put("groupId",groupId);
        pomMap.put("artifactId",artifactId);
        createFile(javaFtlPath,pomPath,"pom.ftl",pomMap);
        //生成yml文件
        String ymlPath = javaCreatePath + "/src/main/resources/application.yml";
        Map<String,Object> ymlMap = new HashMap<>();
        ymlMap.put("projectName",name);
        String ymlPackageFileName = packageName.replace(".", "/");
        ymlMap.put("packageFileName",ymlPackageFileName);
        ymlMap.put("packageName",packageName);
        ymlMap.put("port", AutoConfig.PORT);
        createFile(javaFtlPath,ymlPath,"application.ftl",ymlMap);
        //创建目录
        String projectRoot = javaCreatePath + "src/main/java/"+ymlPackageFileName+"/";
        //创建springboot项目主入口文件
        String oldMainPath = projectRoot + "DemoApplication.java";
        String newMainPath = projectRoot + StringUtils.indexCodeChangeBig(name) +"Application.java";
        File oldMainFile = new File(oldMainPath);
        oldMainFile.delete();
        Map<String,Object> mainMap = new HashMap<>();
        mainMap.put("packageName",packageName);
        mainMap.put("projectName",StringUtils.indexCodeChangeBig(name));
        createFile(javaFtlPath,newMainPath,"mainApplication.ftl",mainMap);

        String projectIntoRoot = projectRoot + name + "/";
        //创建domain目录
        File domain = new File(projectIntoRoot+"domain");
        domain.mkdirs();
        //创建enums目录
        /*File enums = new File(projectIntoRoot+"enums");
        enums.mkdirs();*/
        //创建mapper目录
        File mapper = new File(projectIntoRoot+"mapper/xml");
        mapper.mkdirs();
        //创建controller目录
        File controller = new File(projectIntoRoot+"controller/base");
        controller.mkdirs();
        //创建service目录
        File service = new File(projectIntoRoot+"service/impl");
        service.mkdirs();
        //创建utils目录
        File utils = new File(projectRoot+"utils");
        utils.mkdir();
        //创建config目录
        File config = new File(projectRoot+"config");
        config.mkdir();
        Map<String,Object> utilsMap = new HashMap<>();
        utilsMap.put("packageName",packageName);
        utilsMap.put("projectName",name);
        //创建LayuiBackData.java
        String layuiPath = projectRoot +"utils/LayuiBackData.java";
        createFile(javaFtlPath,layuiPath,"layuiBackData.ftl",utilsMap);
        //创建Msg.java
        String msgPath = projectRoot +"utils/Msg.java";
        createFile(javaFtlPath,msgPath,"msg.ftl",utilsMap);
        //创建StringUtils.java
        String stringUtilsPath = projectRoot +"utils/StringUtils.java";
        createFile(javaFtlPath,stringUtilsPath,"stringUtils.ftl",utilsMap);
        //创建RequestUtils.java
        String requestUtilsPath = projectRoot +"utils/RequestUtils.java";
        createFile(javaFtlPath,requestUtilsPath,"requestUtils.ftl",utilsMap);
        //创建DbEnums.java
//        String enumPath = projectRoot +name+"/enums/DbEnums.java";
//        createFile(javaFtlPath,enumPath,"dbEnums.ftl",utilsMap);
        //创建FreeMarkerUtils.java
//        String freeMarkerUtilsPath = projectRoot +"utils/FreeMarkerUtils.java";
//        createFile(javaFtlPath,freeMarkerUtilsPath,"freeMarkerUtils.ftl",utilsMap);
        //创建DataBaseUtils.java
//        String dataBaseUtilPath = projectRoot +"utils/DatabaseUtils.java";
//        createFile(javaFtlPath,dataBaseUtilPath,"databaseUtils.ftl",utilsMap);
        //创建AutoCodeBuild.java
        String autoCodeBuildPath = projectRoot +"utils/AutoCodeBuild.java";
        createFile(javaFtlPath,autoCodeBuildPath,"autoCodeBuild.ftl",utilsMap);
        //AutoConfig.java
        String autoConfigPath = projectRoot +"config/AutoConfig.java";
        createFile(javaFtlPath,autoConfigPath,"autoConfig.ftl",utilsMap);
        //WebAppConfig.java
        String webAppConfigPath = projectRoot +"config/WebAppConfig.java";
        createFile(javaFtlPath,webAppConfigPath,"webAppConfig.ftl",utilsMap);
        //SwaggerConfig.java
        String SwaggerConfigPath = projectRoot +"config/SwaggerConfig.java";
        createFile(javaFtlPath,SwaggerConfigPath,"swaggerConfig.ftl",utilsMap);
        //RedisConfig.java
        String RedisConfigPath = projectRoot +"config/RedisConfig.java";
        createFile(javaFtlPath,RedisConfigPath,"redisConfig.ftl",utilsMap);
        //创建interceptor目录
        File interceptor = new File(projectRoot+"interceptor");
        interceptor.mkdir();
        //创建ControllerAopInterceptor.java
        String controllerAopInterceptorPath = projectRoot +"interceptor/ControllerAopInterceptor.java";
        createFile(javaFtlPath,controllerAopInterceptorPath,"controllerAopInterceptor.ftl",utilsMap);
        //创建ControllerExceptionHandler.java
        String controllerExceptionHandlerPath = projectRoot +"interceptor/ControllerExceptionHandler.java";
        createFile(javaFtlPath,controllerExceptionHandlerPath,"controllerExceptionHandler.ftl",utilsMap);
        //解压layuiAdmin到项目静态文件夹下
        String layuiAdminPath = javaCreatePath + "src/main/resources/static/";
        ZipUtils.decompressionFile(rootPath+"self/layuiadmin.zip",layuiAdminPath);
        //创建页面静态文件夹
        File pageStaticPath = new File(layuiAdminPath+name);
        pageStaticPath.mkdir();
        //创建页面静态路由config.js
        String configJsPath = layuiAdminPath+name+"/config.js";
        Map configMap = new HashMap();
        configMap.put("port",AutoConfig.PORT);
        configMap.put("projectName","/"+projectName);
        createFile(htmlFtlPath,configJsPath,"config.ftl",configMap);
        //创建后台主界面
        File oldIndexFile = new File(rootPath+"self/index.html");
        File newIndexFile = new File(layuiAdminPath+name+"/index.html");
        FileCopyUtils.copy(oldIndexFile,newIndexFile);
        //创建ftl模板文件
        String ftlPath = javaCreatePath + "src/main/resources/templates/";
        //创建controller模板
        String controllerFtlPath = ftlPath + "controller";
        File controllerFtlFile = new File(controllerFtlPath);
        controllerFtlFile.mkdirs();
        File oldControllerFileFile = new File(rootPath+"self/controller.java.ftl");
        File newControllerFileFile = new File(controllerFtlPath+"/controller.java.ftl");
        FileCopyUtils.copy(oldControllerFileFile,newControllerFileFile);
        //创建页面模板
        String pageFtlPath = ftlPath + "html";
        File pageFtlFile = new File(pageFtlPath);
        pageFtlFile.mkdirs();
        File oldPageFileFile = new File(rootPath+"self/list.html.ftl");
        File newPageFileFile = new File(pageFtlPath+"/list.html.ftl");
        FileCopyUtils.copy(oldPageFileFile,newPageFileFile);
    }

    /**
     * 删除文件
     * @param file
     */
    public static void deleteFile(String file){
        File oldPomFile = new File(file);
        oldPomFile.delete();
    }
}
