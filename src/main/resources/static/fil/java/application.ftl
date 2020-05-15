server:
  port: ${port}
  servlet:
    context-path: /${projectName}
  tomcat:
    accesslog:
      buffered: true
      directory: /home/logs/tomcat
      enabled: true
      file-date-format: .yyyy-MM-dd
      pattern: '%t | %{X-Real-IP}i | %b | %B | %H | %l | %m | %p | %q | %r | %s | %S | %u | %U | %v | %D | %T | %{Cookie}i | %{User-Agent}i | %{a}r'
      prefix: access_log
      rename-on-rotate: false
      request-attributes-enabled: false
      rotate: true
      suffix: .log
spring:
  application:
    name: support
  datasource:
    name: test
    url: jdbc:mysql://localhost:3306/${projectName}?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&allowMultiQueries=true
    username: root
    password: root
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
  redis:
    database: 0
    host: 127.0.0.1
    port: 6379
    password:
    lettuce:
      pool:
        max-active: 8
        max-wait: -1
        max-idle: 8
        min-idle: 0
    timeout: 5000
mybatis-plus:
  mapper-locations: classpath*:${packageFileName}/mapper/xml/*.xml
  configuration:
    map-underscore-to-camel-case: true
logging:
  level:
    ${packageName}.${projectName}.mapper: debug
