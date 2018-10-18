package sample;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;


public class MainScreenController {
    @FXML
    public TextField txt_searchUser;
    public TextField txt_searchDestination;
    public HBox advancedSearchBox;
    public CheckBox advanceSearchCheckbox;

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
        System.out.println("login");
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
        if(keyEvent.getCode().equals(KeyCode.ENTER)){

        }
    }
}
