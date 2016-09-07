package project2.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Steve on 04-Sep-16.
 */
public class DBUtil {

    private static Connection con;

    public static Connection getConnection(){

        // check if connection has already been initialized
        if (con != null) {
            return con;
        }
        else {
            try {
                String driver = "com.mysql.jdbc.Driver";
                String url = "jdbc:mysql://localhost:3306/project2";
                String user = "root";
                String password = "nightfall";
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return con;
    }
}
