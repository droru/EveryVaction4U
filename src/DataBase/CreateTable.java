package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateTable {
    public static void createNewTables() {
        // SQLite connection string
        String url = "jdbc:sqlite:src/DataBase/Vacation4You-DB";

        // SQL statement for creating a new table

        //User Table

        String sql = "CREATE TABLE IF NOT EXISTS Users (\n"
                + "     UserName text NOT NULL PRIMARY KEY ,\n"
                + "     FirstName text NOT NULL ,\n"
                + "	    LastName text NOT NULL,\n"
                + " 	Password text NOT NULL,\n"
                + " 	BirthDate DATE,\n"
                + " 	City text NOT NULL\n"
                + ");";


        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("Table Created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
