package View;

import Model.Flight;
import Model.Query;
import javafx.scene.LightBase;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.Aview;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class AddFlightScreenController extends Aview {

    public TextField txt_destinationCountry;
    public TextField txt_destinationCity;
    public TextField txt_price;
    public TextField txt_company;
    public TextField txt_seller;
    public DatePicker txt_fromDate;
    public DatePicker txt_toDate;
    public ComboBox txt_isConnection;
    public ComboBox txt_isSeparate;
    public ComboBox txt_baggage;

    private Flight flight;
    private String dest;
    private String city;
    private int price;
    private int conInclude;
    private int isSeparte;
    private String company;
    private Date fromDate;
    private Date to_date;
    boolean saveFlight = true;
    private int bag;

    public Label erordestinatin;
    public Label erorfromDate;
    public Label erortoDate;
    public Label erorprice;
    public Label erorseller;
    public Label erorcompany;
    public Label erorisConnection;
    public Label erorisSeparate;
    public Label erorbaggage;
    public Label erorcity;

    public void initialize() {
        txt_seller.setText(Main.loggedUser.getFirstName() + " " + Main.loggedUser.getLastName());
    }

    public void saveclicked(MouseEvent mouseEvent) {
        saveFlight = true;
        if (txt_destinationCountry.getText() != "" && txt_destinationCountry.getLength() != 0 && Validation.isWord(txt_destinationCountry.toString())) {
            dest = txt_destinationCountry.getText();
            erordestinatin.setVisible(false);
        } else {
            saveFlight = false;
            erordestinatin.setVisible(true);
        }

        if (txt_destinationCity.getText() != "" && txt_destinationCity.getLength() != 0 && Validation.isWord(txt_destinationCity.getText())) {
            city = txt_destinationCity.getText();
            erorcity.setVisible(false);
        } else {
            saveFlight = false;
            erorcity.setVisible(true);
        }

        if (txt_price.getText() != "" && txt_price.getLength() != 0 && Validation.isNumber(txt_price.getText())) {
            price = Integer.parseInt(txt_price.getText());
            erorprice.setVisible(false);
        } else {
            saveFlight = false;
            erorprice.setVisible(true);
        }


        if (txt_company.getText() != "" && txt_company.getLength() != 0 && Validation.isWord(txt_company.getText())) {
            company = (txt_company.getText());
            erorcompany.setVisible(false);
        } else {
            saveFlight = false;
            erorcompany.setVisible(true);
        }


        if (txt_isConnection.getValue() != null)
            if (txt_isConnection.getValue().toString().compareTo("לא") == 0) {
                conInclude = 0;
                erorisConnection.setVisible(false);
            } else if (txt_isConnection.getValue().toString().compareTo("כן") == 0) {
                conInclude = 1;
                erorisConnection.setVisible(false);
            } else {
                saveFlight = false;
                erorisConnection.setVisible(true);
            }


        if (txt_isSeparate.getValue() != null)
            if (txt_isSeparate.getValue() != null && txt_isSeparate.getValue().toString().compareTo("לא") == 0) {

                isSeparte = 0;
                erorisSeparate.setVisible(false);
            } else if (txt_isSeparate.getValue() != null && txt_isSeparate.getValue().toString().compareTo("כן") == 0) {
                isSeparte = 1;
                erorisSeparate.setVisible(false);
            } else {
                saveFlight = false;
                erorisSeparate.setVisible(true);
            }


        if (txt_fromDate.getEditor().getText().compareTo("")!=0) {
            if (!Validation.isValidDate(txt_fromDate.getValue())) {//txt_fromDate.getEditor().getText())) {
                saveFlight = false;
                erorfromDate.setVisible(true);
            } else {
                //fromDate = new Date(Integer.parseInt(txt_fromDate.getEditor().getText().split("/")[2]), Integer.parseInt(txt_fromDate.getEditor().getText().split("/")[1]), Integer.parseInt(txt_fromDate.getEditor().getText().split("/")[0]));
                Instant instant = Instant.from(txt_fromDate.getValue().atStartOfDay(ZoneId.systemDefault()));
                fromDate = Date.from(instant);
                erorfromDate.setVisible(false);
            }
        }
        else
            saveFlight=false;


        if (txt_toDate.getEditor().getText().compareTo("")!=0) {
            if (!Validation.isValidDate(txt_toDate.getValue())) {//txt_toDate.getEditor().getText())) {
                saveFlight = false;
                erortoDate.setVisible(true);
            } else {
                //to_date = new Date(Integer.parseInt(txt_toDate.getEditor().getText().split("/")[2]), Integer.parseInt(txt_toDate.getEditor().getText().split("/")[1]), Integer.parseInt(txt_toDate.getEditor().getText().split("/")[0]));
                Instant instant = Instant.from(txt_toDate.getValue().atStartOfDay(ZoneId.systemDefault()));
                to_date = Date.from(instant);
                erortoDate.setVisible(false);
            }
        }
        else
            saveFlight=false;

        if (txt_baggage.getValue() != null) {
            bag = Integer.parseInt(txt_baggage.getValue().toString());
            erorbaggage.setVisible(false);
        } else {
            erorbaggage.setVisible(true);
            saveFlight = false;
        }


        if (saveFlight) {
            erortoDate.setVisible(false);
            java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
            java.sql.Date sqltoDate = new java.sql.Date(to_date.getTime());
            flight = new Flight(0, dest, city, sqlFromDate, sqltoDate, Main.loggedUser.getFirstName() + " " + Main.loggedUser.getLastName(), price, conInclude, isSeparte, company, 10, Main.loggedUser.getUserName());
            System.out.println(flight.toString());
            getController().insert(flight);
            Main.switchScene("../View/MainScreen.fxml", Main.getStage(), Main.mainWidth, Main.mainHeight);

        }
        System.out.println("here");

    }

    public void cancelclicked(MouseEvent mouseEvent) {
        Main.switchScene("../View/MainScreen.fxml", Main.getStage(), Main.mainWidth, Main.mainHeight);
    }
}