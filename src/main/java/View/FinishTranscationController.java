package View;

import Model.Flight;
import Model.Vecation;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Main;


import java.time.LocalDate;
import java.time.ZoneId;
import sample.Aview;

public class FinishTranscationController extends Aview {


    public Label PaymentNumber;
    public Label lbl_date;
    public Label lbl_resFlightid;
    public Label lbl_resDest;
    public Label lbl_resCom;
    public Label lbl_resDate;
    public Label lbl_price;
    public Label lbl_seller;
    public Label lbl_ticketType;
    public Hyperlink link_close;

    Flight flight;

    Vecation vecation;
    public GridPane pane_hotel;
    public Label lbl_hotelName;
    public Label lbl_hotelRank;
    public Label lbl_hotelType;
    public Label lbl_hotelKind;


    public void initialize(){
        flight=getController().serach(Main.not.getFlightID());
        ZoneId zonedId = ZoneId.of( "America/Montreal" );
        LocalDate today = LocalDate.now( zonedId );
        PaymentNumber.setText(String.valueOf(Main.not.hashCode()));
        lbl_date.setText(today.toString());
        lbl_resCom.setText(flight.getCompany());
        lbl_resDate.setText(flight.getFromDate().toString()+" -- "+ flight.getToDate().toString());
        lbl_resFlightid.setText(String.valueOf(flight.getFlightID()));
        lbl_resDest.setText(flight.getDestinationCountry()+","+flight.getDestinationCity());
        lbl_seller.setText(flight.getSeller());
        lbl_price.setText(String.valueOf(flight.getPrice())+" "+ "שקלים");
        lbl_ticketType.setText(flight.getCardType());
        vecation=getController().search(flight.getFlightID());
        if(vecation!=null)
            initVec();
        else
            pane_hotel.setVisible(false);
    }

    private void initVec() {
        lbl_hotelName.setText(vecation.getHotel_name());
         lbl_hotelRank.setText(vecation.getRate());
        lbl_hotelType.setText(vecation.getVec_Hotel());
        lbl_hotelKind.setText(vecation.getVec_Kind());
    }

    public void close(MouseEvent mouseEvent) {
        ((Stage)this.link_close.getScene().getWindow()).close();
        //getController().delete(flight.getFlightID());
    }
}
