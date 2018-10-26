package DataBase;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class createDatabase {
    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:src/DataBase/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static boolean isDatabaseExist(String DatabaseName){
        File tmpDir = new File("src/DataBase/"+DatabaseName);
        return tmpDir.exists();
    }
}
