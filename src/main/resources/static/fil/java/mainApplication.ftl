package ${packageName};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan("${packageName}.**.mapper")
public class ${projectName}Application {

    public static void main(String[] args) {
        SpringApplication.run(${projectName}Application.class, args);
    }

}