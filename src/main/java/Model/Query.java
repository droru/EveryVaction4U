package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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

    private static Connection connect() {
        // SQLite connection string
      //  DriverManager.getConnection("jdbc:sqlite:D:\\db\\my-db.sqlite");
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:sqlite:src/main/java/DataBase/Vacation4You-DB";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    //region User
    // return: 0 if the insert succeed else 1
    public int insert(User user)
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
    public   User search(String username) {
       // DriverManager.getConnection("jdbc:sqlite:D:\\db\\my-db.sqlite");

        String sql = "SELECT UserName,FirstName,LastName,Password,BirthDate,City,Email,Picture "
                + "FROM Users WHERE UserName = ?";

        return SearcByValue(username, sql);
    }
    private  User SearcByValue(String username, String sql) {
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
    public  User search_by_pass(String password)
    {
        // DriverManager.getConnection("jdbc:sqlite:D:\\db\\my-db.sqlite");

        String sql = "SELECT UserName,FirstName,LastName,Password,BirthDate,City,Email,Picture "
                + "FROM Users WHERE Password = ?";

        return SearcByValue(password, sql);
    }
    // return: 0 if the delete succeed else 1 (The username doesn't exist in the table or the connection to db  doesn't succeed )
    public  int delete(String user) {
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
    public  int update(User user,String oldUser) throws SQLException {
    {
        InitUser(user);
        if(search(oldUser)!= null )
        {
            delete(oldUser);
            insert(user);
            return 0;
        }
        else
        return 1;
    }
}
    //endregion

    //region Flight
    // return: 0 if the insert succeed else 1
    public int insert(Flight flight)
    {
        String sql = "INSERT INTO Flights(destinationCountry, fromDate, toDate, seller, price, isConnection, isSeparate, company, baggage, destinationCity) VALUES(?,?,?,?,?,?,?,?)";
        try (
                Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, flight.getDestinationCountry());
            pstmt.setDate(2, flight.getFromDate());
            pstmt.setDate(3,flight.getToDate());
            pstmt.setString(4,flight.getSeller().getUserName());
            pstmt.setInt(5, flight.getPrice());
            pstmt.setInt(6, (flight.isConnection() ? 1 : 0));
            pstmt.setInt(7, (flight.isSeparate() ? 1 : 0));
            pstmt.setString(8, flight.getCompany());
            pstmt.setInt(9, flight.getBaggage());
            pstmt.setString(10, flight.getDestinationCity());

            pstmt.executeUpdate();
            return 0 ;
        } catch (SQLException e) {
            return 1 ;
        }
    }
    public Flight search(int flightID) {
        String sql = "SELECT *"
                + "FROM Flights WHERE flightID = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setInt(1 , flightID);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                User user = search(rs.getString("seller"));
                return new Flight(rs.getInt("flightID"), rs.getString("destinationCountry"), rs.getString("destinatinCity"), rs.getDate("fromDate"), rs.getDate("toDate"), user, rs.getInt("price"), rs.getInt("isConnection"), rs.getInt("isSeparate"), rs.getString("company"), rs.getInt("baggage"));
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
    public int delete(int flightID) {
        String sql = "DELETE FROM Flights WHERE flightID = ?";

        Flight f = search(flightID);
        if(f == null)
            return 1 ;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, flightID);
            pstmt.executeUpdate();
            return 0;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return 1;
        }
    }
    public ObservableList<Flight> getAllFlights(){
        String sql = "SELECT * FROM Flights";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            List<Flight> flights = new ArrayList<>();
            while (rs.next()) {
                User user = search(rs.getString("seller"));
                Flight f = new Flight(rs.getInt("flightID"), rs.getString("destinationCountry"), rs.getString("destinationCity"), rs.getDate("fromDate"), rs.getDate("toDate"), user, rs.getInt("price"), rs.getInt("isConnection"), rs.getInt("isSeparate"), rs.getString("company"), rs.getInt("baggage"));
                flights.add(f);
            }
            ObservableList<Flight> observableFlights = FXCollections.observableArrayList(flights);
            return observableFlights;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    //endregion


}
