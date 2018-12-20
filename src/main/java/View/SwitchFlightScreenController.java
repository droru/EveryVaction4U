package View;

import Model.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.Main;
import java.io.IOException;
import java.sql.Date;

public class SwitchFlightScreenController {
    ObservableList<Flight> flights;

    @FXML
    //ComboBox flightsList;
    ListView flightsListView;

    public void initialize(){
        //ObservableList<HBox> hBoxes = FXCollections.observableArrayList();


        /*Flight fli = new Flight(500, "AAA", "BBB", new Date(1, 1, 2001), new Date(2, 2, 2002), "me", 500, 0, 0, "biga", 5, "uuu", "כן", 3, "לא", "מבוגר");
        HBox hBox = new HBox();
        hBox.getChildren().add(new Label("hu"));
        Hyperlink hyperlink = new Hyperlink("לצפייה");
        hBox.getChildren().add(hyperlink);
        hyperlink.setAlignment(Pos.CENTER_LEFT);
        hBoxes.add(hBox);*/

    }

    public void setData(ObservableList<Flight> flights){
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
            //scene.getStylesheets().add(getClass().getResource("../View/Style.css").toExternalForm());
            parents.add(scene.getRoot());
        }
        flightsListView.setItems(parents);

    }

    public void addNewFlight(ActionEvent actionEvent) {
        //show add new flight
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/AddFlightScreen.fxml"));
            Stage stage = Main.newStage(root, "Add Flight", Main.mainWidth, Main.mainHeight, flightsListView.getScene().getWindow());
            stage.setOnHidden(e->{stage.close();});
        } catch (IOException e) {
            e.printStackTrace();
        }
        //after close add the flight to the list

    }

    public void sendSwitchRequest(ActionEvent actionEvent) {
        //add notification
        //ask for accept
        //after accept send approvale to the buyer
    }
}
