package View;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.Main;
import Model.Notification;
import javafx.fxml.FXML;

public class NotificationDetailsBoxController {
    Notification notification;
    @FXML
    public Button btn_accept;
    public Button btn_deny;
    public Button btn_buy;
    public Button btn_close;
    public Label lbl_msg;
    public Label lbl_user;

    @FXML
    public void initialize(){
        btn_accept.managedProperty().bind(btn_accept.visibleProperty());
        btn_close.managedProperty().bind(btn_close.visibleProperty());
        btn_buy.managedProperty().bind(btn_buy.visibleProperty());
        btn_deny.managedProperty().bind(btn_deny.visibleProperty());
    }

    public void setData(Notification notification){
        this.notification = notification;
        if(notification.getIsResponsed()==false && notification.getToUser().equals(Main.loggedUser.getUserName())) {
            lbl_user.setText(notification.getFromUser());
            lbl_msg.setText(" ביקש לרכוש את טיסה מס' " + notification.getFlightID());
            btn_accept.setVisible(true);
            btn_deny.setVisible(true);
        }
        else if(notification.getIsResponsed()==true && notification.getFromUser().equals(Main.loggedUser.getUserName())) {
            String str = "רכישת הטיסה " + notification.getFlightID();
            if (notification.getIsAccept()) {
                lbl_user.setText(notification.getToUser());
                lbl_msg.setText("אישר " + str);
                btn_buy.setVisible(true);
                btn_close.setVisible(true);
            } else {
                lbl_msg.setText("לא אישר " + str);
                btn_close.setVisible(true);
                btn_close.setText("קראתי");
            }
        }
    }

    public void accept() {
        notification.setIsResponsed(true);
        notification.setIsAccept(true);
        //update in db
    }

    public void deny() {
        notification.setIsResponsed(true);
        notification.setIsAccept(false);
        //update in db
    }

    public void buy() {
        //delete notification
        //show payment screen
        //delete flight
    }

    public void close() {
        //delete notification
    }
}
