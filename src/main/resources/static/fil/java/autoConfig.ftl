package ${packageName}.config;

/**
* @author auto
*/
public class AutoConfig {
    /**
    * 数据库配置
    */
    public static final String DATA_URL = "jdbc:mysql://localhost:3306/${projectName}?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull";
    public static final String DATA_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DATA_USERNAME = "root";
    public static final String DATA_PASSWORD = "root";

    /**
    * 项目名
    */
    public static final String PROJECT_NAME = "${projectName}";
    public static final String PACK_NAME = "${packageName}";
}
