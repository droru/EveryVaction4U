package View;

import Model.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import sample.Main;

import java.io.IOException;

public class FlightRowBoxController {
    Flight flight;

    @FXML
    Label lbl_flight;

    public void setData(Flight flight){
        this.flight = flight;
        lbl_flight.setText("טיסה מס' " + flight.getFlightID() + " ל-" + flight.getDestinationCity() + ", " + flight.getDestinationCountry());
    }

    public void showFlightDetails(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/FlightDetailScreen.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        FlightDetailScreenController controller = fxmlLoader.getController();
        controller.setFlight(flight, false);
        Main.newStage(root, "Flight details", 700, 430, lbl_flight.getScene().getWindow());
    }
}
