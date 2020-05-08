package org.example;
import java.sql.*;

/**
 * @author yu
 * @date 2020/4/9
 */
public class JdbcConnection {
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/demo";
    static final String USER = "root";
    static final String PASSWORD = "root";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("加载驱动失败!", e);
        }
    }

    /**
     * 返回一个连接
     * @return
     */
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("连接失败", e);
        }
        return conn;
    }

    public static void main(String[] args) {
        //连接数据库
        Connection conn=getConnection();
        //Statement，预编译的SQL语句的对象
        PreparedStatement statement = null;
        //结果集
        ResultSet resultSet = null;
        String sql = "select * from dept where pk_id=?";
        try {
            statement = conn.prepareStatement(sql);
            //设置参数
            statement.setString(1, "3");
            //执行Sql查询
            resultSet = statement.executeQuery();
            //遍历输出结果
            while (resultSet.next()) {
                System.out.println(resultSet.getString("deptname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //一定要释放资源.....
            //1.resultSet.close() 2.statement.close() 3.conn.close
        }
    }
}