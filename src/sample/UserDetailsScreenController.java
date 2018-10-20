package sample;

import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import static Model.Query.delete;
import static Model.Query.update;
import static sample.RegisterController.*;

public class UserDetailsScreenController {
    @FXML
    public ImageView img_profile;
    public TextField lbl_firstName;
    public TextField lbl_lastName;
    public TextField lbl_birthDate;
    public TextField lbl_city;
    public TextField lbl_email;
    public Button updateButton;
    public Button deleteButton;
    public Hyperlink makeEdit;
    public Hyperlink changePass;
    public Hyperlink deleteuser;

    public Label title;
    public ChoiceBox _city;
    public Label erorfirstname;
    public Label erorlastname;
    public Label erormail;


    public void initialize(){
            lbl_firstName.setText(Main.user.getFirstName());
            lbl_lastName.setText(Main.user.getLastName());
            lbl_birthDate.setText(Main.user.getBirthDate());
            lbl_city.setText(Main.user.getCity());
           lbl_email.setText(Main.user.getEmail());
            disableEdit();
            if (!Main.editable) {
                title.setText("אזור אישי");
                makeEdit.setVisible(true);
            }
            else {
                title.setText("פרופיל משתמש");
            }
    }
    public void disableEdit(){
        lbl_firstName.setDisable(true);
        lbl_birthDate.setDisable(true);
        lbl_email.setDisable(true);
        lbl_city.setDisable(true);
        lbl_lastName.setDisable(true);
        updateButton.setVisible(false);
        deleteButton.setVisible(false);
        makeEdit.setVisible(false);
        changePass.setVisible(false);
    }

    public void enableEdit(){
        lbl_firstName.setDisable(false);
        lbl_email.setDisable(false);
        lbl_city.setDisable(false);
        lbl_lastName.setDisable(false);
        changePass.setVisible(true);
        updateButton.setVisible(true);
        lbl_city.setVisible(false);
        _city.setVisible(true);

    }

    public void setUserDetails(User user){
        lbl_firstName.setText("test");

    }

    public void deleteClicked(){}
    public void updateClicked() {
        if (validateMail(lbl_email.getText())) {
            Main.user.setEmail(lbl_email.getText());
            erormail.setVisible(false);
        }
        else
            erormail.setVisible(true);
        if (validateName(lbl_firstName.getText())) {
            Main.user.setFirstName(lbl_firstName.getText());
            erorfirstname.setVisible(false);
        }
        else
            erorfirstname.setVisible(true);
        if (validateName(lbl_lastName.getText())) {
            Main.user.setLastName(lbl_lastName.getText());
            erorlastname.setVisible(false);
        }
        else
            erorlastname.setVisible(true);

        if(!erormail.isVisible()&&!erorlastname.isVisible()&&!erorfirstname.isVisible())
        {
            Main.user.setEmail(lbl_email.getText());
            Main.user.setFirstName(lbl_firstName.getText());
            Main.user.setLastName(lbl_lastName.getText());
            //Main.user.setCity(_city.getValue().toString());
            update(Main.user);
            Main.switchScene("../View/MainScreen.fxml", (Stage) updateButton.getScene().getWindow(), 720,500);
        }

    }
    public void delUser(){
        delete(Main.user.getUserName());
        Main.switchScene("../View/MainScreen.fxml", (Stage) updateButton.getScene().getWindow(), 720,500);

    }
}
