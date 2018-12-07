package View;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.Main;
import Model.Notification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class NotificationDetailsBoxController {
    @FXML
    public Button btn_accept;
    public Button btn_deny;
    public Button btn_buy;
    public Button btn_close;
    public Label lbl_msg;

    @FXML
    public void initialize(){
        btn_accept.managedProperty().bind(btn_accept.visibleProperty());
        btn_close.managedProperty().bind(btn_close.visibleProperty());
        btn_buy.managedProperty().bind(btn_buy.visibleProperty());
        btn_deny.managedProperty().bind(btn_deny.visibleProperty());

    }

    public void setData(Notification notification){
        if(notification.getIsResponsed()==false && notification.getToUser().equals(Main.loggedUser.getUserName())) {
            lbl_msg.setText(notification.getFromUser() + " ביקש לרכוש את טיסה מס' " + notification.getFlightID());
            btn_accept.setVisible(true);
            btn_deny.setVisible(true);
        }
        else if(notification.getIsResponsed()==true && notification.getFromUser().equals(Main.loggedUser.getUserName())) {
            String str = "רכישת הטיסה " + notification.getFlightID() + " ";
            if (notification.getIsAccept()) {
                lbl_msg.setText(str + "אושרה ע\"י המוכר");
                btn_buy.setVisible(true);
                btn_close.setVisible(true);
            } else {
                lbl_msg.setText(str + "לא אושרה ע\"י המוכר");
                btn_close.setVisible(true);
                btn_close.setText("קראתי");
            }
        }
    }

    public void accept() {
    }

    public void deny() {
    }

    public void buy() {
    }

    public void close() {
    }
}
