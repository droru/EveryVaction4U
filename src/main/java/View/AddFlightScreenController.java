package View;

import Model.Flight;
import Model.Query;
import Model.Vecation;
import javafx.collections.ObservableList;
import javafx.scene.LightBase;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.Aview;

import java.lang.reflect.Array;
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
    boolean hotelInc;
    boolean hotelOk;

    private Flight flight;
    private String dest;
    private String city;
    private int price;
    private int conInclude;
    private int isSeparte;
    private String company;
    private Date fromDate;
    private Date to_date;
    private int bag;
    boolean saveFlight = true;


    String vecHotel;
    String vecKind;
    String vecRank;
    String isReturn;
    int numTicket;
    int vecInc;
    String card_type;
    String hotel_name;

    public ComboBox txt_isReturn;
    public CheckBox checkbox_hotelInc;
    public ComboBox combox_vecKind;
    public ComboBox combox_vecHotel;
    public TextField txt_vecRank;
    public CheckBox checkbox_priceInc;
    public TextField txt_numTicket;
    public ComboBox combox_cardType;
    public TextField txt_hotelName;

    public Label eror_numTicket;
    public Label eror_isReturn;
    public Label eror_vecRank;
    public Label eror_vecKind;
    public Label eror_vecHotel;
    public Label eror_cardType;
    public Label eror_hotelName;

    public Label lbl_detailvec;
    public Label lbl_typelbl;
    public Label lbl_kindlbl;
    public Label lbl_ranklbl;
    public Label lbl_priclbl;
    public Label lbl_hotelName;

    public Label erordestinatin;
    public Label erorfromDate;
    public Label erortoDate;
    public Label erorprice;
    public Label erorcompany;
    public Label erorisConnection;
    public Label erorisSeparate;
    public Label erorbaggage;
    public Label erorcity;


    public void initialize() {
        txt_seller.setText(Main.loggedUser.getFirstName() + " " + Main.loggedUser.getLastName());
        showHotelDitailes(false);


    }

    private void showHotelDitailes(boolean val) {
        combox_vecHotel.setVisible(val);
        combox_vecKind.setVisible(val);
        txt_vecRank.setVisible(val);
        checkbox_priceInc.setVisible(val);
        txt_hotelName.setVisible(val);
        lbl_hotelName.setVisible(val);
        lbl_detailvec.setVisible(val);
        lbl_typelbl.setVisible(val);
        lbl_kindlbl.setVisible(val);
        lbl_ranklbl.setVisible(val);
        lbl_priclbl.setVisible(val);
        eror_hotelName.setVisible(val);
        eror_vecHotel.setVisible(val);
        eror_vecRank.setVisible(val);
        eror_vecKind.setVisible(val);
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


        if (txt_fromDate.getEditor().getText().compareTo("") != 0) {
            if (!Validation.isValidDate(txt_fromDate.getValue())) {//txt_fromDate.getEditor().getText())) {
                saveFlight = false;
                erorfromDate.setVisible(true);
            } else {
                //fromDate = new Date(Integer.parseInt(txt_fromDate.getEditor().getText().split("/")[2]), Integer.parseInt(txt_fromDate.getEditor().getText().split("/")[1]), Integer.parseInt(txt_fromDate.getEditor().getText().split("/")[0]));
                Instant instant = Instant.from(txt_fromDate.getValue().atStartOfDay(ZoneId.systemDefault()));
                fromDate = Date.from(instant);
                erorfromDate.setVisible(false);
            }
        } else
            saveFlight = false;


        if (txt_toDate.getEditor().getText().compareTo("") != 0) {
            if (!Validation.isValidDate(txt_toDate.getValue())) {//txt_toDate.getEditor().getText())) {
                saveFlight = false;
                erortoDate.setVisible(true);
            } else {
                //to_date = new Date(Integer.parseInt(txt_toDate.getEditor().getText().split("/")[2]), Integer.parseInt(txt_toDate.getEditor().getText().split("/")[1]), Integer.parseInt(txt_toDate.getEditor().getText().split("/")[0]));
                Instant instant = Instant.from(txt_toDate.getValue().atStartOfDay(ZoneId.systemDefault()));
                to_date = Date.from(instant);
                erortoDate.setVisible(false);
            }
        } else
            saveFlight = false;

        if (txt_baggage.getValue() != null) {
            bag = Integer.parseInt(txt_baggage.getValue().toString());
            erorbaggage.setVisible(false);
        } else {
            erorbaggage.setVisible(true);
            saveFlight = false;
        }

        if (txt_isReturn.getValue() != null) {
            if (txt_isReturn.getValue().toString().compareTo("כן") == 0) {
                isReturn = "כן";
                eror_isReturn.setText("");
            } else if (txt_isReturn.getValue().toString().compareTo("לא") == 0) {
                isReturn = "לא";
                eror_isReturn.setText("");
            }
        } else {
            saveFlight = false;
            eror_isReturn.setText("עליך לבחור שדה זה");
        }


        if (txt_numTicket.getText().compareTo("") != 0) {
            if (Validation.isNumber(txt_numTicket.getText())) {
                numTicket = Integer.parseInt(txt_numTicket.getText());
                eror_numTicket.setText("");
            } else {
                saveFlight = false;
                eror_numTicket.setText("עליך להזין מספר חוקי");
            }
        } else {
            saveFlight = false;
            eror_numTicket.setText("עליך להזין שדה זה");

        }

        if (combox_cardType.getValue() != null) {
            if (combox_cardType.getValue().toString().compareTo("מבוגר") == 0) {
                card_type = "מבוגר";
                eror_cardType.setText("");
            } else if (combox_cardType.getValue().toString().compareTo("ילד") == 0) {
                card_type = "ילד";
                eror_cardType.setText("");
            }
        } else {
            saveFlight = false;
            eror_cardType.setText("עליך לבחור שדה זה");
        }


        if (checkbox_hotelInc.isSelected()) {
            if (checkhotelDetials()) {
                if ((saveFlight)) {
                    vecInc = 1;
                    pushFlight();
                    pushvecation();
                }
            }
        } else {
            if (saveFlight) {
                vecInc = 0;
                pushFlight();
            }
        }
    }

    private void pushvecation() {
        Vecation vec=new Vecation();
        ObservableList<Flight> flights=getController().getAllFlights();
        flight=flights.get(flights.size()-1);
        vec.setFlightID(flight.getFlightID());
        vec.setRate(vecRank);
        vec.setVec_Hotel(vecHotel);
        vec.setVec_Kind(vecKind);
        vec.setHotel_name(hotel_name);
        if(checkbox_priceInc.isSelected())
            vec.setPriceInc("כן");
        else
            vec.setPriceInc("לא");

        getController().insert(vec);

    }

    private void pushFlight() {
        erortoDate.setVisible(false);
        java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
        java.sql.Date sqltoDate = new java.sql.Date(to_date.getTime());
        flight = new Flight(0, dest, city, sqlFromDate, sqltoDate, Main.loggedUser.getFirstName() + " " + Main.loggedUser.getLastName(), price, conInclude, isSeparte, company, bag, Main.loggedUser.getUserName(),isReturn,numTicket,vecInc==1 ? "כן":"לא",card_type);
        System.out.println(flight.toString());
        getController().insert(flight);
        Main.switchScene("../View/MainScreen.fxml", Main.getStage(), Main.mainWidth, Main.mainHeight);
    }

    private boolean checkhotelDetials() {
       hotelOk=true;
        if (combox_vecHotel.getValue()!=null) {
            if ( combox_vecHotel.getValue().toString().compareTo("מלון") == 0)
                vecHotel = "מלון";
            else if (combox_vecHotel.getValue().toString().compareTo("צימר") == 0)
                vecHotel = "צימר";
            else if ( combox_vecHotel.getValue().toString().compareTo("חדר שכור") == 0)
                vecHotel = "חדר שכור";
            else if (combox_vecHotel.getValue().toString().compareTo("ביקתה") == 0)
                vecHotel = "ביקתה";
            eror_vecHotel.setText("");
        }
                 else {
                    hotelOk = false;
                    eror_vecHotel.setText("עליך לבחור שדה זה");
                }

        if (combox_vecKind.getValue()!=null) {
            if ( combox_vecKind.getValue().toString().compareTo("אורבנית") == 0)
                vecKind = "אורבנית";
            else if ( combox_vecKind.getValue().toString().compareTo("אקזוטית") == 0)
                vecKind = "אקזוטית";
            else if ( combox_vecKind.getValue().toString().compareTo("משפחתית") == 0)
                vecKind = "משפחתית";
            else if ( combox_vecKind.getValue().toString().compareTo("רומנטית") == 0)
                vecKind = "רומנטית";
            eror_vecKind.setText("");

        }
        else {
            hotelOk = false;
            eror_vecKind.setText("עליך לבחור שדה זה");
        }

        if(txt_vecRank.getText().compareTo("")!=0) {
            if (Validation.isDouble(txt_vecRank.getText())) {
                if (Double.parseDouble(txt_vecRank.getText())>5)
                    eror_vecRank.setText("דירוג לא יכול להיות מעל 5");
                else{
                    vecRank = (txt_vecRank.getText());
                    eror_vecRank.setText("");
                }
            }
            else {
                hotelOk = false;
                eror_vecRank.setText("שדה זה צריך להכיל רק מספרים באון הבא (x.xx)");
            }
        }
        else {
            hotelOk = false;
            eror_vecRank.setText("עליך למלא שדה זה");
        }

        if(txt_hotelName.getText().compareTo("")!=0)
        {
            if(Validation.isWord(txt_hotelName.getText()))
            {
                eror_hotelName.setText("");
                hotel_name=txt_hotelName.getText();
            }
            else {
                eror_hotelName.setText("נא להזין שם חוקי");
                hotelOk=false;
            }
        }
        else {
            eror_hotelName.setText("נא להזין שדה זה ");
            hotelOk=false;
        }

        return hotelOk;
    }

    public void cancelclicked(MouseEvent mouseEvent) {
        Main.switchScene("../View/MainScreen.fxml", Main.getStage(), Main.mainWidth, Main.mainHeight);
    }

    public void includeHotel(MouseEvent mouseEvent) {
        if(checkbox_hotelInc.isSelected()) {
            showHotelDitailes(true);
            emtypFiled();
            //((Stage)checkbox_hotelInc.getScene().getWindow()).setHeight(600);

        }
        else {
            showHotelDitailes(false);
            //((Stage)checkbox_hotelInc.getScene().getWindow()).setHeight(450);

        }
    }

    private void emtypFiled() {
        combox_vecHotel.getSelectionModel().select(null);
        combox_vecKind.getSelectionModel().select(null);
        txt_vecRank.setText("");
        checkbox_priceInc.setSelected(false);
    }
}