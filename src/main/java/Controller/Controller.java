package Controller;

import Model.Flight;
import Model.Notification;
import Model.Query;
import Model.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class Controller {

    private  static Query query=new Query();

    //region User
    public User search_by_pass(String password){ return query.search_by_pass(password); }
    public  User search(String username) {return query.search(username); }
    public  int insert(User user) { return query.insert(user); }
    public  int delete(String user) { return query.delete(user); }
    public  int update(User user,String oldUser) throws SQLException { return query.update(user,oldUser); }
    //endregion

    //region Flights
    public ObservableList<Flight> getAllFlights(){
        return query.getAllFlights();
    }
    public int insert(Flight flight){ return query.insert(flight);}
    public ObservableList<String> getAllCompanies(){return query.getAllCompanies();}
    //endregion

    //region Notifications
    public List<Notification> getNotificationsByUser(String toUser){
        return query.getAllNotificationsByUser(toUser);
    }
    public int insert(Notification notification){
        return query.insert(notification);
    }
    //endregion
}
