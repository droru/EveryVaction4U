package Controller;

import Model.Flight;
import Model.Query;
import Model.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;

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
    //endregion
}
