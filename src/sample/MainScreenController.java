package sample;

import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static Model.Query.search;


public class MainScreenController {
    @FXML
    public TextField txt_searchUser;
    public TextField txt_searchDestination;
    public HBox advancedSearchBox;
    public CheckBox advanceSearchCheckbox;
    public Hyperlink LoginRegister;
    public TextArea textArea_firstFlight;
    public TextArea textArea_secondFlight;
    public TextArea textArea_thirdFlight;
    public TextArea textArea_fourthFlight;
    public TextArea textArea_fifthFlight;

    public void advanceSearchChacked(){
        if(advanceSearchCheckbox.isSelected())
            advancedSearchBox.setVisible(true);
        else
            advancedSearchBox.setVisible(false);
        advancedSearchBox.managedProperty().bind(advancedSearchBox.visibleProperty());
    }

    public void loginClicked(){
        Main.switchScene("../View/RegisterForm.fxml", (Stage) LoginRegister.getScene().getWindow(), 720,500);
    }
    public void seeProfileClicked(){
        System.out.println("profile");
    }
    public void destinationSearchPressed(KeyEvent keyEvent){
        if (keyEvent.getCode().equals(KeyCode.ENTER))
        {
        }
    }
    public void userSearchPresses(KeyEvent keyEvent){
        User userSerach;
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            userSerach=search(txt_searchUser.getText());
            if (userSerach!=null) {
                userSerach.print();
                Main.setUser(userSerach);
                Main.switchScene("../View/UserDetailsScreen.fxml", (Stage) txt_searchUser.getScene().getWindow(), 720,500);

            }
            else
                System.out.println("not found");
        }
    }
}

