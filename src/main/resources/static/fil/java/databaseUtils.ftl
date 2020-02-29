package ${packageName}.utils;

import ${packageName}.config.AutoConfig;
import ${packageName}.${projectName}.enums.DbEnums;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author admin
*/
public class DatabaseUtils {

    static {
        try {
        Class.forName(AutoConfig.DATA_DRIVER);
        } catch (ClassNotFoundException e) {
        e.printStackTrace();
        }
    }

    /**
    * 获取数据库连接
    */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(AutoConfig.DATA_URL, AutoConfig.DATA_USERNAME, AutoConfig.DATA_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
    * 关闭数据库连接
    * @param connection
    */
    public static void closeConnection(Connection connection){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
    * 获取数据表结构数据
    * @param tableName
    * @return
    */
    public static List<Map<String,String>> getColumnName(String tableName){
        List<Map<String,String>> columnNames = new ArrayList<>();
            Connection connection = getConnection();
            PreparedStatement preparedStatement = null;
            String tableSql = "select * from information_schema.COLUMNS where TABLE_SCHEMA = (select database()) and TABLE_NAME= '"+tableName+"' ORDER BY ORDINAL_POSITION";
            try {
                preparedStatement = connection.prepareStatement(tableSql);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    Map<String,String> map = new HashMap<>();
                    map.put(DbEnums.getDbEnums(DbEnums.columnName),resultSet.getString("COLUMN_NAME"));
                    map.put(DbEnums.getDbEnums(DbEnums.oldColumnName),resultSet.getString("COLUMN_NAME"));
                    map.put(DbEnums.getDbEnums(DbEnums.dataType),resultSet.getString("DATA_TYPE"));
                    map.put(DbEnums.getDbEnums(DbEnums.columnComment),resultSet.getString("COLUMN_COMMENT"));
                    map.put(DbEnums.getDbEnums(DbEnums.extra),resultSet.getString("EXTRA"));
                    map.put(DbEnums.getDbEnums(DbEnums.columnKey),resultSet.getString("COLUMN_KEY"));
                    columnNames.add(map);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                closeConnection(connection);
            }
            return columnNames;
    }

}
