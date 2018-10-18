package Model;

import java.sql.*;


public class Query
{

    private static Connection connect()
    {
        // SQLite connection string
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
        String UserName=user.getUserName();
        String FirstName=user.getFirstName();
        String LastName=user.getLastName();
        String Password=user.getPassword();
        String BirthDate=user.getBirthDate();
        String City=user.getCity();
        String Email=user.getEmail();

        String sql = "INSERT INTO Users(UserName,FirstName,LastName,Password,BirthDate,City,Email) VALUES(?,?,?,?,?,?,?)";
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

            pstmt.executeUpdate();
            return 0 ;
        } catch (SQLException e) {
            return 1 ;
        }
    }


    public static  User search(String username)
    {
        String sql = "SELECT UserName ,FirstName,LastName,Password,BirthDate,City,Email "
                + "FROM Users WHERE UserName = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1 , username);
            //
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("UserName"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Password"), rs.getString("BirthDate"), rs.getString("City"),rs.getString("Email"));
            }
            else {
                return null;
            }
            // loop through the result set

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // return: 0 if the delete succeed else 1 (The username doesn't exist in the table or the connection to db  doesn't succeed )

    public static int delete(String user) {
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
            return 0;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return 1;
        }
    }


    // return: 0 if the update succeed else 1 (One or more fields are not valid or the connection to db  doesn't succeed )

public static int update(String userName_old , String userName_new ,String firstName,String lastName,String password,String birthDate,String city,String email) {
    {
        User user=new User(userName_new,firstName,lastName,password,birthDate,city,email);
        if(search(userName_old)!= null && search(userName_new)== null )
        {
            delete(userName_old);
            insert(user);
            return 0;
        }
        else
        return 1;
    }
}

}
