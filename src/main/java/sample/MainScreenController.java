package sample;

import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import static Model.Query.search;


public class MainScreenController {
    @FXML
    public TextField txt_searchUser;
    public TextField txt_searchDestination;
    public GridPane advancedSearchBox;
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

    public void loginClicked() throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginForm.fxml"));
        StageDetail(stage, root, Main.loginWidth, Main.loginHeight, "Login/register");
        LoginRegister.setDisable(true);
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
        Main.isProfile=true;
        Main.switchScene("../View/UserDetailsScreen.fxml", Main.getStage(), Main.mainWidth,Main.mainHeight);

    }
    public void destinationSearchPressed(KeyEvent keyEvent){
        if (keyEvent.getCode().equals(KeyCode.ENTER))
        {
            System.out.println("do search");
        }
    }
    public void userSearchPresses(KeyEvent keyEvent) throws IOException {
        User userSerach;
        if(keyEvent.getCode().equals(KeyCode.ENTER))
        {
            userSerach=search(txt_searchUser.getText());
            if (userSerach!=null) {
                userSerach.print();
                Main.setUser(userSerach);
                Main.isProfile = false;
                Stage stage=new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("../View/UserDetailsScreen.fxml"));
                StageDetail(stage, root, Main.registerWidth, Main.registerHeight, "User profile");
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

    private void StageDetail(Stage stage, Parent root, int width, int height, String title) {
        stage.setTitle(title);
        Scene scene=new Scene(root,width,height);
        scene.getStylesheets().add(getClass().getResource("../View/Style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(txt_searchUser.getScene().getWindow());
        stage.show();
        stage.setOnCloseRequest(event -> LoginRegister.setDisable(false));
    }


}

