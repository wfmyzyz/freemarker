package com.example.freemarker.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 */
@RestController
@RequestMapping("springboot")
public class CreateController {

    String rootPath = "src/main/resources/static/";

    @RequestMapping("createFile")
    public String createFile() throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File(rootPath+"/fil"));
        configuration.setDefaultEncoding("utf-8");
        Template template = configuration.getTemplate("hello.ftl");
        Map dataModel = new HashMap();
        dataModel.put("hello","hello world!");
        Writer out = new FileWriter(new File(rootPath+"hello.html"));
        template.process(dataModel,out);
        out.close();
        return "123";
    }
}
