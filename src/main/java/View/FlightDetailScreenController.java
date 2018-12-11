package View;

import Model.Flight;
import Model.Notification;
import Model.Vecation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import sample.Aview;

public class FlightDetailScreenController extends Aview {
    public Flight flight;
    public Vecation vecation;

    @FXML
    public Label lbl_destinationCountry;
    public Label lbl_destinationCity;
    public Label lbl_fromDate;
    public Label lbl_toDate;
    public Label lbl_flightID;
    public Label lbl_company;
    public Label lbl_isConnection;
    public Label lbl_isSeparate;
    public Label lbl_price;
    public Label lbl_seller;
    public Button btn_buy;


    public Label lbl_hotelName;
    public Label lbl_hotelKind;
    public Label lbl_hotelprice;
    public Label lbl_hotelType;
    public Label lbl_hotelRank;

    public Label hotelname_lbl;
    public Label kind_lbl;
    public Label rank_lbl;
    public Label vecKind_lbl;

    @FXML
    public void initialize(){
        hotelname_lbl.setVisible(false);
        kind_lbl.setVisible(false);
        rank_lbl.setVisible(false);
        vecKind_lbl.setVisible(false);
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
        lbl_destinationCountry.setText(flight.getDestinationCountry());
        lbl_destinationCity.setText(flight.getDestinationCity());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        lbl_fromDate.setText(df.format(flight.getFromDate()));
        lbl_toDate.setText(df.format(flight.getToDate()));
        lbl_flightID.setText(String.valueOf(flight.getFlightID()));
        lbl_company.setText(flight.getCompany());
        lbl_isConnection.setText(flight.isConnection() ? "כולל קונקשיין" : "ללא קונקשיין");
        lbl_isSeparate.setText(flight.isSeparate() ? "ניתן לרכוש כרטיסים בנפרד" : "אין אפשרות לרכוש כרטיסים בנפרד");
        lbl_price.setText(flight.getPrice() + "ש\"ח");
        lbl_seller.setText(flight.getSeller());

        vecation=getController().search(flight.getFlightID());
        if(vecation!=null) {
            hotelname_lbl.setVisible(true);
            kind_lbl.setVisible(true);
            rank_lbl.setVisible(true);
            vecKind_lbl.setVisible(true);
            lbl_hotelName.setText(vecation.getHotel_name());
            lbl_hotelKind.setText(vecation.getVec_Kind());
            lbl_hotelType.setText(vecation.getVec_Hotel());
            lbl_hotelRank.setText(vecation.getRate());
        }
    }

    public void buyFlight(ActionEvent actionEvent) {
        if (Main.loggedUser != null) {
            if(Main.loggedUser.getUserName().equals(flight.getUserNameSeller())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("User Error");
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setHeaderText("רכישת טיסה");
                alert.setContentText("אינך יכול לרכוש טיסה שהינך מוכר אותה");
                alert.showAndWait();
            }
            else {
                Notification notification = new Notification(Main.loggedUser.getUserName(), flight.getUserNameSeller(), flight.getFlightID(), false, false);
                getController().insert(notification);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Buy Flight");
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setHeaderText("בקשת רכישה");
                alert.setContentText("בקשת הרכישה הועברה למוכר הטיסה\nתתיקבל התראה כאשר המוכר יאשר את הרכישה");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                    ((Stage) lbl_seller.getScene().getWindow()).close();
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User Error");
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setHeaderText("המשתמש אינו מחובר למערכת");
            alert.setContentText("רק למשתמשים מחוברים יש הרשאות לבצע רכישה של חופשות\nאנא בצע התחברות");
            ButtonType buttonTypeLogIn = new ButtonType("התחבר");
            ButtonType buttonTypeCancel = new ButtonType("ביטול");
            alert.getButtonTypes().setAll(buttonTypeLogIn, buttonTypeCancel);
            alert.showAndWait();
            if(alert.getResult() == buttonTypeLogIn){
                Main.switchScene("../View/LoginForm.fxml", (Stage) lbl_seller.getScene().getWindow(), Main.loginWidth, Main.loginHeight);
            }
        }
    }
}
