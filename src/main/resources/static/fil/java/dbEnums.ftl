package ${packageName}.${projectName}.enums;

/**
* @author admin
*/

public enum  DbEnums {
    //列名
    columnName,
    //"_", "-"下划线转中划线名
    replaceName,
    //自增类型
    extra,
    //主键类型
    columnKey,
    //原列明
    oldColumnName,
    //列类型
    dataType,
    //列注释
    columnComment;

    DbEnums() {
    }

    public static String getDbEnums(DbEnums dbEnum){
        return dbEnum.toString();
    }

}
