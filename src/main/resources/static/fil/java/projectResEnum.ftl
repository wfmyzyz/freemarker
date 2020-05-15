package ${packageName}.enums;

/**
* @author auto
*/
public enum  ResponseEnum {

    /**
    * API调用
    */
    API_ERROR(210,"服务调用异常！"),

    /**
    * 通用
    */
    SUCCESS(200,"成功！"),
    FAIL(0,"失败！"),
    LOGIN(206,"请登录！"),
    NONE_AUTHORITY(208,"没有权限！");

    private final int code;
    private final String msg;

    ProjectResEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }
}
