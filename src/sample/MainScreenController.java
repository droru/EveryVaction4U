package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    public void activateEvents(){
        System.out.println("hi");
        /*txt_searchDestination.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (newPropertyValue)
                {
                    txt_searchDestination.setText("");
                }
                else
                {
                    txt_searchDestination.setText("הקלד יעד לחיפוש...");
                }
            }
        });*/
    }

    public void destinationSearchIn(){
//        txt_searchDestination.setText("");
    }
    public void destinationSearchOut(){
//        txt_searchDestination.setText("הקלד יעד לחיפוש...");
    }
    public void advanceSearchChacked(){
        if(advanceSearchCheckbox.isSelected())
            advancedSearchBox.setVisible(true);
        else
            advancedSearchBox.setVisible(false);
        advancedSearchBox.managedProperty().bind(advancedSearchBox.visibleProperty());
    }
}
