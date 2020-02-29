package ${packageName}.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
* @author auto
*/
public class FreeMarkerUtils {

    /**
    * 创建文件
    * @param ftlPath
    * @param createPath
    * @param ftlName
    * @param map
    */
    public static void createFile(String ftlPath, String createPath, String ftlName, Map map){
        Configuration configuration = new Configuration(Configuration.getVersion());
        try {
            configuration.setDirectoryForTemplateLoading(new File(ftlPath));
            configuration.setDefaultEncoding("utf-8");
            Template template = configuration.getTemplate(ftlName);
            Writer out = new FileWriter(new File(createPath));
            template.process(map,out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
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
