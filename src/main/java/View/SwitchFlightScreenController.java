package View;

import Model.Flight;
import Model.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sample.Aview;
import sample.Main;
import java.io.IOException;

public class SwitchFlightScreenController extends Aview {
    ObservableList<Flight> flights;
    Flight firstFlight;

    @FXML
    ListView flightsListView;

    public void initialize(){
        ObservableList<Flight> flights = getController().getFlightsByUserName(Main.loggedUser.getUserName());
        //setData(flights);
        this.flights = flights;
        ObservableList<Parent> parents = FXCollections.observableArrayList();
        for (Flight flight: flights) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/FlightRowBox.fxml"));
            Parent root = null;
            try {
                root = (Parent) fxmlLoader.load();
                FlightRowBoxController controller = fxmlLoader.getController();
                controller.setData(flight);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            parents.add(scene.getRoot());
        }
        flightsListView.setItems(parents);

    }

    public void setData(Flight firstFlight){
        this.firstFlight = firstFlight;
    }

    public void addNewFlight(ActionEvent actionEvent) {
        Main.switchScene("../View/AddFlightScreen.fxml", (Stage) flightsListView.getScene().getWindow(), Main.registerWidth, Main.registerHeight);
    }

    public void sendSwitchRequest(ActionEvent actionEvent) {
        if(flightsListView.getSelectionModel().getSelectedItem() != null) {
        /*Notification notification = new Notification(Main.loggedUser.getUserName(), flight.getUserNameSeller(), flight.getFlightID(), false, false, false);
        getController().insert(notification);
*/
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Switch Flight");
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setHeaderText("בקשת החלפה");
            alert.setContentText("בקשת ההחלפה הועברה למוכר הטיסה\nתתיקבל התראה כאשר המוכר יאשר את ההחלפה");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                alert.close();
                ((Stage)((Stage)flightsListView.getScene().getWindow()).getOwner()).close();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Switch Flight");
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setHeaderText("בקשת החלפה");
            alert.setContentText("לא בוצעה בחירת טיסה להחלפה");
            alert.showAndWait();
        }
    }
}
