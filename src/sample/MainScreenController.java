package sample;

import Model.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLException;
import java.util.Optional;

import static Model.Query.search;


public class MainScreenController {
    @FXML
    public TextField txt_searchUser;
    public TextField txt_searchDestination;
    public HBox advancedSearchBox;
    public CheckBox advanceSearchCheckbox;
    public Hyperlink LoginRegister;
    public VBox loggedUserBox;
    public TextArea textArea_firstFlight;
    public TextArea textArea_secondFlight;
    public TextArea textArea_thirdFlight;
    public TextArea textArea_fourthFlight;
    public TextArea textArea_fifthFlight;
    public Label lbl_welcome;

    public  void initialize(){
        if(Main.loggedUser == null) {
            lbl_welcome.setText("שלום אורח");
            LoginRegister.setVisible(true);
            LoginRegister.managedProperty().bind(LoginRegister.visibleProperty());
            loggedUserBox.setVisible(false);
        }
        else {
            lbl_welcome.setText("שלום " + Main.loggedUser.getUserName());
            LoginRegister.setVisible(false);
            LoginRegister.managedProperty().bind(LoginRegister.visibleProperty());
            loggedUserBox.setVisible(true);
        }
    }

    public void advanceSearchChacked(){
        if(advanceSearchCheckbox.isSelected())
            advancedSearchBox.setVisible(true);
        else
            advancedSearchBox.setVisible(false);
        advancedSearchBox.managedProperty().bind(advancedSearchBox.visibleProperty());
    }

    public void loginClicked(){
        Main.switchScene("../View/LoginForm.fxml",(Stage) LoginRegister.getScene().getWindow(), 400,300);
    }
    public void logOutClicked(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("האם אתה בטוח שאתה רוצה להתנתק?");
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Main.loggedUser = null;
            initialize();
        }
        else{
            alert.close();
        }
    }
    public void seeProfileClicked(){
        Main.editable=false;
        Main.switchScene("../View/UserDetailsScreen.fxml", (Stage) LoginRegister.getScene().getWindow(), 720,500);

    }
    public void destinationSearchPressed(KeyEvent keyEvent){
        if (keyEvent.getCode().equals(KeyCode.ENTER))
        {
            System.out.println("do search");
        }
    }
    public void userSearchPresses(KeyEvent keyEvent) throws SQLException {
        User userSerach;
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            userSerach=search(txt_searchUser.getText());
            if (userSerach!=null) {
                userSerach.print();
                Main.setUser(userSerach);
                Main.switchScene("../View/UserDetailsScreen.fxml", (Stage) txt_searchUser.getScene().getWindow(), 720,500);

            }
            else {
                System.out.println("not found");
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("לא נמצאו תוצאות");
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.showAndWait();
            }
        }
    }



}

