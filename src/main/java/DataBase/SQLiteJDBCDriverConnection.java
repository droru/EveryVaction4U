package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteJDBCDriverConnection {
    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       // DriverManager.getConnection("jdbc:sqlite:D:\\db\\my-db.sqlite");
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:src/main/java/DataBase/Vacation4You-DB";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    /**
     * @param args the command line arguments
     */


    public static void main(String[] args) throws SQLException {
        connect();
    }
}