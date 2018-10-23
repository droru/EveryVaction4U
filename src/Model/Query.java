package Model;

import java.sql.*;


public class Query
{
    private static String UserName;
    private  static  String FirstName;
    private  static String LastName;
    private  static String Password;
    private  static String BirthDate;
    private  static String City;
    private  static String Email;
    private static String Picture;
    public static Connection conn;

    private static Connection connect() throws SQLException {
        // SQLite connection string
      //  DriverManager.getConnection("jdbc:sqlite:D:\\db\\my-db.sqlite");
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:sqlite:src/DataBase/Vacation4You-DB";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // return: 0 if the insert succeed else 1

    public static int insert(User user)
    {
        InitUser(user);

        String sql = "INSERT INTO Users(UserName,FirstName,LastName,Password,BirthDate,City,Email,Picture) VALUES(?,?,?,?,?,?,?,?)";
        try (
                Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, UserName);
            pstmt.setString(2, FirstName);
            pstmt.setString(3,LastName);
            pstmt.setString(4,Password);
            pstmt.setString(5,BirthDate);
            pstmt.setString(6,City);
            pstmt.setString(7,Email);
            pstmt.setString(8,Picture);

            pstmt.executeUpdate();
            //conn.close();

            return 0 ;
        } catch (SQLException e) {
            return 1 ;
        }
    }

    private static void InitUser(User user) {
        UserName=user.getUserName();
        FirstName=user.getFirstName();
        LastName=user.getLastName();
        Password=user.getPassword();
        BirthDate=user.getBirthDate();
        City=user.getCity();
        Email=user.getEmail();
        Picture=user.getProfilePicPath();
    }


    public static  User search(String username) throws SQLException {
       // DriverManager.getConnection("jdbc:sqlite:D:\\db\\my-db.sqlite");

        String sql = "SELECT UserName,FirstName,LastName,Password,BirthDate,City,Email,Picture "
                + "FROM Users WHERE UserName = ?";

        return SearcByValue(username, sql);
    }

    private static User SearcByValue(String username, String sql) {
        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1 , username);
            //
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
               // conn.close();
                return new User(rs.getString("UserName"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Password"), rs.getString("BirthDate"), rs.getString("City"),rs.getString("Email"),rs.getString("Picture"));
            }
            else {
                //conn.close();
                return null;
            }
            // loop through the result set

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public static  User search_by_pass(String password) throws SQLException
    {
        // DriverManager.getConnection("jdbc:sqlite:D:\\db\\my-db.sqlite");

        String sql = "SELECT UserName,FirstName,LastName,Password,BirthDate,City,Email,Picture "
                + "FROM Users WHERE Password = ?";

        return SearcByValue(password, sql);
    }

    // return: 0 if the delete succeed else 1 (The username doesn't exist in the table or the connection to db  doesn't succeed )

    public static int delete(String user) throws SQLException {
        String sql = "DELETE FROM Users WHERE UserName = ?";

        User u = search(user);
        if(u == null)
            return 1 ;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, user);
            // execute the delete statement
            pstmt.executeUpdate();
            //conn.close();
            return 0;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return 1;
        }
    }


    // return: 0 if the update succeed else 1 (One or more fields are not valid or the connection to db  doesn't succeed )

public static int update(User user) throws SQLException {
    {
        InitUser(user);
        if(search(UserName)!= null )
        {
            delete(UserName);
            insert(user);
            return 0;
        }
        else
        return 1;
    }
}

}
