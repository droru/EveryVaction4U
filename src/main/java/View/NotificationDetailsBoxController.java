package View;

import com.sun.javafx.scene.control.behavior.PaginationBehavior;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import Model.Notification;
import javafx.fxml.FXML;
import sample.Aview;

import java.io.IOException;
import java.sql.SQLException;

public class NotificationDetailsBoxController extends Aview {
    Notification notification;
    Pane pane;

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

    public void setData(Notification notification, Pane pane){
        this.notification = notification;
        this.pane = pane;
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

    public void accept() throws SQLException {
        notification.setIsResponsed(true);
        notification.setIsAccept(true);
        //update in db
        getController().update(notification);
       //delete not
        //getController().delete(this.notification);
        pane.getChildren().remove(lbl_user.getParent());
    }

    public void deny() throws SQLException {
        notification.setIsResponsed(true);
        notification.setIsAccept(false);
        //update in db
        getController().update(notification);
        //delete not
        //getController().delete(this.notification);
        pane.getChildren().remove(lbl_user.getParent());
    }

    public void buy() throws IOException {
        //delete notification
       // getController().delete(this.notification);
        //show payment screen
        openPaymentChoose();
        Main.not=this.notification;
        //delete flight ->after payment
    }

    private void openPaymentChoose() throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/PaymentChoose.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Payment");
        Scene scene=new Scene(root,616 ,430);
        scene.getStylesheets().add(getClass().getResource("../View/Style.css").toExternalForm());
        stage.setScene(scene);
        //stage.setResizable(false);
        stage.initOwner(btn_buy.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        stage.setOnHiding(e->{
            boolean isPay = ((PaymentChooseController)fxmlLoader.getController()).isPay();
            if(isPay)
                pane.getChildren().remove(lbl_user.getParent());
        });
    }

    public void close() throws SQLException {
        //delete notification
        getController().update(this.notification);
        //getController().delete(this.notification);
        pane.getChildren().remove(lbl_user.getParent());
    }
}
