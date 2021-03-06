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
        String sql = "INSERT INTO Flights(destinationCountry, fromDate, toDate, seller, price, isConnection, isSeparate, company, baggage, destinationCity,sellerUserName, isActive,isReturn,numTicket,vecInc,cardType) VALUES(?,?,?,?,?,?,?,?,?,?,?,1,?,?,?,?)";
        try (
                Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setString(1, flight.getDestinationCountry());
            pstmt.setDate(2, flight.getFromDate());
            pstmt.setDate(3,flight.getToDate());
            pstmt.setString(4,flight.getSeller());
            pstmt.setInt(5, flight.getPrice());
            pstmt.setInt(6, (flight.isConnection() ? 1 : 0));
            pstmt.setInt(7, (flight.isSeparate() ? 1 : 0));
            pstmt.setString(8, flight.getCompany());
            pstmt.setInt(9, flight.getBaggage());
            pstmt.setString(10, flight.getDestinationCity());
            pstmt.setString(11,flight.getUserNameSeller());
            pstmt.setString(12,flight.getIsReturn());
            pstmt.setInt(13,flight.getNumTicket());
            pstmt.setString(14,flight.getVecInc());
            pstmt.setString(15,flight.getCardType());

            pstmt.executeUpdate();
            conn.close();
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
                return new Flight(rs.getInt("flightID"), rs.getString("destinationCountry"), rs.getString("destinationCity"), rs.getDate("fromDate"), rs.getDate("toDate"), rs.getString("seller"), rs.getInt("price"), rs.getInt("isConnection"), rs.getInt("isSeparate"), rs.getString("company"), rs.getInt("baggage"), rs.getString("sellerUserName"),rs.getString("isReturn"),rs.getInt("numTicket"),rs.getString("vecInc"),rs.getString("cardType"));
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
    public int updateActiveField(int flightID){
        String sql = "UPDATE Flights set isActive = 0 where flightID = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, flightID);
            pstmt.executeUpdate();
            return 1;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public ObservableList<Flight> getAllFlights(){
        String sql = "SELECT * FROM Flights where isActive = 1";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            return flightResultSetToObservable(rs);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public ObservableList<String> getAllCompanies(){
        String sql = "SELECT company FROM Flights";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            List<String> companies = new ArrayList<>();
            while (rs.next()) {
                if(!companies.contains(rs.getString("company")))
                    companies.add(rs.getString("company"));
            }
            ObservableList<String> observablStrings = FXCollections.observableArrayList(companies);
            return observablStrings;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public ObservableList<Flight> getFlightsByUserName(String userName){
        String sql = "SELECT * FROM Flights where sellerUserName like ? and isActive = 1";
        try(Connection conn = connect();
            PreparedStatement psmt = conn.prepareStatement(sql);){

            psmt.setString(1, userName);
            ResultSet rs = psmt.executeQuery();

            return flightResultSetToObservable(rs);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ObservableList<Flight> flightResultSetToObservable(ResultSet rs) throws SQLException {
        List<Flight> flights = new ArrayList<>();
        while (rs.next()) {
            Flight f=new  Flight(rs.getInt("flightID"), rs.getString("destinationCountry"), rs.getString("destinationCity"), rs.getDate("fromDate"), rs.getDate("toDate"), rs.getString("seller"), rs.getInt("price"), rs.getInt("isConnection"), rs.getInt("isSeparate"), rs.getString("company"), rs.getInt("baggage"), rs.getString("sellerUserName"),rs.getString("isReturn"),rs.getInt("numTicket"),rs.getString("vecInc"),rs.getString("cardType"));
            flights.add(f);
        }
        if(flights != null) {
            ObservableList<Flight> observableFlights = FXCollections.observableArrayList(flights);
            return observableFlights;
        }
        return null;
    }
    //endregion

    //region Notification
    // return: 0 if the insert succeed else 1
    public int insert(Notification notification)
    {
        String sql = "INSERT INTO Notification(fromUser, toUser, flightID, isResponsed, isAccept, isPayProcess) VALUES(?,?,?,?,?,?)";
        try (
                Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, notification.getFromUser());
            pstmt.setString(2, notification.getToUser());
            pstmt.setInt(3,notification.getFlightID());
            pstmt.setInt(4,notification.getIsResponsed() ? 1 : 0);
            pstmt.setInt(5, notification.getIsAccept() ? 1 : 0);
            pstmt.setInt(6, notification.isPayProcess() ? 1 : 0);

            return pstmt.executeUpdate();
            //return 0 ;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1 ;
        }
    }
    public ObservableList<Notification> getAllNotificationsByUser(String userName){
        String sql = "SELECT * FROM Notification where (isResponsed = 0 and toUser = ?) or (isResponsed =1 and fromUser = ?) or (isPayProcess = 1 and toUser = ?)";
        String sql2 = "select * from SwitchNotification";
        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql);
             Statement stmt  = conn.createStatement() ){

            pstmt.setString(1, userName);
            pstmt.setString(2, userName);
            pstmt.setString(3, userName);
            ResultSet rs    = pstmt.executeQuery();

            List<Notification> notifications = new ArrayList<>();
            while (rs.next()) {
                Notification n;
                ResultSet rs2 = stmt.executeQuery(sql2);
                ResultSet r = contains(rs2, rs.getInt("notificationID"));
                if(r != null)
                    n = new SwitchNotification(rs.getString("fromUser"), rs.getString("toUser"), rs.getInt("flightID"), rs.getInt("isResponsed")==1 ? true : false, rs.getInt("isAccept") == 1 ? true : false, rs.getInt("isPayProcess") == 1 ? true : false, r.getInt("SecondFlightID"), rs.getInt("notificationID"));
                else
                    n = new Notification(rs.getInt("notificationID"), rs.getString("fromUser"), rs.getString("toUser"), rs.getInt("flightID"), rs.getInt("isResponsed")==1 ? true : false, rs.getInt("isAccept") == 1 ? true : false, rs.getInt("isPayProcess") == 1 ? true : false);
                notifications.add(n);
            }
            ObservableList<Notification> observableNotifications = FXCollections.observableArrayList(notifications);
            return observableNotifications;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    private ResultSet contains(ResultSet rs, int id){
        try {
            while (rs.next()) {
                if(rs.getInt("NotificationID") == id)
                    return rs;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public  int update(Notification notification) throws SQLException {
        String sql = "update Notification set isResponsed = ?, isAccept = ?, isPayProcess = ? where notificationID = ?";
        try(Connection conn = connect();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1, notification.getIsResponsed() ? 1 : 0);
            pstm.setInt(2, notification.getIsAccept() ? 1 : 0);
            pstm.setInt(3, notification.isPayProcess() ? 1 : 0);
            pstm.setInt(4, notification.getNotificationID());
            return pstm.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            return 1;
        }
        /*{
            if(searchNot(notification)!= null )
            {
                delete(notification);
                insert(notification);
                return 0;
            }
            else
                return 1;
        }*/
    }
    public Notification searchNot(Notification notification) {
        String sql = "SELECT *"
                + "FROM Notification WHERE fromUser = ? and flightID = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1 , notification.getFromUser());
            pstmt.setInt(2,notification.getFlightID());
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next())
                return new Notification(rs.getInt("notificationID"), rs.getString("fromUser"),rs.getString("toUser"),rs.getInt("flightID"),rs.getInt("isResponsed")==1 ?true:false,rs.getInt("isAccept")==1 ?true :false, rs.getInt("isPayProcess") == 1 ? true : false);
            else
                return null;
            // loop through the result set

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public int delete(Notification notification) {
        String sql = "DELETE FROM Notification WHERE fromUser = ? and flightID = ?";
        Notification f = searchNot(notification);
        if(f == null)
            return 1 ;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, notification.getFromUser());
            pstmt.setInt(2, notification.getFlightID());

            pstmt.executeUpdate();
            return 0;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return 1;
        }
    }
    //endregion

    //region Vecation
    public int insert(Vecation vecation)
    {
        String sql = "INSERT INTO Vecation(flightID, Kind, Type , Rate, priceInc,hotelName) VALUES(?,?,?,?,?,?)";
        try (
                Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, vecation.getFlightID());
            pstmt.setString(2, vecation.getVec_Kind());
            pstmt.setString(3,vecation.getVec_Hotel());
            pstmt.setString(4,vecation.getRate() );
            pstmt.setString(5, vecation.getPriceInc());
            pstmt.setString(6,vecation.getHotel_name());

            return pstmt.executeUpdate();
            //return 0 ;
        } catch (SQLException e) {
            return 1 ;
        }
    }

    public Vecation searchVec(int flightID) {
        String sql = "SELECT *"
                + "FROM Vecation WHERE flightID = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setInt(1 , flightID);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                return new Vecation(rs.getString("priceInc"), rs.getString("Kind"), rs.getString("Type"),rs.getString("Rate"),rs.getInt("flightID"), rs.getString("hotelName"));

            }  else {
                return null;
            }
            // loop through the result set

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public int delete(Vecation vecation) {
        String sql = "DELETE FROM Vecation WHERE flightID = ?";
        Vecation f = searchVec(vecation.getFlightID());
        if(f == null)
            return 1 ;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vecation.getFlightID());
            pstmt.executeUpdate();
            return 0;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return 1;
        }
    }
    //endregion

    //region SwitchNotification
    public int insert(SwitchNotification switchNotification){
        String sql = "Insert into SwitchNotification(NotificationID, SecondFlightID) values (?,?)";
        try(Connection conn = connect();
            PreparedStatement psmt = conn.prepareStatement(sql)){
            insert((Notification) switchNotification);
            Notification notification = searchNot(switchNotification);
            switchNotification.setNotificationID(notification.getNotificationID());

            psmt.setInt(1, switchNotification.getNotificationID());
            psmt.setInt(2, switchNotification.getSecondFlightID());
            return psmt.executeUpdate();
        }
        catch (SQLException e){
            return 1;
        }
    }
    public int delete(SwitchNotification switchNotification){
        String sql = "delete from SwitchNotification where NotificationID = ? and SecondFlightID = ?";
        try (Connection conn = connect();
            PreparedStatement pamt = conn.prepareStatement(sql)){

            delete((Notification) switchNotification);
            pamt.setInt(1, switchNotification.getNotificationID());
            pamt.setInt(2, switchNotification.getSecondFlightID());
            return pamt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            return 1;
        }
    }
    public int update(SwitchNotification switchNotification){
        try {
            return update((Notification) switchNotification);
        }
        catch (SQLException e){
            e.printStackTrace();
            return 1;
        }
    }
    //endregion
}
